package com.hmx.user.service;


import com.hmx.data.LoginButtonData;

import java.util.List;
import java.util.Map;

/**
 * @author shi
 * @create 2018-07-04  21:43
 * @description
 **/

public interface LoginService {
    /**
     *@Author: shi
     *@Description: 根据用户id获取按钮
     *@param: userId
     *@return 
     *@Date: 16:47 2018/7/20
     */
    public List<LoginButtonData> loginButton(int userId);


    /*
    * 加载首页统计报表数据
    * */
    Map<String, Integer> loadHomeStatistics();
}
