package com.emarsys.mnemonic.miner.model;


public class PhoneMnemonic {

    public String phoneNumber;
    public String mNemonic;

    public PhoneMnemonic(String phoneNumber, String mNemonic) {
        this.phoneNumber = phoneNumber;
        this.mNemonic = mNemonic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getmNemonic() {
        return mNemonic;
    }

    public void setmNemonic(String mNemonic) {
        this.mNemonic = mNemonic;
    }
}
