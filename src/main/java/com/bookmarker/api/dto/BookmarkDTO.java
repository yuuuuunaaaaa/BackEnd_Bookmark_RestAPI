package com.bookmarker.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
public class BookmarkDTO {
    private Long id;
    private String title;
    private String url;
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Instant createdAt;
}