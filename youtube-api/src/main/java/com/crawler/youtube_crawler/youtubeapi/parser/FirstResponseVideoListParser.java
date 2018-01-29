package com.crawler.youtube_crawler.youtubeapi.parser;

import com.jayway.restassured.response.Response;

public class FirstResponseVideoListParser extends VideoListParser {

    private static final String PATH_TO_VIDEO_LIST = "[1].response.contents.twoColumnSearchResultsRenderer.primaryContents.sectionListRenderer.contents[0].itemSectionRenderer.contents.videoRenderer.videoId";

    private static final String PATH_TO_SESSION_TOKEN = "[1].xsrf_token";
    private static final String PATH_TO_CONTINUATION = "[1].response.contents.twoColumnSearchResultsRenderer.primaryContents.sectionListRenderer.contents[0].itemSectionRenderer.continuations[0].nextContinuationData.continuation";
    private static final String PATH_TO_CTOKEN = PATH_TO_CONTINUATION;
    private static final String PATH_TO_ITCT = "[1].response.contents.twoColumnSearchResultsRenderer.primaryContents.sectionListRenderer.contents[0].itemSectionRenderer.continuations[0].nextContinuationData.clickTrackingParams";

    public FirstResponseVideoListParser(Response response) {
        super(response);
    }

    @Override
    protected String getPathToSessionToken() {
        return PATH_TO_SESSION_TOKEN;
    }

    @Override
    protected String getPathToContinuation() {
        return PATH_TO_CONTINUATION;
    }

    @Override
    protected String getPathToCtoken() {
        return PATH_TO_CTOKEN;
    }

    @Override
    protected String getPathToItct() {
        return PATH_TO_ITCT;
    }

    @Override
    protected String preapareCookie() {
        return "";
    }

    @Override
    protected String getPathToVideoList() {
        return PATH_TO_VIDEO_LIST;
    }
}
