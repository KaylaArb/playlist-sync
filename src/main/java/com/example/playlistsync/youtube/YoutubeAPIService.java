package com.example.playlistsync.youtube;

import com.example.playlistsync.youtube.model.PlaylistItem;
import com.example.playlistsync.youtube.model.PlaylistItemSnippet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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

    public HashMap<String, ArrayList<String>> getSongByArtistAndTitle() {
        HashMap<String, ArrayList<String>> titleByArtist = new HashMap<String, ArrayList<String>>();
        for (PlaylistItem playlistItem : callAPI().getItems()) {
            String[] details = playlistItem.getSnippet().getTitle().split("-");
            if (details.length == 2) {
                String artist = details[0].trim();
                if (!titleByArtist.containsKey(artist)) {
                    titleByArtist.put(artist, new ArrayList<String>());
                }
                titleByArtist.get(artist).add(details[1].trim());
            } else {
                System.out.println("Wrong title format for: " + details[0]);
            }
        }
        return titleByArtist;
    }

    public Set<String> getArtists() {
        return getSongByArtistAndTitle().keySet();
    }

    public ArrayList<String> getSongTitle() {
        ArrayList<String> songTitles = new ArrayList<String>();
        HashMap<String, ArrayList<String>> titleByArtist = getSongByArtistAndTitle();
        if (!titleByArtist.isEmpty()) {
            for (String artist : titleByArtist.keySet()) {
                songTitles.addAll(titleByArtist.get(artist));
            }
        }
        return songTitles;
    }
}
