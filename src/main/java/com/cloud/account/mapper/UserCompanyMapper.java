package com.cloud.account.mapper;

import com.cloud.account.entity.UserAdmin;
import com.cloud.account.entity.UserCompany;
import com.cloud.account.vo.LoginVo;

public interface UserCompanyMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserCompany record);

    int insertSelective(UserCompany record);

    UserCompany selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserCompany record);

    int updateByPrimaryKey(UserCompany record);
    
    UserCompany selectUserInfo(LoginVo record);
}