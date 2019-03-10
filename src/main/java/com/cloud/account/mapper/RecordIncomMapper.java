package com.cloud.account.mapper;

import com.cloud.account.entity.RecordIncom;

public interface RecordIncomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecordIncom record);

    int insertSelective(RecordIncom record);

    RecordIncom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecordIncom record);

    int updateByPrimaryKey(RecordIncom record);
}