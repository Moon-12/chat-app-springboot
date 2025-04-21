package dev.bedesi.sms.samplemanagementsystem.mysql.repository;

import dev.bedesi.sms.samplemanagementsystem.mysql.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SampleRepository extends JpaRepository<SampleEntity,Integer> {
    @Query("SELECT s FROM SAMPLE s WHERE s.id = :id AND s.active = true")
    Optional<SampleEntity> findById(@Param("id") int id);
}
