package com.emarsys.mnemonic.miner.model;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TfIdfIndex implements Serializable {
    private String id;
    private Map<String, List<MnemonicTfIdf>> tfIdfMap;

    public TfIdfIndex() {
    }

    public TfIdfIndex(String id, Map<String, List<MnemonicTfIdf>> tfIdfMap) {
        this.id = id;
        this.tfIdfMap = tfIdfMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, List<MnemonicTfIdf>> getTfIdfMap() {
        return tfIdfMap;
    }

    public void setTfIdfMap(Map<String, List<MnemonicTfIdf>> tfIdfMap) {
        this.tfIdfMap = tfIdfMap;
    }

    @Override
    public String toString() {
        return "TfIdfIndex{" +
                "id='" + id + '\'' +
                ", tfIdfMap=" + tfIdfMap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TfIdfIndex that = (TfIdfIndex) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return tfIdfMap != null ? tfIdfMap.equals(that.tfIdfMap) : that.tfIdfMap == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tfIdfMap != null ? tfIdfMap.hashCode() : 0);
        return result;
    }
}
