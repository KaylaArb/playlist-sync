package com.example.playlistsync.youtube;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class YoutubeAPIController {

    YoutubeAPIService youtubeAPIService;

    @Autowired
    public void setYoutubeAPIService(YoutubeAPIService youtubeAPIService) { this.youtubeAPIService = youtubeAPIService; }

//    @GetMapping("/titles_by_artist")
//    public HashMap<String, ArrayList<String>> getSongByArtistAndTitle() {
//        return youtubeAPIService.getSongsByArtistAndTitle();
//    }

}
