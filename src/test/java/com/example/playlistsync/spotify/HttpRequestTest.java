package com.example.playlistsync.spotify;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.playlistsync.spotify.authorization.SpotifyAuth;
import com.example.playlistsync.youtube.YoutubeAPIService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SpotifyApiController.class)
public class HttpRequestTest {

    SpotifyAuth spotifyAuth;

    @Autowired
    public void setSpotifyAuth(SpotifyAuth spotifyAuth) { this.spotifyAuth = spotifyAuth; }



    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpotifyApiService service;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        this.mockMvc.perform(get("/spotify/user")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, Mock")));
    }
}
