package com.masternaut.repository;

import com.masternaut.domain.Asset;
import org.springframework.data.mongodb.core.MongoTemplate;

public class AssetRepository {

    private MongoTemplate mongoTemplate;

    public AssetRepository(MongoTemplate mongoTemplate) {

        this.mongoTemplate = mongoTemplate;
    }

    public void deleteAll() {

    }

    public void save(Asset asset) {
        mongoTemplate.save(asset);
    }

    public Asset findById(String id) {
        return mongoTemplate.findById(id, Asset.class);
    }
}
