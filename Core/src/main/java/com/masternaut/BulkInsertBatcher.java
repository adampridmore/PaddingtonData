package com.masternaut;

import com.masternaut.repository.BaseCustomerRepository;

import java.util.ArrayList;
import java.util.List;

public class BulkInsertBatcher<T extends CustomerIdentifiable>{

    private List<T> batch = new ArrayList<T>();
    private int batchSize = 1000;
    private BaseCustomerRepository<T> repository;

    public BulkInsertBatcher(BaseCustomerRepository<T> repository) {
        this.repository = repository;
    }

    public void add(T t){
        batch.add(t);

        if (batch.size() >= batchSize){
            flush();
        }
    }

    public void flush(){
        repository.bulkInsert(batch);

        batch = new ArrayList<T>();
    }
}
