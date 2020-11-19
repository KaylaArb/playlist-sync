package com.example.playlistsync.youtube.model;

import lombok.Data;

@Data
public class PlaylistItem {

    private String etag;
    private String id;
    private String kind;
    private PlaylistItemSnippet snippet;

}
