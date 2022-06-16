package com.zahir.url.repository;

import com.zahir.url.entity.URL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface URLRepository extends JpaRepository<URL, Long> {

    public URL findByShortURL(String shortURL);
    public void deleteByExpirationDateLessThan(LocalDateTime expirationDate);
}
