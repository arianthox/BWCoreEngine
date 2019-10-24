package com.globant.brainwaves.repository;

import com.globant.brainwaves.model.Wave;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaveRepository extends ElasticsearchRepository<Wave, String> {
}
