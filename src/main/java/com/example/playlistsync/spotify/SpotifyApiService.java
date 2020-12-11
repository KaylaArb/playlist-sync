package com.example.playlistsync.spotify;

import com.example.playlistsync.spotify.authorization.SpotifyAuth;
import com.example.playlistsync.youtube.YoutubeAPIService;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.follow.legacy.UnfollowPlaylistRequest;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import com.wrapper.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import com.wrapper.spotify.requests.data.search.SearchItemRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class SpotifyApiService {

    YoutubeAPIService youtubeAPIService;

    @Autowired
    public void setYoutubeAPIService(YoutubeAPIService youtubeAPIService) { this.youtubeAPIService = youtubeAPIService; }

    public User getCurrentUserProfile() {
        final GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = SpotifyAuth.spotifyApi.getCurrentUsersProfile()
                .build();
        try {
            final User user = getCurrentUsersProfileRequest.execute();
            return user;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    private String getPlaylistId(String name) {
        String result = null;
        final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = SpotifyAuth.spotifyApi.getListOfCurrentUsersPlaylists()
                .build();
        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();
            for (PlaylistSimplified i : playlistSimplifiedPaging.getItems()){
                if (i.getName().equals(name)) {
                    result = i.getId();
                } else {
                    System.out.println("no matches");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return result;
    }

    private ArrayList<String> searchSongs(HashMap<String, ArrayList<String>> titles) {
        ArrayList<String> uri = new ArrayList<String>();
        titles.forEach((k, v) -> {
            for (String song: v) {
                String q = song + " artist:" + k;
                final SearchItemRequest searchItemRequest = SpotifyAuth.spotifyApi.searchItem(q, "track")
                        .limit(1)
                        .build();
                SearchResult searchResult = null;
                try {
                    searchResult = searchItemRequest.execute();
                    for (Track s : searchResult.getTracks().getItems()) {
                        uri.add(s.getUri());
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        });
        return uri;
    }

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

    public PlaylistSimplified[] getListOfCurrentUsersPlaylists() { // move to service
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

    public String createPlaylist(String name) {
        System.out.println(">>>>> "+name);
        String user = getCurrentUserProfile().getDisplayName();
        final CreatePlaylistRequest createPlaylistRequest = SpotifyAuth.spotifyApi.createPlaylist(user, name)
                .build();
        try {
            final Playlist playlist = createPlaylistRequest.execute();
            System.out.println("Name: " + playlist.getName());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "success";
    }

    public String addSongs(String playlistName, String youtubeId) {
        HashMap<String, ArrayList<String>> getPlaylist = youtubeAPIService.getSongsByArtistAndTitle(youtubeId);
        String[] uris = searchSongs(getPlaylist).toArray(new String[0]);
        String playlistId = getPlaylistId(playlistName);
        final AddItemsToPlaylistRequest addItemsToPlaylistRequest = SpotifyAuth.spotifyApi.addItemsToPlaylist(playlistId, uris)
                .build();
        try {
            final SnapshotResult snapshotResult = addItemsToPlaylistRequest.execute();
            System.out.println("Snapshot ID: " + snapshotResult.getSnapshotId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "Songs have been added";
    }

    public String deletePlaylist(@PathVariable String playlistId) {
        String ownerId = getCurrentUserProfile().getId();
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
