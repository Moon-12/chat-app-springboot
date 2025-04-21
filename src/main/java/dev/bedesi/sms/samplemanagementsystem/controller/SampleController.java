package dev.bedesi.sms.samplemanagementsystem.controller;

import dev.bedesi.sms.samplemanagementsystem.mysql.entity.SampleEntity;
import dev.bedesi.sms.samplemanagementsystem.service.SampleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sms/sample")
public class SampleController {
    @Autowired
    SampleService sampleService;

    @GetMapping("/{id}")
    public ResponseEntity<SampleEntity> getSampleEntityById(@PathVariable int id){
        return sampleService.getSampleEntityById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<SampleEntity> getAllSampleEntities() {
        return sampleService.getAllSampleEntities();
    }

    @PostMapping
    public SampleEntity createSampleEntity(@RequestBody SampleEntity sampleEntity) {
        return sampleService.createSampleEntity(sampleEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSampleEntity(@PathVariable int id) {
        try {
            sampleService.deleteSampleEntity(id);
            return ResponseEntity.noContent().build(); // 204 No Content on success
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found if student doesn't exist or is inactive
        }
    }

}
