package org.example.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class Staff {
    private String name;
    private String imageURL;
    private String role;
}
