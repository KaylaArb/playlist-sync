package com.example.playlistsync.youtube;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YoutubeAPIController {

    YoutubeAPIService youtubeAPIService;

    @Autowired
    public void setYoutubeAPIService(YoutubeAPIService youtubeAPIService) { this.youtubeAPIService = youtubeAPIService; }

    @GetMapping("/playlist")
    public PlaylistAPI callAPI() {
        return youtubeAPIService.callAPI(); }

}
