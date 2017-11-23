package com.emarsys.mnemonic.service;


import com.emarsys.mnemonic.dao.IndexDao;
import com.emarsys.mnemonic.miner.model.MnemonicTfIdf;
import com.emarsys.mnemonic.miner.model.TfIdfIndex;
import com.google.common.collect.Iterables;

import java.util.*;
import java.util.stream.Collectors;

public class PhoneMnemonicServiceImpl implements PhoneMnemonicService {

    private IndexDao indexDao;
    private List<TfIdfIndex> tfIdfIndices;

    public PhoneMnemonicServiceImpl(IndexDao indexDao) {
        this.indexDao = indexDao;
        init();
    }

    private void init() {
        tfIdfIndices = indexDao.readIndexes();
    }

    /**
     * Looks up the mnemonics with their tfidf value for the input phone numbers
     *
     * @param phoneNumbers
     * @return A map of phone numbers and a list of mnemonics with their tfIdf value
     */
    @Override
    public Map<String, List<MnemonicTfIdf>> getMnemonicsForPhoneNumbers(final List<String> phoneNumbers) {
        Map<String, List<MnemonicTfIdf>> result = new HashMap<>();
        phoneNumbers.forEach(phoneNumber -> result.put(phoneNumber, getMnemonicsForPhoneNumber(phoneNumber)));
        return result;
    }

    /**
     * Looks up the mnemonics with their tfidf value for the input phone number, if it is present in multiple files, then
     * it pick the one with the greater tfidf value
     *
     * @param phoneNumber
     * @return A list of mnemonics with their tfIdf value
     */
    @Override
    public List<MnemonicTfIdf> getMnemonicsForPhoneNumber(final String phoneNumber) {
        final List<MnemonicTfIdf> result = new ArrayList<>();
        for (TfIdfIndex tfIdfIndice : tfIdfIndices) {
            List<MnemonicTfIdf> mnemonicTfIdfsToAdd = tfIdfIndice.getTfIdfMap().get(phoneNumber);
            if (mnemonicTfIdfsToAdd == null || mnemonicTfIdfsToAdd.size() == 0)
                continue;

            if (result.size() > 0) {
                mnemonicTfIdfsToAdd.forEach(mnemonicTfIdf -> {
                    Optional<MnemonicTfIdf> first = result
                            .stream()
                            .filter(mnemonicTfIdf1 -> mnemonicTfIdf1.getMnemonic().equalsIgnoreCase(mnemonicTfIdf.getMnemonic()))
                            .findFirst();

                    if (first.isPresent()) {
                        if (first.get().getTfIdf() < mnemonicTfIdf.getTfIdf()) {
                            first.get().setTfIdf(mnemonicTfIdf.getTfIdf());
                        }
                    } else {
                        result.add(mnemonicTfIdf);
                    }
                });
            } else {
                result.addAll(mnemonicTfIdfsToAdd);
            }
        }

        return result;
    }

    /**
     * Genereates multi world mnemonics based on already found mnemonics according the logic that a multi world mnemonics
     * should be present in its full length. Creates partitions from the phone number and tries to find mnemonics for those
     * partitions
     * TODO finish implementation
     * @param mnemonics
     * @param phoneNumber
     * @return
     */
    private List<MnemonicTfIdf> tryToGenerateMultiWordMnemonics(List<MnemonicTfIdf> mnemonics, String phoneNumber) {
        int phoneNumberLength = phoneNumber.length();
        String[] split = phoneNumber.split("");
        for (int i = 3; i < phoneNumberLength; i++) {
            Iterable<List<String>> partitionsIterable = Iterables.partition(Arrays.asList(split), i);
            boolean invalidPartitioning = false;
            for (List<String> partition : partitionsIterable) {
                if (partition.size() < 3) {
                    invalidPartitioning = true;
                }
            }
            if (invalidPartitioning)
                continue;

            for (List<String> partition : partitionsIterable) {
                String phoneNumberPartition = partition.stream()
                        .collect(Collectors.joining(""));

                final List<MnemonicTfIdf> resultForPartition = new ArrayList<>();
                for (TfIdfIndex tfIdfIndice : tfIdfIndices) {
                    List<MnemonicTfIdf> mnemonicTfIdfsToAdd = tfIdfIndice.getTfIdfMap().get(phoneNumberPartition);
                    if (mnemonicTfIdfsToAdd == null || mnemonicTfIdfsToAdd.size() == 0)
                        continue;

                    if (resultForPartition.size() > 0) {
                        mnemonicTfIdfsToAdd.forEach(mnemonicTfIdf -> {
                            Optional<MnemonicTfIdf> first = resultForPartition
                                    .stream()
                                    .filter(mnemonicTfIdf1 -> mnemonicTfIdf1.getMnemonic().equalsIgnoreCase(mnemonicTfIdf.getMnemonic()))
                                    .findFirst();

                            if (first.isPresent()) {
                                if (first.get().getTfIdf() < mnemonicTfIdf.getTfIdf()) {
                                    first.get().setTfIdf(mnemonicTfIdf.getTfIdf());
                                }
                            } else {
                                resultForPartition.add(mnemonicTfIdf);
                            }
                        });
                    } else {
                        resultForPartition.addAll(mnemonicTfIdfsToAdd);
                    }
                }

                List<MnemonicTfIdf> collect = resultForPartition.stream().filter(mnemonicTfIdf -> {
                    for (MnemonicTfIdf mnemonic : mnemonics) {
                        if (mnemonic.getMnemonic().contains(mnemonicTfIdf.getMnemonic()))
                            return true;
                    }
                    return false;
                }).collect(Collectors.toList());
            }
        }

        return mnemonics;
    }
}
