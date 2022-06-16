package com.zahir.url.controller;

import com.zahir.url.dto.URLDto;
import com.zahir.url.dto.URLErrorResponseDto;
import com.zahir.url.dto.URLResponseDto;
import com.zahir.url.dto.converter.URLDtoConverter;
import com.zahir.url.entity.URL;
import com.zahir.url.service.URLServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@Slf4j
@EnableScheduling
public class URLController {

    @Autowired
    private URLServiceImpl urlServiceImpl;

    @PostMapping("/short")
    public ResponseEntity<?> saveURL(@RequestParam("url") String originalURL){
        log.info("Inside saveURL of URLController");

        URLDto urlDto = URLDtoConverter.getURLDto(originalURL);

        URL url = urlServiceImpl.generateShortURL(urlDto);

        if(null != url){
            URLResponseDto urlResponseDto = URLDtoConverter.getURLResponseDto(url);
            return new ResponseEntity<URLResponseDto>(urlResponseDto, HttpStatus.OK);
        }

        URLErrorResponseDto urlErrorResponseDto = URLDtoConverter.getURLErrorResponseDto(String.valueOf(HttpStatus.NOT_FOUND)
                , "There was an Error processing the request.");
        return new ResponseEntity<URLErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
    }

    @GetMapping("/long")
    public ResponseEntity<?> getOriginalURL(@RequestParam("tiny") String shortURL){
        URL url = urlServiceImpl.getOriginalURL(shortURL);

        if(null!= url){
            URLResponseDto urlResponseDto = URLDtoConverter.getURLResponseDto(url);
            return new ResponseEntity<URLResponseDto>(urlResponseDto, HttpStatus.OK);
        }
        URLErrorResponseDto urlErrorResponseDto = URLDtoConverter.getURLErrorResponseDto(String.valueOf(HttpStatus.NOT_FOUND)
                , "Short URL Not found, it may have expired.");
        return new ResponseEntity<URLErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
    }
}
