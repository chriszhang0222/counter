package com.chris.counter.mapper;

import com.chris.counter.domain.PosiInfo;
import com.chris.counter.domain.PosiInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PosiInfoMapper {
    long countByExample(PosiInfoExample example);

    int deleteByExample(PosiInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PosiInfo record);

    int insertSelective(PosiInfo record);

    List<PosiInfo> selectByExample(PosiInfoExample example);

    PosiInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PosiInfo record, @Param("example") PosiInfoExample example);

    int updateByExample(@Param("record") PosiInfo record, @Param("example") PosiInfoExample example);

    int updateByPrimaryKeySelective(PosiInfo record);

    int updateByPrimaryKey(PosiInfo record);
}
