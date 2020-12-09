package com.example.playlistsync.spotify;

import com.wrapper.spotify.model_objects.specification.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spotify")
public class SpotifyApiController {

    SpotifyApiService spotifyApiService;

    @Autowired
    public void setSpotifyApiService(SpotifyApiService spotifyApiService) { this.spotifyApiService = spotifyApiService; }


    @GetMapping("user")
    public User getCurrentUserProfile() {
        return spotifyApiService.getCurrentUserProfile();
    }

    @GetMapping("user-top-artists")
    public Artist[] getUserTopArtists() {
        return spotifyApiService.getUserTopArtists();
    }

    @GetMapping("user-playlists")
    public PlaylistSimplified[] getListOfCurrentUsersPlaylists() { // move to service
        return spotifyApiService.getListOfCurrentUsersPlaylists();
    }

    @GetMapping("create-playlist/{name}")
    public String createPlaylist(@PathVariable String name) {
        return spotifyApiService.createPlaylist(name);
    }

    @GetMapping("add-songs/{playlistName}/{youtubeId}")
    public String addSongs(@PathVariable String playlistName, @PathVariable String youtubeId) {
        return spotifyApiService.addSongs(playlistName,youtubeId);
    };

    @GetMapping("delete-playlist/{playlistId}")
    public String deletePlaylist(@PathVariable String playlistId) {
        return spotifyApiService.deletePlaylist(playlistId);
    }

}


