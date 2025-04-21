package dev.bedesi.sms.samplemanagementsystem.dto;

import lombok.Data;

@Data
public class SampleDTO {
    private int id;
    private String rollNo;
    private String name;

    public SampleDTO(int id, String rollNo, String name ) {
        this.id = id;
        this.name = name;
        this.rollNo = rollNo;
    }
}
