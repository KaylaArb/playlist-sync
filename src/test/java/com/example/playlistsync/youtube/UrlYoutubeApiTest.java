package com.example.playlistsync.youtube;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UrlYoutubeApiTest {

    YoutubeAPIService youtubeAPIService;

    @Autowired
    public void setYoutubeAPIService(YoutubeAPIService youtubeAPIService) { this.youtubeAPIService = youtubeAPIService; }

    @Test
    public void checkURLTest() {
        String expectedURL= "https://youtube.googleapis.com/youtube/v3/playlistItems?playlistId=PL7Q2ZklqtR8CyOXdWk9PumZasCwi60wsl&key=AIzaSyBgPvC12sylwDME_RhDUIE9gF2hMwik3aQ&part=snippet&maxResults=50";
        assertEquals(expectedURL, youtubeAPIService.buildURL(null), "Must return " + expectedURL);
    }

}
