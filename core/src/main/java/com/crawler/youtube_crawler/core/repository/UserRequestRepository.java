package com.crawler.youtube_crawler.core.repository;

import com.crawler.youtube_crawler.core.model.UserRequest;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import com.crawler.youtube_crawler.core.model.VideoInfo;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRequestRepository extends MongoRepository<UserRequest, String> {

    default UserRequest updateComments(String requestId, String videoId, List<String> comments) {
        UserRequest userRequest = findOne(requestId);
        if (userRequest == null) return null;

        Map<String, VideoInfo> vs = userRequest.getVideos();
        vs.putIfAbsent(videoId, new VideoInfo());

        VideoInfo videoInfo = vs.get(videoId);

        videoInfo.setComments(comments);
        save(userRequest);
        return userRequest;
    }

    default UserRequest updateVideoDetails(String requestId, String videoId, List<String> videos) {
        UserRequest userRequest = findOne(requestId);
        if (userRequest == null) return null;

        Map<String, VideoInfo> vs = userRequest.getVideos();
        vs.putIfAbsent(videoId, new VideoInfo());

        VideoInfo videoInfo = vs.get(videoId);

        videoInfo.setVideoDetails(videos);
        save(userRequest);
        return userRequest;
    }
}