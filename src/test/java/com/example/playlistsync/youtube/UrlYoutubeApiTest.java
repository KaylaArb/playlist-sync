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
    public void checkURLTest1() {
        String youtubeUrl = "PLCB4F961F723051AC";
        String expectedURL= "https://youtube.googleapis.com/youtube/v3/playlistItems?playlistId=PLCB4F961F723051AC&key=AIzaSyBgPvC12sylwDME_RhDUIE9gF2hMwik3aQ&part=snippet&maxResults=50";
        assertEquals(expectedURL, youtubeAPIService.buildURL(null, youtubeUrl), "Must return " + expectedURL);
    }

    @Test
    public void checkURLTest2() {
        String youtubeUrl = "PL4o29bINVT4EG_y-k5jGoOu3-Am8Nvi10";
        String expectedURL= "https://youtube.googleapis.com/youtube/v3/playlistItems?playlistId=PL4o29bINVT4EG_y-k5jGoOu3-Am8Nvi10&key=AIzaSyBgPvC12sylwDME_RhDUIE9gF2hMwik3aQ&part=snippet&maxResults=50";
        assertEquals(expectedURL, youtubeAPIService.buildURL(null, youtubeUrl), "Must return " + expectedURL);
    }

    @Test
    public void checkURLTest3() {
        String youtubeUrl = "PLCB4F961F723051AC";
        String expectedURL= "https://youtube.googleapis.com/youtube/v3/playlistItems?playlistId=PLCB4F961F723051AC&key=AIzaSyBgPvC12sylwDME_RhDUIE9gF2hMwik3aQ&part=snippet&maxResults=50";
        assertEquals(expectedURL, youtubeAPIService.buildURL(null, youtubeUrl), "Must return " + expectedURL);
    }



}
