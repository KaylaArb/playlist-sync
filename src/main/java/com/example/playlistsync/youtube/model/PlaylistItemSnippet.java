package com.example.playlistsync.youtube.model;

public class PlaylistItemSnippet {

    private String channelId;
    private String channelTitle;
    private String description;
    private String playlistId;
    private Long position;
    private String publishedAt;
    private String title;

    public PlaylistItemSnippet() {}

    public PlaylistItemSnippet(String channelId, String channelTitle, String description, String playlistId, Long position, String publishedAt, String title) {
        this.channelId = channelId;
        this.channelTitle = channelTitle;
        this.description = description;
        this.playlistId = playlistId;
        this.position = position;
        this.publishedAt = publishedAt;
        this.title = title;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
