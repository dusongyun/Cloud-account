package com.cloud.account.mapper;

import com.cloud.account.entity.UserAdmin;
import com.cloud.account.vo.LoginVo;

public interface UserAdminMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserAdmin record);

    int insertSelective(UserAdmin record);

    UserAdmin selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserAdmin record);

    int updateByPrimaryKey(UserAdmin record);
    
    UserAdmin selectUserInfo(LoginVo record);
}