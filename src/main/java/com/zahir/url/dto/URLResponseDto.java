package com.zahir.url.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class URLResponseDto {

    private String originalUrl;
    private String shortURL;
}
