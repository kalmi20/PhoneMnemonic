package com.emarsys.mnemonic.miner;


import com.emarsys.mnemonic.miner.model.PhoneMnemonic;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumberCalculatorImpl implements PhoneNumberCalculator {

    private int getNumber(char uppercaseLetter) {
        char[] letter = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        int[] value = {2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 9, 9, 9, 9};

        for (int i = 0; i < letter.length; i++) {
            if (uppercaseLetter == letter[i])
                return value[i];
        }
        return 0;
    }

    /**
     * Calculates the phone number representation of the input string
     *
     * @param mNemonic
     * @return Number sequence which represents a phone number
     */
    @Override
    public String calcPhoneNumber(String mNemonic) {
        String s = mNemonic.toUpperCase();
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            int number;
            if (Character.isDigit(s.charAt(i))) {
                number = Character.getNumericValue(s.charAt(i));
            } else {
                number = getNumber(s.charAt(i));
            }
            result += String.valueOf(number);
        }
        return result;
    }

    /**
     * Calculates the phone number representation of the given intput strings
     *
     * @param mNemonics
     * @return
     */
    @Override
    public List<PhoneMnemonic> calcPhoneNumbers(List<String> mNemonics) {
        List<PhoneMnemonic> results = new ArrayList<>();
        mNemonics.forEach(mNemonic -> {
            String s = mNemonic.toUpperCase();
            String result = "";
            for (int i = 0; i < s.length(); i++) {
                int number;
                if (Character.isDigit(s.charAt(i))) {
                    number = Character.getNumericValue(s.charAt(i));
                } else {
                    number = getNumber(s.charAt(i));
                }
                result += String.valueOf(number);
            }
            results.add(new PhoneMnemonic(result, mNemonic));
        });
        return results;
    }
}
