package com.cloud.account.mapper;

import com.cloud.account.entity.CompanyRule;

public interface CompanyRuleMapper {
    int deleteByPrimaryKey(String id);

    int insert(CompanyRule record);

    int insertSelective(CompanyRule record);

    CompanyRule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CompanyRule record);

    int updateByPrimaryKey(CompanyRule record);
}