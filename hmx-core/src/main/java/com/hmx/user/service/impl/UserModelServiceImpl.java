package com.hmx.user.service.impl;

import com.hmx.user.dao.UserModelMapper;
import com.hmx.user.dto.UserModelDto;
import com.hmx.user.entity.po.UserModel;
import com.hmx.user.entity.po.UserModelExample;
import com.hmx.user.service.UserModelService;
import com.hmx.utils.common.CommonUtils;
import com.hmx.utils.logger.LogHelper;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.Result;
import com.hmx.utils.secret.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/7/8.
 */
@Service
public class UserModelServiceImpl implements UserModelService {

    @Autowired
    private UserModelMapper userModelMapper;

    /**
     * @Method: insert
     * @Description: 添加
     * @param userModel 要添加的对象
     * @return
     */
    @Override
    public Boolean insert( UserModel userModel ) {
        return userModelMapper.insertSelective( userModel ) > 0;
    }

    /**
     * @Method: deleteByIdArray
     * @Description: 批量删除
     * @param ids 将要删除的对象主键字符串 例如:1,5,10,12
     * @return true 删除成功  false 删除失败
     */
    @Override
    @Transactional
    public Boolean deleteByIdArray(String ids) {
        List<Integer> idArray = new ArrayList<Integer>();
        String[] arrayStr = null ;
        try{
            if( ids == null || ids == "" ){
                return false;
            }

            if( ids.length() > 0 ){
                arrayStr = ids.split(",");
            }

            for(String strid: arrayStr){
                Integer id = Integer.parseInt(strid);
                idArray.add(id);
            }
            UserModelExample userModelExample = new UserModelExample();
            userModelExample.or().andIdIn( idArray );

            int ret = userModelMapper.deleteByExample( userModelExample );
            return ret > 0;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @Method: update
     * @Description: 修改
     * @param userModel 要修改的对象
     * @return true 修改成功  false 修改失败
     */
    @Override
    public Boolean update(UserModel userModel) {
        return userModelMapper.updateByPrimaryKeySelective( userModel ) > 0;
    }


    /**
     * @Method: info
     * @Description: 根据自增主键查询对象信息
     * @return HmxUser 查询的对象
     */
    public UserModel info (Integer hmxUserId) {
        return userModelMapper.selectByPrimaryKey( hmxUserId );
    }

    /**
     * @Method: getPage
     * @Description: 分页查询
     * @param page 分页参数
     * @param userModelDto 查询条件
     * @return PageBean<HmxUser> 查询到的分页值
     */
    public PageBean<UserModel> getPage(PageBean<UserModel> page, UserModelDto userModelDto) {

        UserModelExample userModelExample = new UserModelExample();

        userModelExample.setOffset(page.getStartOfPage());
        userModelExample.setLimit(page.getPageSize());

        UserModelExample.Criteria where = userModelExample.createCriteria();



        Integer count = userModelMapper.countByExample( userModelExample );

        boolean haveData = page.setTotalNum((int)(long)count);

        if(!haveData){
            return page;
        }

        List<UserModel> data = userModelMapper.selectByExample( userModelExample );

        page.setPage(data);

        return page;
    }


    /**
     * @Method: list
     * @Description: 查询某个条件下的所有数据
     * @param userModelDto 查询参数
     * @return List<HmxUser> 符合条件的list集合
     */
    public List<UserModel> list( UserModelDto userModelDto ) {


        return null;
    }

    /**
     * 用户登录
     */
    public UserModel login(UserModel userModel) {

        UserModelExample example = new UserModelExample();
        UserModelExample.Criteria where = example.createCriteria();

        if (!StringUtils.isEmpty(userModel.getUsername())) {
            where.andUserNameEqualTo(userModel.getUsername());
        }
        if (!StringUtils.isEmpty(userModel.getPassword())) {
            where.andPasswordEqualTo(MD5Util.encode(userModel.getPassword()));
        }

//        userCriteria.andStateEqualTo(0);
        List<UserModel> users = userModelMapper.selectByExample(example);

        if (users != null && users.size() == 1) {

            LogHelper.logger().debug("登录成功");

            return users.get(0);
        }
        return null;
    }
    /**
     * 用户手机号查询用户信息
     * @param userPhone
     * @return
     */
    public UserModel selectUserInfoByUserPhone(String userPhone){
        return null;
    }

    @Override
    public Result<Object> addOrUpdateUser(UserModelDto hmxUserDto) {

        return null;
    }

    @Override
    public boolean modifyPas(String userPhone, String oldPassword, String newPassword) {
//        //根据手机号查询验证码
//
//        //根据手机号查找用户信息
//        HmxUser hmxUser = hmxUserMapper.findUserBycellPhone(userPhone);
//        if(null == hmxUser){
//            return false;
//        }
//        //对比密码
//        String oldPasswordWd5 = MD5Util.encode(oldPassword);
//        if(!oldPasswordWd5.equals(hmxUser.getPassword())){
//            return false;
//        }
//        hmxUser.setPassword(MD5Util.encode(newPassword));
//        return hmxUserMapper.updateByPrimaryKeySelective( hmxUser ) > 0;
        return true;
    }

    @Override
    public boolean findPas(String userPhone, String newPassword) {
        //根据手机号查找用户信息
//        HmxUser hmxUser = hmxUserMapper.findUserBycellPhone(userPhone);
//        if(null == hmxUser){
//            return false;
//        }
//        hmxUser.setPassword(MD5Util.encode(newPassword));
//        return hmxUserMapper.updateByPrimaryKeySelective( hmxUser ) > 0;
        return true;
    }

    @Override
    public boolean updateUserLever(String ids, Integer userLevel) {
//        try {
//            String[] idsArr = ids.split(",");
//            if(null != idsArr && idsArr.length > 0){
//                for(String id : idsArr){
//                    if(CommonUtils.isInteger(id)){
//                        HmxUser user = hmxUserMapper.selectByPrimaryKey(Integer.valueOf(id));
//                        user.setUserLevel(userLevel);
//                        hmxUserMapper.updateByPrimaryKey(user);
//                    }
//                }
//            }
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return false;
    }

}
