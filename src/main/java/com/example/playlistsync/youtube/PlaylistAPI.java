package com.example.playlistsync.youtube;

import com.example.playlistsync.youtube.model.PageInfo;
import com.example.playlistsync.youtube.model.PlaylistItem;

import java.util.ArrayList;

public class PlaylistAPI {

    private String kind;
    private String etag;
    private String nextPageToken;
    private PageInfo pageInfo;
    private ArrayList<PlaylistItem> items;

    public PlaylistAPI() { this.items = new ArrayList<PlaylistItem>();}

    public PlaylistAPI(String kind, String etag, String nextPageToken, PageInfo pageInfo ) {
        this.kind = kind;
        this.etag = etag;
        this.nextPageToken = nextPageToken;
        this.pageInfo = pageInfo;
        this.items = new ArrayList<PlaylistItem>();
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public ArrayList<PlaylistItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<PlaylistItem> items) {
        this.items = items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
