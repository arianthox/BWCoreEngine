package com.globant.brainWaves.repository;

import com.globant.brainWaves.model.Wave;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaveRepository extends ElasticsearchRepository<Wave, String> {
}
