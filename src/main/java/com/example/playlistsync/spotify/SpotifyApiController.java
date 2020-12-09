package com.example.playlistsync.spotify;

import com.example.playlistsync.spotify.authorization.SpotifyAuth;
import com.example.playlistsync.youtube.YoutubeAPIService;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.follow.legacy.UnfollowPlaylistRequest;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import com.wrapper.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/spotify")
public class SpotifyApiController {

    SpotifyApiService spotifyApiService;

    @Autowired
    public void setSpotifyApiService(SpotifyApiService spotifyApiService) { this.spotifyApiService = spotifyApiService; }

    YoutubeAPIService youtubeAPIService;

    @Autowired
    public void setYoutubeAPIService(YoutubeAPIService youtubeAPIService) { this.youtubeAPIService = youtubeAPIService; }

    @GetMapping("user")
    public User getCurrentUserProfile() {
        return spotifyApiService.getCurrentUserProfile();
    }

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

    @GetMapping("user-playlists")
    public PlaylistSimplified[] getListOfCurrentUsersPlaylists() {
        final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = SpotifyAuth.spotifyApi.getListOfCurrentUsersPlaylists()
                .build();
        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();
            return playlistSimplifiedPaging.getItems();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return new PlaylistSimplified[0];
    }


    /**
     * Creates a new playlist in the authorized user's spotify account
     * @param name
     * @return
     */
    @GetMapping("create-playlist/{name}")
    public String createPlaylist(@PathVariable String name) {
        String user = spotifyApiService.getCurrentUserProfile().getDisplayName();
        final CreatePlaylistRequest createPlaylistRequest = SpotifyAuth.spotifyApi.createPlaylist(user, name)
                .build();
        try {
            final Playlist playlist = createPlaylistRequest.execute();
            System.out.println("Name: " + playlist.getName());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return name + " has been created. Check your playlists. :)";
    }

    /**
     * Adds songs from youtube into a specific spotify playlist
     * @param playlistName
     * @return
     */
    @GetMapping("add-songs/{playlistName}/{youtubeId}")
    public String addSongs(@PathVariable String playlistName, @PathVariable String youtubeId) {
        HashMap<String, ArrayList<String>> getPlaylist = youtubeAPIService.getSongsByArtistAndTitle(youtubeId);
        String[] uris = spotifyApiService.searchSongs(getPlaylist).toArray(new String[0]);
        String playlistId = spotifyApiService.getPlaylistId(playlistName);
        final AddItemsToPlaylistRequest addItemsToPlaylistRequest = SpotifyAuth.spotifyApi.addItemsToPlaylist(playlistId, uris)
                .build();
        try {
            final SnapshotResult snapshotResult = addItemsToPlaylistRequest.execute();
            System.out.println("Snapshot ID: " + snapshotResult.getSnapshotId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "Songs have been added";
    };

    @GetMapping("delete-playlist/{playlistId}")
    public String deletePlaylist(@PathVariable String playlistId) {
        String ownerId = spotifyApiService.getCurrentUserProfile().getId();
        UnfollowPlaylistRequest unfollowPlaylistRequest = SpotifyAuth.spotifyApi.unfollowPlaylist(ownerId, playlistId)
                .build();

        try {
            final String string = unfollowPlaylistRequest.execute();
            System.out.println("Null: " + string);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "sucess";
    }

}


