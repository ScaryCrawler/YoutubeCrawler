package com.crawler.youtube_crawler.youtubeapi.proxy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@ConfigurationProperties(prefix = "circle")
public class CircleDelayProxyGenerator implements ProxyGenerator {

    @Getter @Setter
    private List<Proxy> proxies;
    @Getter @Setter
    private int delay;

    private int localDelay = 0;
    private int position = 0;

    @Override
    public Proxy next() {
        if (localDelay > 0){
            localDelay--;
        } else  {
            localDelay = delay;
            position++;
            if (position > proxies.size()) position = 0;
        }

        return  proxies.get(position);
    }

    @Override
    public Proxy forceNext() {
        localDelay = delay;
        position++;
        if (position > proxies.size()) position = 0;
        return  proxies.get(position);
    }
}
