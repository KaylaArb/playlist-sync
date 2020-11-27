package com.example.playlistsync.spotify.authorization;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import lombok.Data;
import java.net.URI;

@Data
public class SpotifyAuth {

    private static final String clientId = "f0e7c7f529464a69b6011881f225d657";
    private static final String clientSecret = "78fc07921bc3403d937c2a7551bdfdeb";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/api/get-user-code");
    public static String code = "";

    public static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

}
