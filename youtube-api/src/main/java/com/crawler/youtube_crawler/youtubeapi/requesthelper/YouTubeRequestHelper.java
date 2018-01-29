package com.crawler.youtube_crawler.youtubeapi.requesthelper;

import com.crawler.youtube_crawler.youtubeapi.entity.ContinuationInfo;
import com.crawler.youtube_crawler.youtubeapi.proxy.Proxy;
import com.crawler.youtube_crawler.youtubeapi.proxy.ProxyGenerator;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.jayway.restassured.RestAssured.given;

@Component
@RequiredArgsConstructor
public class YouTubeRequestHelper implements RequestHelper {
    private static final String URI = "https://www.youtube.com";

    private final ProxyGenerator proxyGenerator;

    @Setter
    @Value("${request-helper.timeout}")
    private int timeout;

    @Override
    public RequestSpecification baseGetRequest() {
        Proxy proxy = proxyGenerator.next();
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (Exception e) {

        }
        return given()
                .proxy(proxy.getHost(), proxy.getPort())
//                .log().all()
                .header("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:58.0) Gecko/20100101 Firefox/58.0")
                .accept("*/*")
                .header("Accept-Language", "ru,en-US;q=0.5")
                .header("X-YouTube-Client-Name", "1")
                .header("X-YouTube-Client-Version", "2.20180125")
                .header("DNT", "1")
                .baseUri(URI)
                .queryParam("pbj", "1");
    }


    @Override
    public RequestSpecification basePostRequest(ContinuationInfo info) {
        return baseGetRequest()
                .contentType("application/x-www-form-urlencoded")
                .queryParam("ctoken", info.getCtoken())
                .queryParam("continuation", info.getContinuation())
                .queryParam("itct", info.getItct())
                .formParam("session_token", info.getSessionToken());
    }

    @Override
    public Response videoList(String query, ContinuationInfo info) {
        Response response = basePostRequest(info)
                .queryParam("search_query", query)
                .post("results");
//        response.then().log().all();
        if (response.getStatusCode() != 200) proxyGenerator.forceNext();

        return response;
    }

    @Override
    public Response videoList(String query) {
        Response response = baseGetRequest()
                .queryParam("search_query", query)
                .get("results");

//        response.then().log().all();
        if (response.getStatusCode() != 200) proxyGenerator.forceNext();
        return response;
    }

    @Override
    public Response comments(String videoId, ContinuationInfo info) {
        Response response = basePostRequest(info)
                .header("Cookie", info.getCookie())
                .queryParam("action_get_comments", "1")
                .post("comment_service_ajax");

//        response.then().log().all();
        if (response.getStatusCode() != 200) proxyGenerator.forceNext();
        return response;
    }

    @Override
    public Response video(String videoId) {
        Response response = baseGetRequest()
                .header("Host", "www.youtube.com")
                .queryParam("v", videoId)
                .get("watch");

        if (response.getStatusCode() != 200) proxyGenerator.forceNext();
//        response.then().log().all();

        return response;
    }
}
