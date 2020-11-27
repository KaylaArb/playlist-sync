package com.example.playlistsync.spotify;

import com.example.playlistsync.spotify.authorization.SpotifyAuth;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import com.wrapper.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SpotifyApiController {

    SpotifyApiService spotifyApiService;

    @Autowired
    public void setSpotifyApiService(SpotifyApiService spotifyApiService) { this.spotifyApiService = spotifyApiService; }

    @GetMapping("user-top-artists")
    public Artist[] getUserTopArtists() {
        final GetUsersTopArtistsRequest getUsersTopArtistsRequest = SpotifyAuth.spotifyApi.getUsersTopArtists()
                .time_range("medium_term")
                .limit(10)
                .offset(5)
                .build();
        try {
            final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();
            return artistPaging.getItems();
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
        }
        return new Artist[0];
    }

    @GetMapping("add-song/{name}/{songTitle}/{type}")
    public String addSong(@PathVariable String name, @PathVariable String songTitle, @PathVariable String type) {
        String[] uris = spotifyApiService.searchSong(songTitle,type).toArray(new String[0]);
        String playlistId = spotifyApiService.getPlaylistId(name);
        final AddItemsToPlaylistRequest addItemsToPlaylistRequest = SpotifyAuth.spotifyApi.addItemsToPlaylist(playlistId, uris)
                .build();
        try {
            final SnapshotResult snapshotResult = addItemsToPlaylistRequest.execute();

            System.out.println("Snapshot ID: " + snapshotResult.getSnapshotId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "Song has been added";
    };

    /**
     * Creates a new playlist in the authorized user's spotify account
     * @param name
     * @return
     */
    @GetMapping("create-playlist/{name}")
    public String createPlaylist(@PathVariable String name) {
        final CreatePlaylistRequest createPlaylistRequest = SpotifyAuth.spotifyApi.createPlaylist(spotifyApiService.getCurrentUserProfile(), name)
                .build();
        try {
            final Playlist playlist = createPlaylistRequest.execute();
            System.out.println("Name: " + playlist.getName());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "Playlist " + name + " has been created. Check your playlists. :)";
    }
}


