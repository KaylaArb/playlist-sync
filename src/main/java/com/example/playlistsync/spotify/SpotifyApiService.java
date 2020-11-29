package com.example.playlistsync.spotify;

import com.example.playlistsync.spotify.authorization.SpotifyAuth;
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
import java.util.HashMap;

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

    public ArrayList<String> searchSong(HashMap<String, ArrayList<String>> titles) {
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

}
