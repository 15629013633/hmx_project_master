package com.hmx.user.service.impl;


import com.hmx.data.LoginButtonData;
import com.hmx.movie.dao.HmxMovieMapper;
import com.hmx.movie.entity.HmxMovieExample;
import com.hmx.movie.service.HmxMovieService;
import com.hmx.user.dao.HmxUserMapper;
import com.hmx.user.dao.PermissionMapper;
import com.hmx.user.dao.UserModelMapper;
import com.hmx.user.entity.HmxUserExample;
import com.hmx.user.entity.po.Permission;
import com.hmx.user.entity.po.UserModelExample;
import com.hmx.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private PermissionMapper permissionDao;

    @Autowired
    private HmxMovieMapper hmxMovieMapper;

    @Autowired
    private HmxUserMapper hmxUserMapper;

    @Override
    public List<LoginButtonData> loginButton(int userId) {
        List<LoginButtonData> loginButtonDatas = new ArrayList<>();
        //查询一级菜单按钮
        List<Permission> models = permissionDao.findPermissionButtonByUserId(userId,0);
        if(models != null && models.size() > 0){
            for(Permission model : models){
                LoginButtonData data = new LoginButtonData();
                data.setId(model.getId());
                data.setButtonName(model.getName());
                data.setUrl(model.getUrl());
                data.setIcon(model.getIcon());
                //查询菜单下的子按钮
                data.setPermissionModels(permissionDao.findPermissionButtonByUserId(userId,model.getId()));
                loginButtonDatas.add(data);
            }
        }
        return loginButtonDatas;
    }

    @Override
    public Map<String, Integer> loadHomeStatistics() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        //电影数统计
        HmxMovieExample hmxMovieExample = new HmxMovieExample();
        hmxMovieExample.createCriteria().andStateEqualTo(0);
        int movieCount = hmxMovieMapper.countByExample(hmxMovieExample);
        map.put("movieCount",movieCount);
        //用户数量统计
        HmxUserExample hmxUserExample = new HmxUserExample();
        hmxUserExample.createCriteria().andStateEqualTo(0);
        int userCount = hmxUserMapper.countByExample(hmxUserExample);
        map.put("userCount",userCount);
        return map;
    }
}
