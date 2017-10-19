package com.crawler.youtube_crawler.rest_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
final class Controller {
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public final void publish() {
        return;
    }
}
