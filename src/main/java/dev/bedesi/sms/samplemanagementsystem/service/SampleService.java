package dev.bedesi.sms.samplemanagementsystem.service;
import dev.bedesi.sms.samplemanagementsystem.mysql.entity.SampleEntity;
import dev.bedesi.sms.samplemanagementsystem.mysql.repository.SampleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SampleService {

    @Autowired
    private SampleRepository sampleRepository;

    public Optional<SampleEntity> getSampleEntityById(int id) {
       return sampleRepository.findById(id);
    }

    public List<SampleEntity> getAllSampleEntities() {
        return sampleRepository.findAll();
    }

    public SampleEntity createSampleEntity(SampleEntity sampleEntity) {
        return sampleRepository.save(sampleEntity);
    }

    public void deleteSampleEntity(int id) {
        // Check if entity exists with active = true
        Optional<SampleEntity> sampleOpt = sampleRepository.findById(id);

        if (sampleOpt.isPresent()) {
            // Get the entity and set active = false
            SampleEntity sampleEntity = sampleOpt.get();
            sampleEntity.setActive(false);
            sampleRepository.save(sampleEntity); // Persist the change
        } else {
            throw new EntityNotFoundException("entity with ID " + id + " not found or already inactive");
        }
    }

}
