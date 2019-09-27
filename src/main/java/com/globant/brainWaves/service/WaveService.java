package com.globant.brainWaves.service;

import com.globant.brainWaves.model.Wave;
import com.globant.brainWaves.repository.WaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WaveService {

    private WaveRepository repository;

    @Autowired
    public WaveService(WaveRepository repository) {
        this.repository = repository;
    }

    public Wave save(Wave wave) {
        return repository.save(wave);
    }

    public void delete(Wave wave) {
        repository.delete(wave);
    }

    public Optional<Wave> findById(String id) {
        return repository.findById(id);
    }


}
