package com.hmx.common.filter;

import com.hmx.user.dao.HmxUserMapper;
import com.hmx.user.dao.PermissionMapper;
import com.hmx.user.dao.UserModelMapper;
import com.hmx.user.entity.HmxUser;
import com.hmx.user.entity.po.Permission;
import com.hmx.user.entity.po.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerUserService implements UserDetailsService {
    @Autowired
    HmxUserMapper hmxUserMapper;
    @Autowired
    PermissionMapper permissionDao;

    @Override
    public UserDetails loadUserByUsername(String userPhone) throws UsernameNotFoundException {
        HmxUser hmxUser = null;
        hmxUser = hmxUserMapper.selectUserInfoByUserPhone(userPhone);
//        hmxUser = hmxUser !=null ? hmxUser : userDao.findUserBycellPhone(username);
        if (hmxUser != null && hmxUser.getState() == 0) {
            //查询相关url资源权限
            List<Permission> permissions = permissionDao.findPermissionByUserId(hmxUser.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Permission permission : permissions) {
                if (permission != null && permission.getName() != null) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(hmxUser.getUserPhone(), hmxUser.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException(userPhone + " do not exist!");

        }
    }
}
