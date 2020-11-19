package com.example.playlistsync.youtube;

import com.example.playlistsync.youtube.model.PageInfo;
import com.example.playlistsync.youtube.model.PlaylistItem;
import lombok.Data;

import java.util.ArrayList;

@Data
public class PlaylistAPI {

    private String kind;
    private String etag;
    private String nextPageToken;
    private PageInfo pageInfo;
    private ArrayList<PlaylistItem> items;

    public PlaylistAPI() { this.items = new ArrayList<PlaylistItem>();}

}
