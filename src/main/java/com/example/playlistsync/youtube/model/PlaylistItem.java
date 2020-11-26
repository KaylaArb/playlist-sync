package com.example.playlistsync.youtube.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaylistItem {

    String etag;
    String id;
    String kind;
    @Getter
    @Setter
    PlaylistItemSnippet snippet;


}
