package com.zahir.url.service;

import com.zahir.url.dto.URLDto;
import com.zahir.url.entity.URL;
import com.zahir.url.repository.URLRepository;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
@Slf4j
@PropertySource("classpath:app.properties")
public class URLServiceImpl implements IURLService {

    @Autowired
    private URLRepository urlRepository;

    @Value("${purge.after.inMins}")
    private int purgeAfter;

    private URL saveURL(URL url){
        log.info("Inside saveURL of URLService");
        return urlRepository.save(url);
    }

    @Override
    public URL generateShortURL(URLDto urlDto) {
        if(StringUtils.isNotEmpty(urlDto.getUrl())){
            String encodedUrl = encodeUrl(urlDto.getUrl());
            URL url = new URL();
            url.setCreationDate(LocalDateTime.now());
            url.setOriginalURL(urlDto.getUrl());
            url.setShortURL(encodedUrl);
            url.setExpirationDate(url.getCreationDate().plusMinutes(purgeAfter));

            return saveURL(url);
        }
        return null;
    }

    /**
     *
     * @param url
     * @return String : encodedURL
     */
    private String encodeUrl(String url){
        String encodedUrl = "";
        LocalDateTime time = LocalDateTime.now();
        encodedUrl = Hashing.murmur3_32()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();
        return  encodedUrl;
    }

    @Override
    public URL getOriginalURL(String shortURL) {
        return urlRepository.findByShortURL(shortURL);
    }

    @Override
    public void purgeURL(URL url) {
        urlRepository.deleteByExpirationDateLessThan(LocalDateTime.now());
    }
}
