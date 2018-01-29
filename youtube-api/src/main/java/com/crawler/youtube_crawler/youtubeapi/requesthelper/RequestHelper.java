package com.crawler.youtube_crawler.youtubeapi.requesthelper;

import com.crawler.youtube_crawler.youtubeapi.entity.ContinuationInfo;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public interface RequestHelper {

    RequestSpecification baseGetRequest();

    RequestSpecification basePostRequest(ContinuationInfo continuationData);

    Response videoList(String query, ContinuationInfo info);
    Response videoList(String query);

    Response comments(String videoId, ContinuationInfo info);

    Response video(String videoId);

}
