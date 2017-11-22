package com.emarsys.mnemonic.miner;

import java.util.ArrayList;
import java.util.List;

public class MnemonicConverterImpl implements MnemonicConverter {

    /**
     * Returns converted mnemonics, letter i will be replaced with 1 and letter o with 0,
     * original mnemonic will also be added to list
     *
     * @param mnemonic
     * @return
     */
    @Override
    public List<String> convertMnemonic(String mnemonic) {
        List<String> convertedStrings = new ArrayList<>();
        convertedStrings.add(mnemonic);
        boolean newStringAdded = true;
        while (newStringAdded) {
            newStringAdded = false;
            ArrayList<String> tempList = new ArrayList<>(convertedStrings);
            for (String convertedString : convertedStrings) {
                for (int i = 0; i < convertedString.length(); i++) {
                    if (convertedString.charAt(i) == 'i' || convertedString.charAt(i) == 'o') {
                        char newChar;
                        newChar = convertedString.charAt(i) == 'i' ? '1' : '0';
                        String newString = changeCharInPosition(i, newChar, convertedString);
                        if (!tempList.contains(newString)) {
                            tempList.add(newString);
                            newStringAdded = true;
                        }
                    }
                }
            }
            convertedStrings = new ArrayList<>(tempList);
        }
        return convertedStrings;
    }

    private String changeCharInPosition(int position, char ch, String str) {
        char[] charArray = str.toCharArray();
        charArray[position] = ch;
        return new String(charArray);
    }
}
