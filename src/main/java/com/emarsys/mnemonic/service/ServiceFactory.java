package com.emarsys.mnemonic.service;

import com.emarsys.mnemonic.dao.IndexDao;
import com.emarsys.mnemonic.dao.IndexDaoImpl;

public class ServiceFactory {

    /**
     * Creates a service object based on input params
     * @param indexDirectory
     * @return An initalized Service object
     */
    public PhoneMnemonicService create(final String indexDirectory) {
        IndexDao indexDao = new IndexDaoImpl(indexDirectory);

        PhoneMnemonicService phoneMnemonicService = new PhoneMnemonicServiceImpl(indexDao);

        return phoneMnemonicService;
    }
}
