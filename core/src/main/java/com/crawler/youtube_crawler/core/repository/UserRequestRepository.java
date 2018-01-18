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

@Repository
public interface UserRequestRepository extends MongoRepository<UserRequest, String> {

    default UserRequest updateComments(String requestId, String videoId, List<Comment> comments) {
        UserRequest userRequest = findOne(requestId);
        if (userRequest == null) return null;

        VideoInfo videoInfo = userRequest.getVideos().get(videoId);
        if (videoInfo == null) return null;

        videoInfo.setComments(comments);
        save(userRequest);
        return userRequest;
    }

    default UserRequest addVideo(String requestId, Video video) {
//        UserRequest userRequest = findOne(requestId);
//        if (userRequest == null) return null;
//
//        VideoInfo videoInfo = userRequest.getVideos().get(video.getId());
//        if (videoInfo == null) return null;
//
//        videoInfo.getVideoDetails().put(video.getId(), video);
//        save(userRequest);
//        return userRequest;
        return null;
    }
}