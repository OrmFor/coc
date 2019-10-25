package com.yinmimoney.web.p2pnew.requestbody;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class RegisterRequestBody implements Serializable {

    @NotBlank(message = "{user.userName.empty}")
    private String userName;

    @NotBlank(message = "{user.mobilePhone.empty}")
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "{user.mobilePhone.format}")
    private String email;// 邮箱

    @NotBlank(message = "{user.pwd.empty}")
    private String pwd;// 登录密码


    @NotNull(message = "{user.register.source.empty}")
    private Integer source;//注册来源

    @NotBlank(message = "{user.register.deviceId.empty}")
    private String deviceId;

    @NotBlank(message = "{user.register.deviceName.empty}")
    private String deviceName;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
