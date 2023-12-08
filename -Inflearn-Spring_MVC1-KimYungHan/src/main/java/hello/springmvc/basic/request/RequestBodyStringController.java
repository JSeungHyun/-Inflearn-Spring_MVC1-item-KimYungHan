package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String message = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("message: {}", message);
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream) throws IOException {
        String message = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("message: {}", message);
    }

    @PostMapping("/request-body-string-v3")
    public void requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String message = httpEntity.getBody();
        log.info("message: {}", message);
    }

    /**
     * @RequestBody 는 요청 본문의 데이터가 HttpMessageConverter를 통해 파싱되어 Java 객체로 변환
     * @ModelAttribute는 요청 쿼리 파라미터에 데이터를 DTO로 파싱
     */
    @PostMapping("/request-body-string-v4")
    @ResponseBody
    public void requestBodyStringV4(@RequestBody HelloData helloData) throws IOException {
        log.info("message: {}", helloData.getAge());
    }
}
