package com.crawler.youtube_crawler.youtubeapi.parser;

import com.crawler.youtube_crawler.youtubeapi.entity.ContinuationInfo;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PROTECTED)
public abstract class YouTubeParser<T> {

    private Response response;

    private JsonPath jsonPath;

    public YouTubeParser(Response response) {
        this.response = response;
        jsonPath = response.jsonPath();
    }

    public abstract T parse(String videoId) throws Exception;

    public ContinuationInfo parseContinuationData() throws Exception {
        ContinuationInfo continuationData = new ContinuationInfo();

        continuationData.setContinuation(jsonPath.get(getPathToContinuation()));
        continuationData.setCtoken(jsonPath.get(getPathToCtoken()));
        continuationData.setItct(jsonPath.get(getPathToItct()));
        continuationData.setSessionToken(jsonPath.get(getPathToSessionToken()));
        continuationData.setCookie(preapareCookie());

        return continuationData;
    }

    protected abstract String getPathToSessionToken();

    protected abstract String getPathToContinuation();

    protected abstract String getPathToCtoken();

    protected abstract String getPathToItct();

    protected abstract String preapareCookie();
}
