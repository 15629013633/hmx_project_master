package com.hmx.movie.service.asyncService;

import com.hmx.movie.entity.HmxMovie;
import com.hmx.movie.service.HmxMovieService;
import com.hmx.utils.upload.InitVodClients;
import com.hmx.utils.upload.UploadVideoDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: 肖映彤
 * @Description: 异步处理电影任务
 * @Date: 18:20 2019-4-8
 */
@Component
public class UploadMovieAsync {

    private Logger logger = LoggerFactory.getLogger(UploadMovieAsync.class);

    @Autowired
    private UploadVideoDemo uploadVideoDemo;

    @Autowired
    private HmxMovieService hmxMovieService;

    @Async(value = "taskExecutorWbswryxx")
    public void uploadVideoAsync(InputStream in, String title, Integer movieId)  {
        String videoId = null;
        String url = null;
        logger.info("start uploadVideoAsync___________________________________");
        try{
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            //先上传video
            logger.info("start hmxUploadVideo___________________________________");
//            InputStream in = file.getInputStream();
            Map<String,Object> map = uploadVideoDemo.hmxUploadVideo(in, UUID.randomUUID().toString() + ".mp4",
                    title.trim(),1);
            if(map != null && !map.get("videoId").equals("")){
                videoId = (String) map.get("videoId");
                updateVideoStatus(movieId,1,videoId);
            }else{
                //保存movie表，处理状态为失败
                updateVideoStatus(movieId,3,videoId);
            }
        }catch (Exception e){
            e.printStackTrace();
            //保存movie表，处理状态为失败
            updateVideoStatus(movieId,3,videoId);
        }
    }

    @Bean(name = "taskExecutorWbswryxx")
    public Executor taskExecutorWbswryxx() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(200);

        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("taskExecutorWbswryxx-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }


    private boolean updateVideoStatus(Integer movieId, Integer videoStatus, String videoId){
        HmxMovie hmxMovie = hmxMovieService.info(movieId);
        if(hmxMovie != null){
            hmxMovie.setVideoStatus(videoStatus);
            hmxMovie.setNewTime(new Date());
            hmxMovie.setVideoId(videoId);
            return hmxMovieService.update(hmxMovie);
        }else{
            return false;
        }
    }
}