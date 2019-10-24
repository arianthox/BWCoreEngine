package com.globant.brainwaves.service;

import com.globant.brainwaves.model.Wave;
import com.globant.brainwaves.repository.WaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WaveService {

    @Autowired
    private transient WaveRepository repository;

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
