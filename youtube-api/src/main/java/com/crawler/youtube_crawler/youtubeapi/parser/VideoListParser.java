package com.crawler.youtube_crawler.youtubeapi.parser;

import com.jayway.restassured.response.Response;

import java.util.List;

public abstract class VideoListParser extends YouTubeParser<List<String>> {

    public VideoListParser(Response response) {
        super(response);
    }

    @Override
    public List<String> parse(String videoId) throws Exception {
        return getJsonPath().get(getPathToVideoList());
    }

    protected abstract String getPathToVideoList();
    
}
