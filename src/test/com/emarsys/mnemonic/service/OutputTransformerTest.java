package com.emarsys.mnemonic.service;

import com.emarsys.mnemonic.miner.model.MnemonicTfIdf;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static junit.framework.TestCase.assertTrue;


public class OutputTransformerTest {

    OutputTransformer outputTransformer;

    @Before
    public void setup() {
        outputTransformer = new OutputTransformer();
    }

    @Test
    public void transform() throws Exception {
        String expected1 = "2774716: aspir1n (0,2085209)";
        String expected2 = "72840436: path0gen (0,0563529)";
        String expected3 = "72846436: ratingen (0,0675645),pathogen (0,0563529)";

        TreeMap<String, List<MnemonicTfIdf>> input = new TreeMap<>();

        ArrayList<MnemonicTfIdf> list = new ArrayList<>();
        list.add(new MnemonicTfIdf("aspir1n", 0.2085209));
        input.put("2774716", list);

        list = new ArrayList<>();
        list.add(new MnemonicTfIdf("path0gen", 0.0563529));
        input.put("72840436", list);

        list = new ArrayList<>();
        list.add(new MnemonicTfIdf("pathogen", 0.0563529));
        list.add(new MnemonicTfIdf("ratingen", 0.0675645));
        input.put("72846436", list);


        String actual = outputTransformer.transform(input);
        assertTrue(actual.contains(expected1));
        assertTrue(actual.contains(expected2));
        assertTrue(actual.contains(expected3));

    }

}