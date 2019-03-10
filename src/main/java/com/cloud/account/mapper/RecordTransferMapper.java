package com.cloud.account.mapper;

import com.cloud.account.entity.RecordTransfer;

public interface RecordTransferMapper {
    int deleteByPrimaryKey(String id);

    int insert(RecordTransfer record);

    int insertSelective(RecordTransfer record);

    RecordTransfer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RecordTransfer record);

    int updateByPrimaryKey(RecordTransfer record);
}