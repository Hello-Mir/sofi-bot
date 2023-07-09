package com.nemo.telegrambot.clients.feign;

import com.nemo.telegrambot.model.freegpt.FreeGptRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "localhostClient", url = "http://127.0.0.1:1338")
public interface LocalhostClient {
    @PostMapping("/")
    String sendRequest(@RequestHeader String header, @RequestBody FreeGptRequest request);
}
