package com.example.playlistsync.youtube.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddPlaylist {

    String spotifyPlaylist;
    String youtubeUrl;
}
