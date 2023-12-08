package hello.springmvc.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/log-test")
    public String logTest(){
        String name = "spring";

        log.trace(" trace log={}", name);
        log.debug(" debug log={}", name);
        log.info(" Info log={}", name);
        log.warn(" warn log={}", name);
        log.error(" error log={}", name);

        /**
         * log.trace(" trace my log=" + name");
         * 출력이 안되는 상황에서도 '+' 연산이 이루어지기 때문에 쓸모없는 자원 소모
         */

        return "OK! ";
    }
}
