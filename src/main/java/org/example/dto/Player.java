package org.example.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
@Builder
@Data
@ToString
public class Player {
    private String name;
    private String imageURL;
    private String dateOfBirth;
    private int heightCm;
    private int weightKg;
    private int number;
    private String role;

}
