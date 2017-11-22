package com.emarsys.mnemonic.miner;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class MnemonicConverterImplTest {

    MnemonicConverter mnemonicConverter;

    @Before
    public void setup() {
        mnemonicConverter = new MnemonicConverterImpl();
    }

    @Test
    public void convertMnemonic() throws Exception {
        List<String> convertMnemonic = mnemonicConverter.convertMnemonic("official");
        assertEquals(8, convertMnemonic.size());
        assertTrue(convertMnemonic.contains("official"));
        assertTrue(convertMnemonic.contains("0fficial"));
        assertTrue(convertMnemonic.contains("0ff1cial"));
        assertTrue(convertMnemonic.contains("0ff1c1al"));
        assertTrue(convertMnemonic.contains("0ffic1al"));
        assertTrue(convertMnemonic.contains("off1c1al"));
        assertTrue(convertMnemonic.contains("offic1al"));
        assertTrue(convertMnemonic.contains("off1cial"));
    }
}