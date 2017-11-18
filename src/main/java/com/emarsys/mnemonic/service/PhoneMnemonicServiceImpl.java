package com.emarsys.mnemonic.service;


import com.emarsys.mnemonic.dao.IndexDao;
import com.emarsys.mnemonic.miner.model.MnemonicTfIdf;
import com.emarsys.mnemonic.miner.model.TfIdfIndex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Looks up the mnemonics with their tfidf value for the input phone number
     * @param phoneNumber
     * @return A list of mnemonics with their tfIdf value
     */
    @Override
    public List<MnemonicTfIdf> getMnemonicsForPhoneNumber(final String phoneNumber) {
        List<MnemonicTfIdf> result = new ArrayList<>();
        for (TfIdfIndex tfIdfIndice : tfIdfIndices) {
            List<MnemonicTfIdf> mnemonicTfIdfs = tfIdfIndice.getTfIdfMap().get(phoneNumber);
            if (mnemonicTfIdfs == null || mnemonicTfIdfs.size() == 0)
                continue;
            result.addAll(mnemonicTfIdfs);
        }
        return result;
    }
}
