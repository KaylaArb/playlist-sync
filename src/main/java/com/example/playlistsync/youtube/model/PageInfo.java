package com.example.playlistsync.youtube.model;

public class PageInfo {

    private Integer resultsPerPage;
    private Integer totalResults;

    public PageInfo() {}

    public PageInfo(Integer resultsPerPage, Integer totalResults) {
        this.resultsPerPage = resultsPerPage;
        this.totalResults = totalResults;
    }

    public Integer getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(Integer resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }


}
