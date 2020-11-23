package com.example.playlistsync.youtube;

import com.example.playlistsync.youtube.model.PageInfo;
import com.example.playlistsync.youtube.model.PlaylistItem;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaylistAPI {

    String kind;
    String etag;
    String nextPageToken;
    PageInfo pageInfo;
    ArrayList<PlaylistItem> items;

    public PlaylistAPI() { this.items = new ArrayList<PlaylistItem>();}

}
