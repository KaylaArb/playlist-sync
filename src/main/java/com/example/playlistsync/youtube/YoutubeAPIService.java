package com.example.playlistsync.youtube;

import com.example.playlistsync.youtube.model.PlaylistItem;
import com.example.playlistsync.youtube.model.PlaylistItemSnippet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Pattern;

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

    private PlaylistAPI callAPI(String pageToken) {
        String url = buildURL(pageToken);
        System.out.println(url);
        PlaylistAPI playlistAPI = restTemplate.getForObject( url, PlaylistAPI.class);
        return playlistAPI;
    }

    private ArrayList<PlaylistItem> getPlaylist(){
        ArrayList<PlaylistItem> playlist = new ArrayList<PlaylistItem>();
        PlaylistAPI playlistAPI = callAPI(null);
        playlist.addAll(playlistAPI.getItems());
        while (playlistAPI.getNextPageToken() != null) {
            playlistAPI = callAPI(playlistAPI.getNextPageToken());
            playlist.addAll(playlistAPI.getItems());
        }
        System.out.println("Size of playlist = " + playlist.size());
        return playlist;
    }

    public String buildURL(String pageToken) {
        String url = this.url + "?playlistId=PL05E1623111A9A860" + "&key=" + APIKey + "&part=snippet&maxResults=50";
        return pageToken == null ? url : url + "&pageToken=" + pageToken;
    }

    public HashMap<String, ArrayList<String>> getSongsByArtistAndTitle() {
        HashMap<String, ArrayList<String>> artistAndSongsList = new HashMap<String, ArrayList<String>>();
        for (PlaylistItem playlist : getPlaylist()) {
            String[] title = playlist.getSnippet().getTitle().split("-");
            if (title.length == 2) {
                String artist = title[0].trim();
                String[] song = title[1].split(Pattern.quote("("));
                String[] songEdit = song[0].split(Pattern.quote("["));
                if (!artistAndSongsList.containsKey(artist)){
                    ArrayList<String> songs = new ArrayList<String>();
                    songs.add(songEdit[0].trim());
                    artistAndSongsList.put(artist, songs);
                } else {
                    artistAndSongsList.get(artist).add(songEdit[0].trim());
                }
            } else {
                System.out.println(title[0] + " --- does not have the appropriate [artist - song] name layout");
            }
        }
        System.out.println("# of Artists = " + artistAndSongsList.size());
        return artistAndSongsList;
    }

}
