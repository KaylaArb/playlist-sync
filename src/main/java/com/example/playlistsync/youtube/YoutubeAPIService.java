package com.example.playlistsync.youtube;

import com.example.playlistsync.youtube.model.PlaylistItem;
import com.example.playlistsync.youtube.model.PlaylistItemSnippet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class YoutubeAPIService {

    private final String APIKey;
    private final String url;
    private RestTemplate restTemplate;

    public YoutubeAPIService(@Value("${YOUTUBE_API_KEY}") String APIKey, @Value("${YOUTUBE_URL}") String url, RestTemplate restTemplate) {
        this.APIKey = APIKey;
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public PlaylistAPI callAPI() {
        String url = buildURL();
        System.out.println(url);
        PlaylistAPI playlistAPI = restTemplate.getForObject( url, PlaylistAPI.class);
        return playlistAPI;
    }

    public String buildURL() {
        return this.url + "?playlistId=PL7Q2ZklqtR8CyOXdWk9PumZasCwi60wsl" + "&key=" + APIKey + "&part=snippet&maxResults=50";
    }

    public HashMap<String, ArrayList<String>> getSongsByArtistAndTitle() {
        HashMap<String, ArrayList<String>> artistAndSongsList = new HashMap<String, ArrayList<String>>();
        for (PlaylistItem playlist : callAPI().getItems()) {
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
                System.out.println(title[0] + "does not have the appropriate artist - song name layout");
            }
        }
        return artistAndSongsList;
    }

}
