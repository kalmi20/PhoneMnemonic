package com.emarsys.mnemonic.service;


import com.emarsys.mnemonic.miner.model.MnemonicTfIdf;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputTransformer {

    /**
     * Transforms the internal representation to a human readable form
     * @param input
     * @return A string representation of the phone, mnemonic lookup results
     */
    public String transform(Map<String, List<MnemonicTfIdf>> input) {
        StringBuilder stringBuilder = new StringBuilder();

        DecimalFormat df = new DecimalFormat("#.#######");

        input.entrySet().forEach(stringListEntry -> stringBuilder.append(stringListEntry.getKey())
                .append(": ")
                .append(stringListEntry.getValue()
                        .stream()
                        .sorted(Comparator.comparing(MnemonicTfIdf::getTfIdf).reversed())
                        .map(mnemonicTfIdf -> mnemonicTfIdf.getMnemonic() + " (" + df.format(mnemonicTfIdf.getTfIdf()) + ")")
                        .collect(Collectors.joining(",")))
                .append(System.lineSeparator()));

        return stringBuilder.toString();
    }
}
