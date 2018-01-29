package com.crawler.youtube_crawler.youtubeapi.proxy;

public interface ProxyGenerator {
    Proxy next();

    Proxy forceNext();
}
