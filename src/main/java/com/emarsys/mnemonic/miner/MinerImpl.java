package com.emarsys.mnemonic.miner;

import com.emarsys.mnemonic.dao.IndexDao;
import com.emarsys.mnemonic.miner.model.MnemonicTfIdf;
import com.emarsys.mnemonic.miner.model.TfIdfIndex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MinerImpl implements Miner {

    private static final String extension = "txt";

    private final String sourceDirectory;
    private PhoneNumberCalculator phoneNumberCalculator;
    private IndexDao indexDao;
    private int numberOfDocs = 0;

    public MinerImpl(String sourceDirectory, PhoneNumberCalculator phoneNumberCalculator, IndexDao indexDao) {
        this.sourceDirectory = sourceDirectory;
        this.phoneNumberCalculator = phoneNumberCalculator;
        this.indexDao = indexDao;
    }

    /**
     * Method iterates trough  all the text files located in the source directory, calculates the tf-idf value for each
     * token, and creates the index structure in below structure.
     *  {
     *      "id":"201711181541",
     *      "tfIdfMap":{
     *          "467748257":[{"mnemonic":"hospitals","tfIdf":0.0457755120278379}, ...],
     *          ...
     *      }
     *	}
     * id: id and the name of the index file
     * tfIdfMap: Map of the generated phone numbers, value is a list of mnemonic and tfidf value pairs
     */
    @Override
    public void mine() {
        File dir = new File(sourceDirectory);
        if (!dir.exists())
            throw new IllegalArgumentException("Source directory doesn't exists");

        RegularExpressionTokenizer mnemonicTokenizer = new RegularExpressionTokenizer();
        RegularExpressionTokenizer worldsTokenizer = new RegularExpressionTokenizer(Pattern.compile("\\b\\w\\w+\\b"));
        Map<String, Integer> numOfTokensInFileMap = new HashMap<>();
        Map<String, Map<String, Integer>> mnemonicsCountInfFilesMap = new HashMap<>();

        Path sourceDirectoryPath = Paths.get(sourceDirectory);
        final PathMatcher filter = sourceDirectoryPath.getFileSystem().getPathMatcher("glob:**/*." + extension);
        try (Stream<Path> paths = Files.walk(Paths.get(sourceDirectory))) {
            paths.filter(filter::matches).forEach(path ->
                    {
                        int numberOfTokensInFile = 0;
                        numberOfDocs++;
                        try (BufferedReader in = new BufferedReader(new FileReader(path.toFile()))) {
                            String read = null;
                            while ((read = in.readLine()) != null) {
                                List<String> mnemonics = mnemonicTokenizer.tokenize(read);

                                mnemonics.forEach(mnemonic ->
                                {
                                    Map<String, Integer> occurenceInFiles = mnemonicsCountInfFilesMap.get(mnemonic);
                                    if (occurenceInFiles == null) {
                                        occurenceInFiles = new HashMap<>();
                                        occurenceInFiles.put(path.toString(), 1);
                                        mnemonicsCountInfFilesMap.put(mnemonic, occurenceInFiles);
                                    } else {
                                        Integer occurenceCount = occurenceInFiles.get(path.toString());
                                        if (occurenceCount == null) {
                                            occurenceInFiles.put(path.toString(), 1);
                                        } else {
                                            occurenceInFiles.put(path.toString(), ++occurenceCount);
                                        }
                                    }
                                });
                                numberOfTokensInFile += worldsTokenizer.tokenize(read).size();
                            }
                            numOfTokensInFileMap.put(path.toString(), numberOfTokensInFile);
                        } catch (IOException e) {
                            System.out.println("There was a problem: " + e);
                            e.printStackTrace();
                        }
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException("Exception occured during mining process, exception: " + e.getMessage());
        }

        Map<String, List<MnemonicTfIdf>> tfIdfMap = calculatetfIdfValues(numOfTokensInFileMap, mnemonicsCountInfFilesMap);

        String id = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        indexDao.persist(new TfIdfIndex(id, tfIdfMap));
    }

    private Map<String, List<MnemonicTfIdf>> calculatetfIdfValues(Map<String, Integer> numOfTokensInFileMap, Map<String, Map<String, Integer>> mnemonicsCountInfFilesMap) {
        Map<String, List<MnemonicTfIdf>> tfIdfMap = new HashMap<>();
        mnemonicsCountInfFilesMap.entrySet().parallelStream().forEach(fileOccurenceMap -> {
            final String mnemonic = fileOccurenceMap.getKey();
            final String phoneNumber = phoneNumberCalculator.calcPhoneNumber(mnemonic);
            int numOfDocumentContainingMnemonic = fileOccurenceMap.getValue().entrySet().size();

            //discard words that do not occur at least in 3 different documents
            if(numOfDocumentContainingMnemonic < 3)
                return;

            final double[] maxTfIdforMnemonic = {Double.MIN_VALUE};
            fileOccurenceMap.getValue().entrySet().forEach(docOccurence -> {
                String fileName = docOccurence.getKey();
                int mnemonicCountInFile = docOccurence.getValue();

                Integer numberOfTokensInFile = numOfTokensInFileMap.get(fileName);
                double tfidf = (((double) mnemonicCountInFile) / numberOfTokensInFile) * Math.log(numberOfDocs / (1 + numOfDocumentContainingMnemonic));

                if (tfidf > maxTfIdforMnemonic[0])
                    maxTfIdforMnemonic[0] = tfidf;
            });

            List<MnemonicTfIdf> mnemonicTfIdfs = tfIdfMap.get(phoneNumber);
            if (mnemonicTfIdfs == null) {
                ArrayList<MnemonicTfIdf> mNemonicList = new ArrayList<>();
                mNemonicList.add(new MnemonicTfIdf(mnemonic, maxTfIdforMnemonic[0]));
                tfIdfMap.put(phoneNumber, mNemonicList);
            } else {
                mnemonicTfIdfs.add(new MnemonicTfIdf(mnemonic, maxTfIdforMnemonic[0]));
            }
        });

        return tfIdfMap;
    }
}
