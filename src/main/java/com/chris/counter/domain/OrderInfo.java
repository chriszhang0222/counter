package com.chris.counter.domain;

public class OrderInfo {
    private Integer id;

    private Long uid;

    private Integer code;

    private Integer direction;

    private Integer type;

    private Long price;

    private Long ocount;

    private Integer status;

    private String date;

    private String time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getOcount() {
        return ocount;
    }

    public void setOcount(Long ocount) {
        this.ocount = ocount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", code=").append(code);
        sb.append(", direction=").append(direction);
        sb.append(", type=").append(type);
        sb.append(", price=").append(price);
        sb.append(", ocount=").append(ocount);
        sb.append(", status=").append(status);
        sb.append(", date=").append(date);
        sb.append(", time=").append(time);
        sb.append("]");
        return sb.toString();
    }
}