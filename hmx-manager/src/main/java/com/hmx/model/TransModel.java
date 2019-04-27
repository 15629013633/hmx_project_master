package com.hmx.model;

import com.hmx.category.dto.HmxCategoryContentDto;
import com.hmx.files.dto.HmxFilesDto;
import com.hmx.images.dto.HmxImagesDto;
import com.hmx.movie.dto.HmxMovieDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/4/27.
 */
public class TransModel {
    private List<HmxMovieDto> movieList = new ArrayList<HmxMovieDto>();
    private List<HmxImagesDto> imagesList = new ArrayList<HmxImagesDto>();
    private List<HmxFilesDto> filesList = new ArrayList<HmxFilesDto>();
    private HmxCategoryContentDto content = new HmxCategoryContentDto();

    public List<HmxMovieDto> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<HmxMovieDto> movieList) {
        this.movieList = movieList;
    }

    public List<HmxImagesDto> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<HmxImagesDto> imagesList) {
        this.imagesList = imagesList;
    }

    public List<HmxFilesDto> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<HmxFilesDto> filesList) {
        this.filesList = filesList;
    }

    public HmxCategoryContentDto getContent() {
        return content;
    }

    public void setContent(HmxCategoryContentDto content) {
        this.content = content;
    }
}
