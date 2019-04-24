package com.hmx.user.service.impl;

import java.util.List;
import java.util.ArrayList;

import com.hmx.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import com.hmx.user.service.HmxUserService;
import com.hmx.utils.logger.LogHelper;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.secret.MD5Util;
import com.hmx.user.entity.HmxUser;
import com.hmx.user.dto.HmxUserDto;
import com.hmx.user.entity.HmxUserExample;
import com.hmx.user.entity.HmxUserExample.Criteria;
import com.hmx.user.dao.HmxUserMapper;
/**
 * Service implementation class
 *
 */
@Service
public class HmxUserServiceImpl implements HmxUserService{

	@Autowired
	private HmxUserMapper hmxUserMapper;


	/**
	 * @Method: insert
	 * @Description: 添加
	 * @param hmxUser 要添加的对象
	 * @return
	 */
	@Override
	public Boolean insert( HmxUser hmxUser ) {
		return hmxUserMapper.insertSelective( hmxUser ) > 0;
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
			HmxUserExample hmxUserExample = new HmxUserExample();
			hmxUserExample.or().andIdIn( idArray );

			int ret = hmxUserMapper.deleteByExample( hmxUserExample );
			return ret > 0;
		}catch( Exception e ){
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @Method: update
	 * @Description: 修改
	 * @param hmxUser 要修改的对象
	 * @return true 修改成功  false 修改失败
	 */
	@Override
	public Boolean update(HmxUser hmxUser) {
		return hmxUserMapper.updateByPrimaryKeySelective( hmxUser ) > 0;
	}


	/**
	 * @Method: info
	 * @Description: 根据自增主键查询对象信息
	 * @param hmxUser 根据自增对象查询信息
	 * @return HmxUser 查询的对象
	 */
	public HmxUser info (Integer hmxUserId) {
		return hmxUserMapper.selectByPrimaryKey( hmxUserId );
	}

	/**
	 * @Method: getPage
	 * @Description: 分页查询
	 * @param page 分页参数
	 * @param hmxUserDto 查询条件
	 * @return PageBean<HmxUser> 查询到的分页值
	 */
	public PageBean<HmxUser> getPage(PageBean<HmxUser> page,HmxUserDto hmxUserDto) {

		HmxUserExample hmxUserExample = new HmxUserExample();

		hmxUserExample.setOffset(page.getStartOfPage());
		hmxUserExample.setLimit(page.getPageSize());

		Criteria where = hmxUserExample.createCriteria();

		if ( hmxUserDto.getId() != null && hmxUserDto.getId() != 0 ) {
			where.andIdEqualTo( hmxUserDto.getId() );
		}
		if ( StringUtils.isEmpty( hmxUserDto.getUserName() ) ) {
			where.andUserNameEqualTo( hmxUserDto.getUserName() );
		}
		if ( StringUtils.isEmpty( hmxUserDto.getUserAliase() ) ) {
			where.andUserAliaseEqualTo( hmxUserDto.getUserAliase() );
		}
		if ( StringUtils.isEmpty( hmxUserDto.getUserPhone() ) ) {
			where.andUserPhoneEqualTo( hmxUserDto.getUserPhone() );
		}
		if ( StringUtils.isEmpty( hmxUserDto.getPassword() ) ) {
			where.andPasswordEqualTo( hmxUserDto.getPassword() );
		}
		if ( hmxUserDto.getGender() != null && hmxUserDto.getGender() != 0 ) {
			where.andGenderEqualTo( hmxUserDto.getGender() );
		}
		if ( StringUtils.isEmpty( hmxUserDto.getHeadPic() ) ) {
			where.andHeadPicEqualTo( hmxUserDto.getHeadPic() );
		}
		if ( hmxUserDto.getCreateTime() != null ) {
			where.andCreateTimeEqualTo( hmxUserDto.getCreateTime() );
		}
		if ( hmxUserDto.getNewTime() != null ) {
			where.andNewTimeEqualTo( hmxUserDto.getNewTime() );
		}
		if ( hmxUserDto.getState() != null && hmxUserDto.getState() != 0 ) {
			where.andStateEqualTo( hmxUserDto.getState() );
		}
		if ( hmxUserDto.getVersion() != null && hmxUserDto.getVersion() != 0 ) {
			where.andVersionEqualTo( hmxUserDto.getVersion() );
		}
		if ( hmxUserDto.getCreateid() != null && hmxUserDto.getCreateid() != 0 ) {
			where.andCreateidEqualTo( hmxUserDto.getCreateid() );
		}

		Integer count = hmxUserMapper.countByExample( hmxUserExample );

		boolean haveData = page.setTotalNum((int)(long)count);

		if(!haveData){
			return page;
		}

		List<HmxUser> data = hmxUserMapper.selectByExample( hmxUserExample );

		page.setPage(data);

		return page;
	}


	/**
	 * @Method: list
	 * @Description: 查询某个条件下的所有数据
	 * @param hmxUserDto 查询参数
	 * @return List<HmxUser> 符合条件的list集合
	 */
	public List<HmxUser> list( HmxUserDto hmxUserDto ) {

		HmxUserExample hmxUserExample = new HmxUserExample();

		Criteria where = hmxUserExample.createCriteria();

		if ( hmxUserDto.getId() != null && hmxUserDto.getId() != 0 ) {
			where.andIdEqualTo( hmxUserDto.getId() );
		}
		if ( !StringUtils.isEmpty( hmxUserDto.getUserName() ) ) {
			where.andUserNameEqualTo( hmxUserDto.getUserName() );
		}
		if ( !StringUtils.isEmpty( hmxUserDto.getUserAliase() ) ) {
			where.andUserAliaseEqualTo( hmxUserDto.getUserAliase() );
		}
		if ( !StringUtils.isEmpty( hmxUserDto.getUserPhone() ) ) {
			where.andUserPhoneEqualTo( hmxUserDto.getUserPhone() );
		}
		if ( !StringUtils.isEmpty( hmxUserDto.getPassword() ) ) {
			where.andPasswordEqualTo( hmxUserDto.getPassword() );
		}
		if ( hmxUserDto.getGender() != null && hmxUserDto.getGender() != 0 ) {
			where.andGenderEqualTo( hmxUserDto.getGender() );
		}
		if ( !StringUtils.isEmpty( hmxUserDto.getHeadPic() ) ) {
			where.andHeadPicEqualTo( hmxUserDto.getHeadPic() );
		}
		if ( hmxUserDto.getCreateTime() != null ) {
			where.andCreateTimeEqualTo( hmxUserDto.getCreateTime() );
		}
		if ( hmxUserDto.getNewTime() != null ) {
			where.andNewTimeEqualTo( hmxUserDto.getNewTime() );
		}
		if ( hmxUserDto.getState() != null && hmxUserDto.getState() != 0 ) {
			where.andStateEqualTo( hmxUserDto.getState() );
		}
		if ( hmxUserDto.getVersion() != null && hmxUserDto.getVersion() != 0 ) {
			where.andVersionEqualTo( hmxUserDto.getVersion() );
		}
		if ( hmxUserDto.getCreateid() != null && hmxUserDto.getCreateid() != 0 ) {
			where.andCreateidEqualTo( hmxUserDto.getCreateid() );
		}

		if( hmxUserDto.getLimit() != null ){
			hmxUserExample.setLimit( hmxUserDto.getLimit() );
		}
		if( !StringUtils.isEmpty( hmxUserDto.getOrderByClause() ) ){
			hmxUserExample.setOrderByClause( hmxUserDto.getOrderByClause() );
		}
		return hmxUserMapper.selectByExample(hmxUserExample);
	}

	/**
	 * 用户登录
	 */
	public HmxUser login(HmxUser hmxUser) {

		HmxUserExample example = new HmxUserExample();

		Criteria userCriteria = example.createCriteria();

		if (!StringUtils.isEmpty(hmxUser.getUserPhone())) {
			userCriteria.addCriterion("user_phone=" + hmxUser.getUserPhone());
		} else if (!StringUtils.isEmpty(hmxUser.getUserName())) {
			userCriteria.addCriterion("user_name=" + hmxUser.getUserName());
		}
		// md5加密密码(加盐方式)
		String pwdMd5 = MD5Util.encode(hmxUser.getPassword());

		userCriteria.andPasswordEqualTo(pwdMd5);
		userCriteria.andStateEqualTo(0);
		List<HmxUser> users = hmxUserMapper.selectByExample(example);

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
	public HmxUser selectUserInfoByUserPhone(String userPhone){
		return hmxUserMapper.selectUserInfoByUserPhone(userPhone);
	}

	@Override
	public Result<Object> addOrUpdateUser(HmxUserDto hmxUserDto) {
		Result<Object> result = new Result<>();
		HmxUser model = null;
		Boolean flag = false;
		//如果是新用户，判断账户和手机号是否重复
		if (hmxUserDto.getId() == null) {
			HmxUser userModelName = hmxUserMapper.findUserByName(hmxUserDto.getUserName());
			//判断账号是否重复
			if (userModelName != null) {
				result.setStatus(2001);
				result.setMsg("用户名已存在！");
				return result;
			}
			//判断手机号是否重复
			HmxUser userModelPhone = hmxUserMapper.findUserBycellPhone(hmxUserDto.getUserPhone());
			if (userModelPhone != null) {
				result.setStatus(20002);
				result.setMsg("手机号已存在！");
				return result;
			}
			model = new HmxUser();
			//初始密码手机号后六位
			model.setPassword(MD5Util.encode(hmxUserDto.getUserPhone().substring(5,11)));
			flag = true;
		} else {
			model = hmxUserMapper.selectByPrimaryKey(hmxUserDto.getId());
			if (!hmxUserDto.getUserName().equals(model.getUserName())) {
				HmxUser userModelName = hmxUserMapper.findUserByName(hmxUserDto.getUserName());
				//判断账号是否重复
				if (userModelName != null) {
					result.setStatus(20001);
					result.setMsg("用户名已存在！");
					return result;
				}
			}
			if (!model.getUserPhone().equals(hmxUserDto.getUserPhone())) {
				//判断手机号是否重复
				HmxUser userModelPhone = hmxUserMapper.findUserBycellPhone(hmxUserDto.getUserPhone());
				if (userModelPhone != null) {
					result.setStatus(20002);
					result.setMsg("手机号已存在！");
					return result;
				}
			}
		}
		model.setId(hmxUserDto.getId());
		model.setUserName(hmxUserDto.getUserName());
		model.setUserPhone(hmxUserDto.getUserPhone());
		model.setState(0);
		model.setType(hmxUserDto.getType());
		model.setGender(hmxUserDto.getGender());
		model.setUserAliase(hmxUserDto.getUserAliase());
		if(flag){
			hmxUserMapper.insert(model);
		}else {
			hmxUserMapper.updateByPrimaryKeySelective(model);
		}
		result.setStatus(10000);
		result.setMsg("操作成功");
		//加入用户组
//		UserRoleModel userRoleModel = userRoleDao.findUserByUserId(model.getId());
//		if (userRoleModel == null) {
//			userRoleModel = new UserRoleModel();
//		}
//		userRoleModel.setUserId(model.getId());
//		userRoleModel.setRoleId(userData.getRole().getId());
//		userRoleDao.saveAndFlush(userRoleModel);
		return result;
	}
}
 
