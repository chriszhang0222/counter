package com.chris.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OrderDto {

    public short f_type;
    public byte f_direction;
    public byte f_ordertype;

    public CmdType type;

    public long timestamp;

    /**
     * 会员ID
     */
    public short mid;

    /**
     * 用户ID
     */
    public long uid;

    /**
     * 代码
     */
    public int code;

    /**
     * 方向
     */
    public OrderDirection direction;

    /**
     * 价格
     */
    public long price;

    /**
     * 量
     */
   public long volume;

    /**
     * 委托类型
     * 1.LIMIT
     */
    public OrderType orderType;

    /**
     * 委托编号
     */
    public long oid;


}
