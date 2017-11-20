package com.crawler.youtube_crawler.core.repository;

import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.dto.ResultDto;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HashMapResultRepository implements ResultRepository {
    private Map<UUID, ResultDto> map = new HashMap<>();

    @Override
    public ResultDto read(@NonNull final UUID id) {
        return map.get(id);
    }

    @Override
    public Collection<ResultDto> readByJobUuid(UUID jobUuid) {
        return null;
    }

    @Override
    public ResultDto create(@NonNull final ResultDto result) {
        result.setId(UUID.randomUUID());
        map.put(result.getId(), result);
        return result;
    }

    @Override
    public void delete(@NonNull final UUID id) {
        map.remove(id);
    }
}