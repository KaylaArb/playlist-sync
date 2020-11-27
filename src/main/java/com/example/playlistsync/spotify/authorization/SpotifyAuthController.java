package com.example.playlistsync.spotify.authorization;

import com.example.playlistsync.spotify.authorization.SpotifyAuth;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class SpotifyAuthController {


    @GetMapping("login")
    @ResponseBody
    public String spotifyLogin() {
        //scopes for user to approve consent
        AuthorizationCodeUriRequest authorizationCodeUriRequest = SpotifyAuth.spotifyApi.authorizationCodeUri()
                .scope("user-read-private, user-read-email, user-top-read, playlist-read-private, playlist-read-collaborative, playlist-modify-public, playlist-modify-private")
                .show_dialog(true)
                .build();
        final URI uri =authorizationCodeUriRequest.execute();
        System.out.println(uri);
        return uri.toString();
    }

    @GetMapping("get-user-code")
    public String getSpotifyUserCode(@RequestParam("code") String userCode, HttpServletResponse response) throws IOException {
        SpotifyAuth.code = userCode;
        AuthorizationCodeRequest authorizationCodeRequest = SpotifyAuth.spotifyApi.authorizationCode(SpotifyAuth.code)
                .build();
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
            SpotifyAuth.spotifyApi.setAccessToken((authorizationCodeCredentials.getAccessToken()));
            SpotifyAuth.spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        response.sendRedirect("http://localhost:3000/playlists");
        System.out.println("access token: " + SpotifyAuth.spotifyApi.getAccessToken());
        return SpotifyAuth.spotifyApi.getAccessToken();
    }

}
