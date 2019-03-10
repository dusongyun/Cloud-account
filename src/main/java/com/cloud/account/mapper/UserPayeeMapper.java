package com.cloud.account.mapper;

import com.cloud.account.entity.UserPayee;

public interface UserPayeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPayee record);

    int insertSelective(UserPayee record);

    UserPayee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPayee record);

    int updateByPrimaryKey(UserPayee record);
}