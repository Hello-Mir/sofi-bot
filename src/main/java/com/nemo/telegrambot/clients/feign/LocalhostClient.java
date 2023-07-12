package com.nemo.telegrambot.clients.feign;

import com.nemo.telegrambot.model.freegpt.FreeGptRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "localhostClient", url = "http://127.0.0.1:1338")
public interface LocalhostClient {
    @RequestMapping(method = RequestMethod.POST, value = "/backend-api/v2/conversation")
    String sendRequest(@RequestHeader String header, @RequestBody FreeGptRequest request);
}
