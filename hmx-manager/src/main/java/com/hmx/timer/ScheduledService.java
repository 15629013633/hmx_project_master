package com.hmx.timer;

import com.hmx.category.entity.HmxCategoryContentTrans;
import com.hmx.category.service.HmxCategoryContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by songjinbao on 2019/6/3.
 */
@Component
@EnableScheduling
public class ScheduledService {

    @Autowired
    private HmxCategoryContentService hmxCategoryContentService;

    @Scheduled(fixedRate = 2000)
    public void scheduled(){
        HmxCategoryContentTrans trans = hmxCategoryContentService.selectCategoryContentById(128);
        //获取到所有的state=1的内容，如果检测到时间超过8小时则将state置为0
        System.out.println("title="+trans.getCategoryTitle());
    }

    @Scheduled(fixedRate = 4000)
    public void scheduled1(){
        System.out.println("******************************");
    }

}
