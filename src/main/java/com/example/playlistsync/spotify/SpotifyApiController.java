package com.example.playlistsync.spotify;

import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SpotifyApiController {


    @GetMapping("user-top-artists")
    public Artist[] getUserTopArtists() {
        final GetUsersTopArtistsRequest getUsersTopArtistsRequest = SpotifyAuth.spotifyApi.getUsersTopArtists()
                .time_range("medium_term")
                .limit(10)
                .offset(5)
                .build();
        try {
            final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();

//            ArrayList<Artist> artistsList = new ArrayList<Artist>();
//            for (Artist artist: artistPaging.getItems()) {
//                System.out.println(artist.toString());
//                System.out.printf(artist.getId());
//            }
//
            return artistPaging.getItems();
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
        }
        return new Artist[0];
    }

    @GetMapping("user-playlist")
    public PlaylistSimplified[] getListOfCurrentUsersPlaylists() {
        final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = SpotifyAuth.spotifyApi.getListOfCurrentUsersPlaylists()
//                .limit(10)
//                .offset(0)
                .build();
        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();

//            ArrayList<PlaylistSimplified> list = new ArrayList<PlaylistSimplified>();
//            for (PlaylistSimplified playlist: playlistSimplifiedPaging.getItems()) {
//                System.out.println(playlist.toString());
//                System.out.printf(playlist.getId());
//            }
//            System.out.println("Total: " + playlistSimplifiedPaging.getTotal());

            return playlistSimplifiedPaging.getItems();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return new PlaylistSimplified[0];
    }
}
