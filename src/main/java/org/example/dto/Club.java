package org.example.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Builder
@Data
public class Club {
    private String name;
    private String imageURL;
    private String headQuarter;
}
