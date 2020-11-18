package com.example.playlistsync.youtube.model;

public class PlaylistItem {

    private String etag;
    private String id;
    private String kind;
    private PlaylistItemSnippet snippet;

    public PlaylistItem() { }

    public PlaylistItem(String etag, String id, String kind, PlaylistItemSnippet snippet) {
        this.etag = etag;
        this.id = id;
        this.kind = kind;
        this.snippet = snippet;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public PlaylistItemSnippet getSnippet() {
        return snippet;
    }

    public void setSnippet(PlaylistItemSnippet snippet) {
        this.snippet = snippet;
    }

}
