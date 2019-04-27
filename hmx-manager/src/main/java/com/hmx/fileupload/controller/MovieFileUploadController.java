package com.hmx.fileupload.controller;

import com.hmx.movie.service.HmxMovieService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.Result;
import com.hmx.utils.result.ResultBean;
import com.hmx.utils.upload.InitVodClients;
import com.hmx.utils.upload.UploadVideoDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Created by songjinbao on 2019/4/25.
 */
@RestController
@RequestMapping("/movieUpload")
public class MovieFileUploadController {
    @Autowired
    private UploadVideoDemo uploadVideoDemo;
    @Autowired
    private InitVodClients initVodClients;
    @Autowired
    private HmxMovieService hmxMovieService;
    /**
     * 视频上传
     * @param file 视频文件
     * @param title 视频标题
     * @return
     */
    @RequestMapping("/upload")
    public ResultBean fileMovieUpload(MultipartFile file, String title){
        if(file == null){
            return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("上传视频文件不能为空");
        }
        if(StringUtils.isEmpty(title)){
            return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("上传视频文件标题不能为空");
        }
        try {
            Map<String,Object> resultMap = uploadVideoDemo.hmxUploadVideo(file.getInputStream(), file.getOriginalFilename(),title.trim());
            boolean flag = Boolean.parseBoolean(resultMap.get("flag").toString());
            if(!flag){
                return new ResultBean().setCode(Config.FAIL_CODE).setContent(resultMap.get("content").toString());
            }
            return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("上传视频文件成功").put("videoId", resultMap.get("videoId"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return new ResultBean().setCode(Config.FAIL_CODE).setContent("视频文件上传异常");
        }
    }
    /**
     * 获取视频播放地址
     * @param videoId
     * @return
     */
    @RequestMapping("/getUrl")
    public ResultBean getVideoPathUrl(String videoId){
        if(StringUtils.isEmpty(videoId)){
            return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("视频编号不能为空");
        }
        try{
            Map<String,Object> resultMap = initVodClients.getUrl(videoId);
            boolean flag = Boolean.parseBoolean(resultMap.get("flag").toString());
            if(!flag){
                return new ResultBean().setCode(Config.FAIL_CODE).setContent(resultMap.get("content").toString());
            }
            return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("获取播放地址成功").put("url", resultMap.get("url"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return new ResultBean().setCode(Config.FAIL_CODE).setContent("获取播放地址异常");
        }
    }

    /**
     * 视频删除
     * @return
     */
    @PostMapping(value = "/delete")
    public ResultBean delete(String ids){
        Result<Object> result = new Result<>();
        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(com.alibaba.druid.util.StringUtils.isEmpty(ids)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("视频主键不能为空");
            flag=false;
        }
        if(flag){
            flag = hmxMovieService.deleteByIdArray(ids);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("删除失败");
                return resultBean;
            }else{
                result.setStatus(10000);
                result.setMsg("成功");
                resultBean.setCode(Config.SUCCESS_CODE).setContent("删除成功");
                return resultBean;
            }
        }
        return resultBean;
    }
}
