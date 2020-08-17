package com.chris.counter.domain;

public class TradeInfo {
    private Long id;

    private Long uid;

    private Integer code;

    private Integer direction;

    private Long price;

    private Long tcount;

    private Integer oid;

    private String date;

    private String time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getTcount() {
        return tcount;
    }

    public void setTcount(Long tcount) {
        this.tcount = tcount;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
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
        sb.append(", price=").append(price);
        sb.append(", tcount=").append(tcount);
        sb.append(", oid=").append(oid);
        sb.append(", date=").append(date);
        sb.append(", time=").append(time);
        sb.append("]");
        return sb.toString();
    }
}