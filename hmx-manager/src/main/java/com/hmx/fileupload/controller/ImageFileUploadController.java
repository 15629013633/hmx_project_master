package com.hmx.fileupload.controller;

import com.hmx.images.service.HmxImagesService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.Result;
import com.hmx.utils.result.ResultBean;
import com.hmx.utils.upload.UploadVideoDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Created by songjinbao on 2019/4/25.
 */

@RestController
@RequestMapping("/imageUpload")
public class ImageFileUploadController {

    @Autowired
    private UploadVideoDemo uploadVideoDemo;

    @Autowired
    private HmxImagesService hmxImagesService;

    /**
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    public ResultBean fileImageUpload(MultipartFile file){
        if(file == null){
            return new ResultBean().setCode(Config.FAIL_CODE).setContent("上传图片文件不能为空");
        }
        try {
            Map<String,Object> resultMap = uploadVideoDemo.hmxUploadImageLocalFile(file.getInputStream(), file.getOriginalFilename());
            boolean flag = Boolean.parseBoolean(resultMap.get("flag").toString());
            if(!flag){
                return new ResultBean().setCode(Config.FAIL_CODE).setContent(resultMap.get("content").toString());
            }
            return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("上传图片成功").put("url", resultMap.get("url"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return new ResultBean().setCode(Config.FAIL_CODE).setContent("图片上传异常");
        }
    }

    /**
     * 分类内容删除
     * @return
     */
    @PostMapping(value = "/delete")
    public ResultBean delete(String ids){
        Result<Object> result = new Result<>();
        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(com.alibaba.druid.util.StringUtils.isEmpty(ids)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("文件主键不能为空");
            flag=false;
        }
        if(flag){
            flag = hmxImagesService.deleteByIdArray(ids);
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
