package com.cloud.account.mapper;

import com.cloud.account.entity.SmsCode;

public interface SmsCodeMapper {
    int deleteByPrimaryKey(String id);

    int insert(SmsCode record);

    int insertSelective(SmsCode record);

    SmsCode selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SmsCode record);

    int updateByPrimaryKey(SmsCode record);
}