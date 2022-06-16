package com.zahir.url.dto.converter;

import com.zahir.url.dto.URLDto;
import com.zahir.url.dto.URLErrorResponseDto;
import com.zahir.url.dto.URLResponseDto;
import com.zahir.url.entity.URL;

/**
 * Helper class to convert to Dto
 */
public class URLDtoConverter {

    public static URLDto getURLDto(String url){
        return new URLDto(url);
    }

    public static URLResponseDto getURLResponseDto(URL url){

        URLResponseDto urlResponseDto = new URLResponseDto();
        urlResponseDto.setOriginalUrl(url.getOriginalURL());
        urlResponseDto.setShortURL(url.getShortURL());

        return urlResponseDto;
    }

    public static URLErrorResponseDto getURLErrorResponseDto(String status, String errorMsg){

        URLErrorResponseDto urlErrorResponseDto = new URLErrorResponseDto();
        urlErrorResponseDto.setStatus(String.valueOf(status));
        urlErrorResponseDto.setErrorMessage(errorMsg);

        return urlErrorResponseDto;
    }
}
