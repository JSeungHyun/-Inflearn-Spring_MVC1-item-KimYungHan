package hello.springmvc.basic.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String message = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("====== message : {}", message);
        HelloData helloData = mapper.readValue(message, HelloData.class);
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
    }

    @PostMapping("/request-body-json-v2")
    @ResponseBody
    public void requestBodyJsonV2(@RequestBody String message) throws IOException {
        log.info("====== message : {}", message);
        HelloData helloData = mapper.readValue(message, HelloData.class);
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
    }

    @PostMapping("/request-body-json-v3")
    @ResponseBody
    public String requestBodyJsonV3(@RequestBody HelloData message) throws IOException {
        log.info("username = {}, age = {}", message.getUsername(), message.getAge());
        return "OK!";
    }

    @PostMapping("/request-body-json-v5")
    @ResponseBody
    public HelloData requestBodyJsonV5(@RequestBody HelloData message) throws IOException {
        log.info("username = {}, age = {}", message.getUsername(), message.getAge());
        return message;
    }
}
