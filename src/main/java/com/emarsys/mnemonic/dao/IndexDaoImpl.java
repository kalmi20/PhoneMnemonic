package com.emarsys.mnemonic.dao;


import com.emarsys.mnemonic.miner.model.TfIdfIndex;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class IndexDaoImpl implements IndexDao {

    private static final String extension = "idx";
    private String indexDirectory;
    private ObjectMapper mapper;

    public IndexDaoImpl(String indexDirectory) {
        this.mapper = new ObjectMapper();
        this.indexDirectory = indexDirectory;
    }

    @Override
    public void persist(TfIdfIndex tfIdfIndex) {
        FileOutputStream fout = null;
        try {
            String persistPath = indexDirectory + File.separator + tfIdfIndex.getId() + "." + extension;
            Path pathToFile = Paths.get(persistPath);
            Files.createDirectories(pathToFile.getParent());
            Files.createFile(pathToFile);
            fout = new FileOutputStream(persistPath);
            mapper.writeValue(fout, tfIdfIndex);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<TfIdfIndex> readIndexes() {
        File dir = new File(indexDirectory);
        if (!dir.exists())
            throw new IllegalArgumentException("Index directory doesn't exists");

        List<TfIdfIndex> indices = new ArrayList<>();

        Path indexDirectoryPath = Paths.get(indexDirectory);
        final PathMatcher filter = indexDirectoryPath.getFileSystem().getPathMatcher("glob:**/*." + extension);

        try (final Stream<Path> stream = Files.list(indexDirectoryPath)) {
            stream.filter(filter::matches)
                    .forEach(path -> {
                        try (
                                InputStream file = new FileInputStream(path.toFile());
                                InputStream buffer = new BufferedInputStream(file);
                        ) {
                            indices.add(mapper.readValue(buffer, TfIdfIndex.class));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indices;
    }
}
