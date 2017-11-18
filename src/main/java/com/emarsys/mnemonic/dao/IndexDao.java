package com.emarsys.mnemonic.dao;


import com.emarsys.mnemonic.miner.model.TfIdfIndex;

import java.util.List;

public interface IndexDao {
    void persist(TfIdfIndex tfIdfIndex);

    List<TfIdfIndex> readIndexes();
}
