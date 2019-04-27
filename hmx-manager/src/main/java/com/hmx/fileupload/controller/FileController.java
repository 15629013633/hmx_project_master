package com.hmx.fileupload.controller;

import com.hmx.files.service.HmxFilesService;
import com.hmx.utils.enums.UploadFileType;
import com.hmx.utils.oss.upload.UploadUtil;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.Result;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by songjinbao on 2019/4/25.
 */
@RestController
@RequestMapping("/fileUpload")
public class FileController {

    @Autowired
    private UploadUtil uploadUtil;

    @Autowired
    private HmxFilesService hmxFilesService;

    /**
     * @param file 文件
     * @param module 自定义文件储存文件夹
     * @param fileType 文件上传类型 1图片类型(pdf) 2大文件类型(epub)
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public ResultBean fileUpload(@RequestParam MultipartFile file , @RequestParam Integer fileType, @RequestParam( required = false) String module ){
        if ( file == null ) {
            return new ResultBean().setCode(Config.UPLOAD_ERROR).setContent("文件为空");
        }
        String path =  "";
        if ( StringUtils.isEmpty( module ) ) {
            path =  File.separator+"files"+File.separator+"default"+File.separator;
        }else{
            path =  File.separator+"files"+File.separator+module+File.separator;
        }
        if(fileType == null){
            return new ResultBean().setCode(Config.UPLOAD_ERROR).setContent("文件不能为空");
        }
        List<String> fileTypeStr = UploadFileType.getName(fileType);
        if(fileTypeStr == null){
            return new ResultBean().setCode(Config.UPLOAD_ERROR).setContent("文件类型不正确");
        }
        try {
            String virtualPath = uploadUtil.uploadFile( file , path, fileTypeStr );

            if ( StringUtils.isEmpty( virtualPath ) ) {
                return new ResultBean().setCode(Config.UPLOAD_ERROR).setContent("文件上传异常");
            }
            return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("上传成功").put("virtualPath", virtualPath);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean().setCode(Config.UPLOAD_ERROR).setContent("文件上传异常:"+ e.getMessage() );
        }
    }

    /**
     * 文件删除
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
            flag = hmxFilesService.deleteByIdArray(ids);
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
