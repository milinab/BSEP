package com.example.demo.dto;

import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class IssuerDTO {
    @Generated
    @Id
    private String id;
    private String serialNumber;
    private String commonName;
}
