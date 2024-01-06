package org.example.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class Stadium {
    private String name;
    private String address;
    private String imageURL;
}
