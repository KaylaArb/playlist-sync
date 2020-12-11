package com.example.playlistsync.spotify;

import com.example.playlistsync.youtube.model.AddPlaylist;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrapper.spotify.model_objects.specification.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("create-playlist")
    public String createPlaylist(@RequestBody String name)  {
        System.out.println("******** " + name);
        return spotifyApiService.createPlaylist(name);
    }

    @PostMapping("add-songs")
    public String addSongs(@RequestBody String payload) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        AddPlaylist addPlaylist = mapper.readValue(payload, AddPlaylist.class);
        return spotifyApiService.addSongs(addPlaylist.getSpotifyPlaylist(),addPlaylist.getYoutubeUrl());
    }

    @DeleteMapping("delete-playlist")
    public String deletePlaylist(@RequestBody String playlistId) {
        return spotifyApiService.deletePlaylist(playlistId);
    }
}



