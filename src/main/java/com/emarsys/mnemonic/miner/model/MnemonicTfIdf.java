package com.emarsys.mnemonic.miner.model;

import java.io.Serializable;

public class MnemonicTfIdf implements Serializable {
    private String mnemonic;
    private Double tfIdf;

    public MnemonicTfIdf() {
    }

    public MnemonicTfIdf(String mnemonic, Double tfIdf) {
        this.mnemonic = mnemonic;
        this.tfIdf = tfIdf;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public Double getTfIdf() {
        return tfIdf;
    }

    public void setTfIdf(Double tfIdf) {
        this.tfIdf = tfIdf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MnemonicTfIdf that = (MnemonicTfIdf) o;

        if (mnemonic != null ? !mnemonic.equals(that.mnemonic) : that.mnemonic != null) return false;
        return tfIdf != null ? tfIdf.equals(that.tfIdf) : that.tfIdf == null;

    }

    @Override
    public int hashCode() {
        int result = mnemonic != null ? mnemonic.hashCode() : 0;
        result = 31 * result + (tfIdf != null ? tfIdf.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return mnemonic + "(" + tfIdf + ")";
    }
}
