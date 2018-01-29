package com.crawler.youtube_crawler.youtubeapi.parser;

import com.crawler.youtube_crawler.youtubeapi.entity.Comment;
import com.crawler.youtube_crawler.youtubeapi.entity.ContinuationInfo;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommentsParser extends YouTubeParser< List<Comment> > {

    private static final String PATH_TO_AUTHORS_LIST = "response.continuationContents.itemSectionContinuation.contents.commentThreadRenderer.comment.commentRenderer.authorText.simpleText";
    private static final String PATH_TO_LIKE_COUNT_LIST = "response.continuationContents.itemSectionContinuation.contents.commentThreadRenderer.comment.commentRenderer.likeCount";
    private static final String PATH_TO_COMMENT_IDS = "response.continuationContents.itemSectionContinuation.contents.commentThreadRenderer.comment.commentRenderer.commentId";
    private static final String PATH_TO_COMMENTS_TEXT_LINES = "response.continuationContents.itemSectionContinuation.contents.commentThreadRenderer.comment.commentRenderer.contentText.runs.text";

    private static final String PATH_TO_CONTINUATION = "response.continuationContents.itemSectionContinuation.continuations[0].nextContinuationData.continuation";
    private static final String PATH_TO_CTOKEN = PATH_TO_CONTINUATION;
    private static final String PATH_TO_ITCT = "response.continuationContents.itemSectionContinuation.continuations[0].nextContinuationData.clickTrackingParams";
    private static final String PATH_TO_SESSION_TOKEN = "xsrf_token";

    public CommentsParser(Response response) {
       super(response);
    }

    @Override
    public List<Comment> parse(String videoId) throws Exception {

        List<String> authors = getJsonPath().get(PATH_TO_AUTHORS_LIST);
        List<Integer> likes = getJsonPath().get(PATH_TO_LIKE_COUNT_LIST);
        List<String> commentIds = getJsonPath().get(PATH_TO_COMMENT_IDS);
        List<List<String>> commentTexts = getJsonPath().get(PATH_TO_COMMENTS_TEXT_LINES);

        return createCommentList(videoId, commentIds, authors, likes, commentTexts);
    }

    private List<Comment> createCommentList(String videoId, List<String> commentIds, List<String> authors, List<Integer> likes, List<List<String>> commentTexts) {
        List<String> texts = prepareCommentText(commentTexts);
        List<Comment> result = new ArrayList<>(commentIds.size());

        for (int i = 0; i < commentIds.size(); i++) {
            Comment comment = new Comment();
            comment.setVideoId(videoId);
            comment.setCommentId(commentIds.get(i));
            comment.setAuthor(authors.get(i));
            comment.setLikes(likes.get(i));
            comment.setText(texts.get(i));

            result.add(comment);
        }

        return result;
    }

    private List<String> prepareCommentText(List<List<String>> texts) {
        return texts.stream()
                .map(lines -> String.join("", lines))
                .collect(Collectors.toList());
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
}
