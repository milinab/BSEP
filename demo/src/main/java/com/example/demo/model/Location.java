package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Location {
    private int postalCode;
    private String city;
    private String state;
    private String country;
    private String streetName;
    private String streetNumber;
}
