package com.emarsys.mnemonic.miner;


import com.emarsys.mnemonic.dao.IndexDao;
import com.emarsys.mnemonic.dao.JsonIndexDaoImpl;

public class MinerFactory {

    /**
     * Creates a miner object based on input params
     *
     * @param indexDirectory
     * @return An initalized MinerImpl object
     */
    public Miner create(final String indexDirectory, final String sourceDirectory) {
        IndexDao indexDao = new JsonIndexDaoImpl(indexDirectory);

        PhoneNumberCalculator phoneNumberCalculator = new PhoneNumberCalculatorImpl();
        MnemonicConverterImpl mnemonicConverter = new MnemonicConverterImpl();
        return new MinerImpl(sourceDirectory, phoneNumberCalculator, indexDao, mnemonicConverter);
    }
}
