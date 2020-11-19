package com.example.playlistsync.youtube.model;

import lombok.Data;

@Data
public class PlaylistItemSnippet {

    private String channelId;
    private String channelTitle;
    private String description;
    private String playlistId;
    private Long position;
    private String publishedAt;
    private String title;

}
