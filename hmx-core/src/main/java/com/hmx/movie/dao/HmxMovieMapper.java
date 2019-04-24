

package com.hmx.movie.dao;


import com.hmx.movie.entity.HmxMovie;
import com.hmx.movie.entity.HmxMovieExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * Dao interface
 */
public interface HmxMovieMapper{

    int countByExample(HmxMovieExample example);

    int deleteByExample(HmxMovieExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(HmxMovie record);

    int insertSelective(HmxMovie record);

    List<HmxMovie> selectByExample(HmxMovieExample example);

    HmxMovie selectByPrimaryKey(Integer hmxMovieId);

    int updateByExampleSelective(@Param("record") HmxMovie record, @Param("example") HmxMovieExample example);

    int updateByExample(@Param("record") HmxMovie record, @Param("example") HmxMovieExample example);

    int updateByPrimaryKeySelective(HmxMovie record);

    int updateByPrimaryKey(HmxMovie record);

}