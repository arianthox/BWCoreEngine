package com.globant.brainwaves.repository;

import com.globant.brainwaves.domain.BufferRawData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BufferRawRepository extends ElasticsearchRepository<BufferRawData, String> {

    Optional<List<BufferRawData>> findAllByDeviceId(String deviceId);

}
