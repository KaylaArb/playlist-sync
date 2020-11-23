package com.example.playlistsync.youtube.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaylistItemSnippet {

    String channelId;
    String channelTitle;
    String description;
    String playlistId;
    Long position;
    String publishedAt;
    String title;

}
