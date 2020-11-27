package com.example.playlistsync.spotify;

import com.example.playlistsync.spotify.authorization.SpotifyAuth;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import com.wrapper.spotify.requests.data.search.SearchItemRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class SpotifyApiService {

    public String getCurrentUserProfile() {
        String result = null;
        final GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = SpotifyAuth.spotifyApi.getCurrentUsersProfile()
                .build();
        try {
            final User user = getCurrentUsersProfileRequest.execute();
            System.out.println("Display name: " + user.getId());
            result = user.getId();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return result;
    }

    public String getPlaylistId(String name) {
        String result = null;
        final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = SpotifyAuth.spotifyApi.getListOfCurrentUsersPlaylists()
                .build();

        System.out.println("name param = " + name);
        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();
            for (PlaylistSimplified i : playlistSimplifiedPaging.getItems()){
                if (i.getName().equals(name)) {
                    System.out.println("id =" + i.getId());
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

    public ArrayList<String> searchSong(String q, String type) {
        ArrayList<String> uri = new ArrayList<String>();
        final SearchItemRequest searchItemRequest = SpotifyAuth.spotifyApi.searchItem(q, type)
                .build();
        SearchResult searchResult = null;
        try {
            searchResult = searchItemRequest.execute();
            System.out.println("Tracks: " + searchResult.getTracks().getItems());
            for (Track s : searchResult.getTracks().getItems()) {
                uri.add(s.getUri());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return uri;
    };

}
