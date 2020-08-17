package com.chris.counter.mapper;

import com.chris.counter.domain.TradeInfo;
import com.chris.counter.domain.TradeInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TradeInfoMapper {
    long countByExample(TradeInfoExample example);

    int deleteByExample(TradeInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TradeInfo record);

    int insertSelective(TradeInfo record);

    List<TradeInfo> selectByExample(TradeInfoExample example);

    TradeInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TradeInfo record, @Param("example") TradeInfoExample example);

    int updateByExample(@Param("record") TradeInfo record, @Param("example") TradeInfoExample example);

    int updateByPrimaryKeySelective(TradeInfo record);

    int updateByPrimaryKey(TradeInfo record);
}
