package com.example.playlistsync.youtube;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

}
