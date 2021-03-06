package com.hmx.fileupload.controller;

import com.hmx.files.service.HmxFilesService;
import com.hmx.utils.enums.UploadFileType;
import com.hmx.utils.oss.upload.UploadEpubUtil;
import com.hmx.utils.oss.upload.UploadUtil;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.Result;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songjinbao on 2019/4/25.
 */
@RestController
@RequestMapping("/fileUpload")
public class FileController {

    @Autowired
    private UploadUtil uploadUtil;

    @Autowired
    private UploadEpubUtil uploadEpubUtil;

    @Autowired
    private HmxFilesService hmxFilesService;

    /**
     * @param pdfFile 文件
     * @param module 自定义文件储存文件夹
     * @param fileType 文件上传类型 1为pdf 2为图片
//     * @param contentFlow 内容流水号
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
   public ResultBean fileUpload(MultipartFile pdfFile , MultipartFile imageFile,@RequestParam Integer fileType,@RequestParam String contentFlow, @RequestParam( required = false) String module ){
        if(fileType == null){
            return new ResultBean().setCode(Config.UPLOAD_ERROR).setContent("文件类型不能为空");
        }
//       if ( pdfFile == null && epubFile == null) {
//            return new ResultBean().setCode(Config.UPLOAD_ERROR).setContent("上传文件不能为空");
//        }
        if(StringUtils.isEmpty(contentFlow)){
            return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("内容流水哈contentFlow不能为空");
        }
        List<String> fileTypeStr = UploadFileType.getName(fileType);
        if(fileTypeStr == null){
            return new ResultBean().setCode(Config.UPLOAD_ERROR).setContent("文件类型不正确");
        }
        try {
            String virtualPath = "";
            if(null != pdfFile){
                virtualPath = uploadUtil.uploadFile( pdfFile , contentFlow, fileTypeStr,fileType+"" );
                //uploadpdf(epubFile,module,fileType,fileTypeStr);
            }

            if(null != imageFile){
                virtualPath = uploadUtil.uploadFile( imageFile , contentFlow, fileTypeStr, fileType+"");
            }

            if ( StringUtils.isEmpty( virtualPath ) ) {
                return new ResultBean().setCode(Config.UPLOAD_ERROR).setContent("文件上传异常");
            }
            return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("上传成功").put("virtualPath", virtualPath);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean().setCode(Config.UPLOAD_ERROR).setContent("文件上传异常:"+ e.getMessage() );
        }
    }

    public String uploadpdf(MultipartFile file,String module,Integer fileType,List<String> fileTypeStr){
        return uploadEpub(file,module,fileType,fileTypeStr);
    }

    public String uploadEpub(MultipartFile file,String module,Integer fileType,List<String> fileTypeStr){
        String path =  "";
        if ( StringUtils.isEmpty( module ) ) {
            path =  File.separator+"files"+File.separator+"default"+File.separator;
        }else{
            path =  File.separator+"files"+File.separator+module+File.separator;
        }
        try {
            String virtualPath = uploadEpubUtil.uploadFile( file , path, fileTypeStr );

            return virtualPath;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据文件地址删除文件 pdf 和epub
     * @return
     */
    @PostMapping(value = "/delete")
    public ResultBean delete(String fileUrl){
        System.out.println("fileUrl:" + fileUrl);
        Result<Object> result = new Result<>();
        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(com.alibaba.druid.util.StringUtils.isEmpty(fileUrl)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("文件地址不能为空");
            flag=false;
        }
        if(flag){
            flag = hmxFilesService.deleteByFileUrl(fileUrl);
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

    public static void main(String[] arg0){
        List<String> fileTypeStr = UploadFileType.getName(2);
        System.out.println(fileTypeStr);
    }
}
