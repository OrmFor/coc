package com.yinmimoney.web.p2pnew.requestbody;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class LoginRequestBody {

    @NotBlank(message = "{login.deviceId.empty}")
    private String deviceId;

    @NotBlank(message = "{login.deviceName.empty}")
    private String deviceName;

    @NotBlank(message = "{login.email.empty}")
    private String email;

    @NotBlank(message = "{login.pwd.empty}")
    private String pwd;


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
