package com.hmx.media;

import com.hmx.category.dto.HmxCategoryContentDto;
import com.hmx.category.dto.HmxCategoryDto;
import com.hmx.category.entity.HmxCategory;
import com.hmx.category.entity.HmxCategoryContent;
import com.hmx.category.service.HmxCategoryContentService;
import com.hmx.category.service.HmxCategoryService;
import com.hmx.movie.dto.HmxMovieDto;
import com.hmx.movie.entity.HmxMovie;
import com.hmx.movie.service.HmxMovieService;
import com.hmx.movie.service.asyncService.UploadMovieAsync;
import com.hmx.user.entity.po.Garousel;
import com.hmx.user.entity.po.Vedio;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.Result;
import com.hmx.utils.upload.InitVodClients;
import com.hmx.utils.upload.UploadVideoDemo;
import freemarker.ext.beans.HashAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;


/**
 * @Author: 肖映彤
 * @Description: 媒介管理
 * @Date: 0:37 2019-4-1
 */
@RestController
@RequestMapping("/media")
public class MediaController {

    private Logger logger = LoggerFactory.getLogger(MediaController.class);

    @Autowired
    private HmxMovieService hmxMovieService;

    @Autowired
    private HmxCategoryContentService hmxCategoryContentService;

    @Autowired
    private UploadMovieAsync uploadMovieAsync;

    @Autowired
    private InitVodClients initVodClients;

    @Autowired
    private UploadVideoDemo uploadVideoDemo;

    @RequestMapping("/vedio/init")
    public ModelAndView vedioInit() {
        HmxCategoryContentDto hmxCategoryContentDto = new HmxCategoryContentDto();
        hmxCategoryContentDto.setState(0);
        List<HmxCategoryContent> hmxCategoryContents = hmxCategoryContentService.list(hmxCategoryContentDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/media/vedio/list");
        modelAndView.addObject("contents",hmxCategoryContents);
        return modelAndView;
    }

    @RequestMapping("/mp3/init")
    public ModelAndView mp3Init() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/media/mp3/list");
        return modelAndView;
    }

    @RequestMapping("/pic/init")
    public ModelAndView picInit() {
        ModelAndView modelAndView = new ModelAndView();
        HmxCategoryContentDto hmxCategoryContentDto = new HmxCategoryContentDto();
        hmxCategoryContentDto.setState(0);
        List<HmxCategoryContent> hmxCategoryContents = hmxCategoryContentService.list(hmxCategoryContentDto);
        modelAndView.setViewName("/media/pic/list");
        modelAndView.addObject("contents",hmxCategoryContents);
        return modelAndView;
    }

    @RequestMapping("/vedio/getList")
    public Map<String,Object> getVedioList(PageBean<HmxMovie> page, HmxMovieDto hmxMovieDto, Integer contentId){
        Map<String,Object> map = new HashMap();
        Integer movieId = null;
        if(contentId != null){
            HmxCategoryContent hmxCategoryContent = hmxCategoryContentService.info(contentId);
            movieId = hmxCategoryContent.getMovieId();
        }
        hmxMovieDto.setMovieId(movieId);
        hmxMovieDto.setState(0);
        PageBean<HmxMovie> pageBean = hmxMovieService.getPage(page,hmxMovieDto);
        map.put("rows", pageBean.getPage());
        map.put("total", pageBean.getTotalNum());
        return map;
    }

    @RequestMapping("/upload/pic")
    public Result<Object> uploadPic(MultipartFile file, Integer contentType) throws IOException {
        Result<Object> result = new Result<>();
        Map<String,Object> resultMap = uploadVideoDemo.hmxUploadImageLocalFile(file.getInputStream(), file.getOriginalFilename());
        if(resultMap.get("url") != null && !resultMap.get("url").equals("")){
            //保存进内容中
            HmxCategoryContent hmxCategoryContent = hmxCategoryContentService.info(contentType);
            hmxCategoryContent.setContentImages((String) resultMap.get("url"));
            hmxCategoryContentService.update(hmxCategoryContent);
        }
        //返回成功
        result.setMsg("成功");
        result.setStatus(10000);
        return result;
    }

    @RequestMapping("/delete/pic")
    public Result<Object> deletePic(Integer categoryContentId) {
        Result<Object> result = new Result<>();
        HmxCategoryContent hmxCategoryContent = hmxCategoryContentService.info(categoryContentId);
        hmxCategoryContent.setContentImages("");
        Boolean check = hmxCategoryContentService.update(hmxCategoryContent);
        //返回成功
        if(check){
            result.setMsg("成功");
            result.setStatus(10000);
        }else {
            result.setMsg("失败");
            result.setStatus(20000);
        }
        return result;
    }

    @RequestMapping("/upload/video")
    public Result<Object> uploadVideo(@RequestParam("file")String file, String title, String duration, Integer contentType, String ratio) throws IOException {
        logger.info("get in upload video controller______________________");
        Result<Object> result = new Result<>();
        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        byte[] bytes1 = decoder.decodeBuffer(file);
        InputStream in = new ByteArrayInputStream(bytes1);
//        OutputStream out = new FileOutputStream("/Users/ouhiroshi/myrich/1.mp4");
//        out.write(bytes1);
//        out.flush();
//        out.close();
        //保存进move表中
        HmxMovie hmxMovie = new HmxMovie();
        hmxMovie.setVideoStatus(2);
        hmxMovie.setDuration(duration);
        hmxMovie.setMovieName(title);
        hmxMovie.setRatio(ratio);
        hmxMovie.setCreateTime(new Date());
        logger.info("save hmxMovie______________________");
        hmxMovieService.insert(hmxMovie);
        //更新内容表
        logger.info("update HmxCategoryContent______________________");
        HmxCategoryContent hmxCategoryContent = hmxCategoryContentService.info(contentType);
        hmxCategoryContent.setMovieId(hmxMovie.getMovieId());
        hmxCategoryContentService.update(hmxCategoryContent);
        logger.info("start async______________________");
        uploadMovieAsync.uploadVideoAsync(in,title,hmxMovie.getMovieId());
        //返回成功
        result.setMsg("成功");
        result.setStatus(10000);
        return result;
    }

    @RequestMapping("/delete/video")
    public Result<Object> deleteVideo(Integer movieId) {
        Result<Object> result = new Result<>();
        HmxMovie hmxMovie = hmxMovieService.info(movieId);
        hmxMovie.setState(1);
        Boolean flag = hmxMovieService.update(hmxMovie);
        //删除内容中的movieId
        HmxCategoryContentDto hmxCategoryContentDto = new HmxCategoryContentDto();
        hmxCategoryContentDto.setMovieId(movieId);
        List<HmxCategoryContent> list = hmxCategoryContentService.list(hmxCategoryContentDto);
        if(list != null && list.size()>0){
            for(HmxCategoryContent hmxCategoryContent : list){
                hmxCategoryContent.setMovieId(0);
                hmxCategoryContentService.update(hmxCategoryContent);
            }
        }
        //返回成功
        if(flag){
            result.setMsg("成功");
            result.setStatus(10000);
        }else {
            result.setMsg("失败");
            result.setStatus(20000);
        }
        return result;
    }

    @RequestMapping("/pic/getList")
    public Map<String,Object> getPicList(PageBean<HmxCategoryContent> pageBean,HmxCategoryContentDto hmxCategoryContentDto){
        Map<String,Object> map = new HashMap();
        PageBean<HmxCategoryContent> page = hmxCategoryContentService.getPage(pageBean,hmxCategoryContentDto);
        map.put("rows", page.getPage());
        map.put("total", page.getTotalNum());
        return map;
    }

    @RequestMapping("/pic/update")
    public Result<Object> updatePicList(){
        Result<Object> result = new Result<>();
        result.setStatus(10000);
        result.setMsg("修改成功");
        return result;
    }

    @RequestMapping("/pic/show")
    public ModelAndView picShow(Integer categoryContentId){
        ModelAndView modelAndView = new ModelAndView();
        HmxCategoryContent hmxCategoryContent = hmxCategoryContentService.info(categoryContentId);
        String picUrl = hmxCategoryContent.getContentImages();
        modelAndView.addObject("picUrl",picUrl);
        modelAndView.setViewName("/media/pic/picShow");
        return modelAndView;
    }

    @RequestMapping("/vedio/add")
    public ModelAndView vedioAdd(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/media/vedio/add");
        return modelAndView;
    }

    @RequestMapping("/vedio/show")
    public ModelAndView show(Integer movieId){
        HmxMovie hmxMovie = hmxMovieService.info(movieId);
        String videoId = hmxMovie.getVideoId();
        Map<String,Object> resultMap = initVodClients.getUrl(videoId);
        String url = (String) resultMap.get("url");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/media/vedio/show");
        modelAndView.addObject("movieUrl",url);
        return modelAndView;
    }

    @RequestMapping("/mp3/show")
    public ModelAndView mp3Play(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/media/mp3/show");
        return modelAndView;
    }

}