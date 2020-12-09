package com.example.playlistsync.youtube;

import com.example.playlistsync.youtube.model.PlaylistItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class YoutubeAPIService {

    private final String APIKey;
    private final String url;
    private final String youtubeFilter;
    private RestTemplate restTemplate;

    public YoutubeAPIService(@Value("${YOUTUBE_API_KEY}") String APIKey, @Value("${YOUTUBE_URL}") String url,@Value("${YOUTUBE_FILTER}") String youtubeFilter, RestTemplate restTemplate) {
        this.APIKey = APIKey;
        this.url = url;
        this.youtubeFilter = youtubeFilter;
        this.restTemplate = restTemplate;
    }

    private PlaylistAPI callAPI(String pageToken, String youtubeId) {
        String url = buildURL(pageToken, youtubeId);
        System.out.println(url);
        PlaylistAPI playlistAPI = restTemplate.getForObject( url, PlaylistAPI.class);
        return playlistAPI;
    }

    private ArrayList<PlaylistItem> getPlaylist(String youtubeId){
        ArrayList<PlaylistItem> playlist = new ArrayList<>();
        PlaylistAPI playlistAPI = callAPI(null, youtubeId);
        playlist.addAll(playlistAPI.getItems());
        while (playlistAPI.getNextPageToken() != null) {
            playlistAPI = callAPI(playlistAPI.getNextPageToken(), youtubeId);
            playlist.addAll(playlistAPI.getItems());
        }
        System.out.println("Size of playlist = " + playlist.size());
        return playlist;
    }

    public String buildURL(String pageToken, String youtubeId) {
        String url = this.url + "?playlistId=" + youtubeId + "&key=" + APIKey + youtubeFilter;
        return pageToken == null ? url : url + "&pageToken=" + pageToken;
    }

    public HashMap<String, ArrayList<String>> getSongsByArtistAndTitle(String youtubeId) {
        HashMap<String, ArrayList<String>> artistAndSongsList = new HashMap<>();
        for (PlaylistItem playlist : getPlaylist(youtubeId)) {
            String[] title = playlist.getSnippet().getTitle().split("-");
            if (title.length == 2) {
                String artist = title[0].trim();
                String[] song = title[1].split(Pattern.quote("("));
                String[] songEdit = song[0].split(Pattern.quote("["));
                if (!artistAndSongsList.containsKey(artist)){
                    ArrayList<String> songs = new ArrayList<String>();
                    songs.add(songEdit[0].trim());
                    artistAndSongsList.put(artist, songs);
                } else {
                    artistAndSongsList.get(artist).add(songEdit[0].trim());
                }
            } else {
                System.out.println(title[0] + " --- does not have the appropriate [artist - song] name layout");
            }
        }
        System.out.println("# of Artists = " + artistAndSongsList.size());
        return artistAndSongsList;
    }

}
