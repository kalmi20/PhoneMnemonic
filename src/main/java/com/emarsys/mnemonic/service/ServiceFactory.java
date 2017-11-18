package com.emarsys.mnemonic.service;

import com.emarsys.mnemonic.dao.IndexDao;
import com.emarsys.mnemonic.dao.JsonIndexDaoImpl;

public class ServiceFactory {

    /**
     * Creates a service object based on input params
     * @param indexDirectory
     * @return An initalized Service object
     */
    public PhoneMnemonicService create(final String indexDirectory) {
        IndexDao indexDao = new JsonIndexDaoImpl(indexDirectory);

        return new PhoneMnemonicServiceImpl(indexDao);
    }
}
