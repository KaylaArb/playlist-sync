package com.example.playlistsync.spotify.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/api")
public class SpotifyAuthController {

    SpotifyAuthService spotifyAuthService;

    @Autowired
    public void setSpotifyAuthService(SpotifyAuthService spotifyAuthService) { this.spotifyAuthService = spotifyAuthService; }

    @GetMapping("login")
    @ResponseBody
    public String spotifyLogin() {
        return spotifyAuthService.spotifyLogin();
    }

    @GetMapping("get-user-code")
    public String getSpotifyUserCode(@RequestParam("code") String userCode, HttpServletResponse response) throws IOException {
        return spotifyAuthService.getSpotifyUserCode(userCode, response);
    }

}
