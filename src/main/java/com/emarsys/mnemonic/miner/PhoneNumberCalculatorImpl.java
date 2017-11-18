package com.emarsys.mnemonic.miner;


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
     * @param text
     * @return Number sequence which represents a phone number
     */
    @Override
    public String calcPhoneNumber(String text) {
        String s = text.toUpperCase();
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            int number = getNumber(s.charAt(i));
            result += String.valueOf(number);
        }
        return result;
    }
}
