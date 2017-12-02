package com.crawler.youtube_crawler.core.repository;

import com.crawler.youtube_crawler.core.dto.ResultDto;
import lombok.NonNull;

import java.util.Collection;
import java.util.UUID;

//TODO: implement using Spring Data/myBatis
public interface ResultRepository {
    ResultDto create(@NonNull final ResultDto result);
    ResultDto read(@NonNull final UUID id);
    Collection<ResultDto> readByJobUuid(@NonNull final UUID jobUuid);
    void delete(@NonNull final UUID id);
}