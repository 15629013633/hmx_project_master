package com.hmx.timer;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by songjinbao on 2019/6/3.
 */
@Component
@EnableScheduling
public class ScheduledService {

    @Scheduled(fixedRate = 2000)
    public void scheduled(){
        System.out.println("111111$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }
//    @Scheduled(fixedRate = 5000)
//    public void scheduled1() {
//        log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
//    }
//    @Scheduled(fixedDelay = 5000)
//    public void scheduled2() {
//        log.info("=====>>>>>fixedDelay{}",System.currentTimeMillis());
//    }
}
