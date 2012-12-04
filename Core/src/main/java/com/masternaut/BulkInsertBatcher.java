package com.masternaut;

import com.masternaut.repository.BaseCustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        Map<String,List<T>> groupedByCustomerId = ListHelper.groupBy(batch, new ListHelper.Selector<T, String>() {
            @Override
            public String action(T t) {
                return t.getCustomerId();
            }
        });

        for(Map.Entry<String,List<T>> itemsForCustomer : groupedByCustomerId.entrySet()){
            repository.bulkInsert(itemsForCustomer.getValue());
        }

        batch = new ArrayList<T>();
    }
}
