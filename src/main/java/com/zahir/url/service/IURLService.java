package com.zahir.url.service;

import com.zahir.url.dto.URLDto;
import com.zahir.url.entity.URL;
import org.springframework.stereotype.Service;

@Service
public interface IURLService {

    public URL generateShortURL(URLDto urlDto);
    public URL getOriginalURL(String url);
    public void purgeURL(URL url);
}
