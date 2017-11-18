package com.emarsys.mnemonic.miner;


import com.emarsys.mnemonic.dao.IndexDao;
import com.emarsys.mnemonic.dao.IndexDaoImpl;

public class MinerFactory {

    /**
     * Creates a miner object based on input params
     * @param indexDirectory
     * @return An initalized MinerImpl object
     */
    public Miner create(final String indexDirectory, final String sourceDirectory) {
        IndexDao indexDao = new IndexDaoImpl(indexDirectory);

        PhoneNumberCalculator phoneNumberCalculator = new PhoneNumberCalculatorImpl();

        Miner miner = new MinerImpl(sourceDirectory, phoneNumberCalculator, indexDao);

        return miner;
    }
}
