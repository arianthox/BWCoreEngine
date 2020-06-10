package com.globant.brainwaves.repository;

import com.globant.brainwaves.domain.PatternFileData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatternFileRepository  extends ElasticsearchRepository<PatternFileData, String> {
    Optional<List<PatternFileData>> findByType(String type);

    Optional<PatternFileData> findByName(String name);

}
