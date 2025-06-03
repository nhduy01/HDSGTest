package com.example.HDSGTest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "system_setting")
public class SystemSetting {

    @Id
    @Column(length = 100)
    private String key;

    @Column(length = 255)
    private String value;

    @Column(length = 255)
    private String description;

}
