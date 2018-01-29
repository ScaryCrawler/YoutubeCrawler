package com.crawler.youtube_crawler.youtubeapi.parser;

import com.crawler.youtube_crawler.youtubeapi.entity.Video;
import com.jayway.restassured.response.Response;

import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class VideoParser extends YouTubeParser<Video> {

    private static final String PATH_TO_VIDEO_ID = "[2].player.args.video_id";
    private static final String PATH_TO_AUTHOR = "[2].player.args.author";
    private static final String PATH_TO_TITLE = "[2].player.args.title";
    private static final String PATH_TO_VIEW_COUNT = "[2].player.args.view_count";
    private static final String PATH_TO_KEYWORDS = "[2].player.args.keywords";
    private static final String PATH_TO_DESCRIPTION = "[3].response.contents.twoColumnWatchNextResults.results.results.contents[1].videoSecondaryInfoRenderer.description.runs.text";
    private static final String PATH_TO_DATE = "[3].response.contents.twoColumnWatchNextResults.results.results.contents[1].videoSecondaryInfoRenderer.dateText.simpleText";
    private static final String PATH_TO_LIKES_DISLIKES = "[3].response.contents.twoColumnWatchNextResults.results.results.contents[0].videoPrimaryInfoRenderer.sentimentBar.sentimentBarRenderer.tooltip";

    private static final String LIKES_DISLIKES_SEPARATOR = "/";

    private static final String PATH_TO_SESSION_TOKEN = "[3].xsrf_token";
    private static final String PATH_TO_CONTINUATION = "[3].response.contents.twoColumnWatchNextResults.results.results.contents[2].itemSectionRenderer.continuations[0].nextContinuationData.continuation";
    private static final String PATH_TO_CTOKEN = PATH_TO_CONTINUATION;
    private static final String PATH_TO_ITCT = "[3].response.contents.twoColumnWatchNextResults.results.results.contents[2].itemSectionRenderer.continuations[0].nextContinuationData.clickTrackingParams";

    public VideoParser(Response response) {
        super(response);
    }

    @Override
    public Video parse(String videoId) throws Exception {

        Video video = new Video();

        video.setVideoId(videoId);
        video.setAuthor(getJsonPath().get(PATH_TO_AUTHOR));
        video.setTitle(getJsonPath().get(PATH_TO_TITLE));
        video.setViews(getJsonPath().get(PATH_TO_VIEW_COUNT));
        video.setKeywords(getJsonPath().get(PATH_TO_KEYWORDS));

        video.setDescription(prepareDescription(getJsonPath().get(PATH_TO_DESCRIPTION)));

        video.setDate(getJsonPath().get(PATH_TO_DATE));

        String likesDislikes = getJsonPath().get(PATH_TO_LIKES_DISLIKES);
        saveLikesAndDislikes(likesDislikes, video);

        return video;
    }

    private void saveLikesAndDislikes(String likesDislikes, Video video) {
        //likesDislikes has format likes / dislikes (100,200,300 / 234,345)
        likesDislikes = likesDislikes.replaceAll("[^\\d/]+", "");
        StringTokenizer tokenizer = new StringTokenizer(likesDislikes, LIKES_DISLIKES_SEPARATOR);

        if (tokenizer.hasMoreTokens()) video.setLikes(tokenizer.nextToken());

        if (tokenizer.hasMoreTokens()) video.setDislikes(tokenizer.nextToken());
    }

    private String prepareDescription(List<String> lines) {
        return String.join("", lines);
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
        return getResponse().headers().asList().stream()
                .filter(header -> header.getName().equals("Set-Cookie"))
                .map(h -> h.getValue().substring(0, h.getValue().indexOf(';')))
                .collect(Collectors.joining("; "));
    }
}
