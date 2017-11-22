package com.emarsys.mnemonic.service;


import com.emarsys.mnemonic.dao.IndexDao;
import com.emarsys.mnemonic.miner.model.MnemonicTfIdf;
import com.emarsys.mnemonic.miner.model.TfIdfIndex;

import java.util.*;

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
        List<MnemonicTfIdf> result = new ArrayList<>();
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
            }
            else
            {
                result.addAll(mnemonicTfIdfsToAdd);
            }
        }
        return result;
    }
}
