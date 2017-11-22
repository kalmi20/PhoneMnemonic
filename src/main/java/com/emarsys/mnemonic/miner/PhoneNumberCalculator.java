package com.emarsys.mnemonic.miner;

import com.emarsys.mnemonic.miner.model.PhoneMnemonic;

import java.util.List;

public interface PhoneNumberCalculator {
    String calcPhoneNumber(String text);

    List<PhoneMnemonic> calcPhoneNumbers(List<String> mNemonics);
}
