package com.example.playlistsync.youtube.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageInfo {

    Integer resultsPerPage;
    Integer totalResults;

}
