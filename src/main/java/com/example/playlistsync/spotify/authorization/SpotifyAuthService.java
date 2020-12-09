package com.example.playlistsync.spotify.authorization;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@Service
public class SpotifyAuthService {

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

    public String getSpotifyUserCode(String userCode, HttpServletResponse response) throws IOException {
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
        response.sendRedirect("http://localhost:3000/user-dashboard");
        System.out.println("access token: " + SpotifyAuth.spotifyApi.getAccessToken());
        return SpotifyAuth.spotifyApi.getAccessToken();
    }
}
