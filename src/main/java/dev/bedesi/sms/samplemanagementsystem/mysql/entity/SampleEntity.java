package dev.bedesi.sms.samplemanagementsystem.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="SAMPLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "roll_no")
    private String rollNo;
    private String name;
    private Boolean active=true;

}
