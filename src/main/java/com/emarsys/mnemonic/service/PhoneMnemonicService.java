package com.emarsys.mnemonic.service;

import com.emarsys.mnemonic.miner.model.MnemonicTfIdf;

import java.util.List;
import java.util.Map;

public interface PhoneMnemonicService {

    Map<String, List<MnemonicTfIdf>> getMnemonicsForPhoneNumbers(final List<String> phoneNumbers);

    List<MnemonicTfIdf> getMnemonicsForPhoneNumber(final String phoneNumber);
}
