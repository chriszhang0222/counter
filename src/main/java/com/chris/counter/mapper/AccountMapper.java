package com.chris.counter.mapper;

import com.chris.counter.domain.Account;
import com.chris.counter.domain.AccountExample;
import java.util.List;

import com.chris.counter.dto.AccountDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {
    long countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    List<Account> selectByExample(AccountExample example);

    Account selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    AccountDto queryAccount(@Param("name") String name, @Param("password") String password);

    int updateLoginTime(@Param("id") Integer id, @Param("modifyDate")String modifyDate,
                        @Param("modifyTime")String modifyTime);

    long queryBalance(@Param("uid") Long uid);
}
