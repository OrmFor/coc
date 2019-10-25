package com.yinmimoney.web.p2pnew.dto;

import java.math.BigDecimal;

public class CityZoomDtoNew {
    private String uC;

    private BigDecimal at;

    private String cC;

    private String cN;

    private BigDecimal lat;

    private BigDecimal lon;

    private Integer iS;

    private Integer cSS;

    public String getcN() {
        return cN;
    }

    public void setcN(String cN) {
        this.cN = cN;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public String getuC() {
        return uC;
    }

    public void setuC(String uC) {
        this.uC = uC;
    }

    public BigDecimal getAt() {
        return at;
    }

    public void setAt(BigDecimal at) {
        this.at = at;
    }

    public String getcC() {
        return cC;
    }

    public void setcC(String cC) {
        this.cC = cC;
    }

    public Integer getiS() {
        return iS;
    }

    public void setiS(Integer iS) {
        this.iS = iS;
    }

    public Integer getcSS() {
        return cSS;
    }

    public void setcSS(Integer cSS) {
        this.cSS = cSS;
    }

}
