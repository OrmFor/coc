package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;

public class PlatformEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String platformCode;
	private String platName;
	private java.math.BigDecimal amount;
	private String currency;
	private java.math.BigDecimal feeRate;
	private String walletAddress;
	private String clientIdentifier;
	private String clientSecret;
	private String webhookSecret;
	private String webhookUrl;
	private String oauthIdentifier;
	private String oauthRedirectUrl;
	private String paltMessage;
	
    public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
    public String getPlatformCode() {
        return platformCode;
    }
	public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }
    public String getPlatName() {
        return platName;
    }
	public void setPlatName(String platName) {
        this.platName = platName;
    }
    public java.math.BigDecimal getAmount() {
        return amount;
    }
	public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }
    public String getCurrency() {
        return currency;
    }
	public void setCurrency(String currency) {
        this.currency = currency;
    }
    public java.math.BigDecimal getFeeRate() {
        return feeRate;
    }
	public void setFeeRate(java.math.BigDecimal feeRate) {
        this.feeRate = feeRate;
    }
    public String getWalletAddress() {
        return walletAddress;
    }
	public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }
    public String getClientIdentifier() {
        return clientIdentifier;
    }
	public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }
    public String getClientSecret() {
        return clientSecret;
    }
	public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    public String getWebhookSecret() {
        return webhookSecret;
    }
	public void setWebhookSecret(String webhookSecret) {
        this.webhookSecret = webhookSecret;
    }
    public String getWebhookUrl() {
        return webhookUrl;
    }
	public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }
    public String getOauthIdentifier() {
        return oauthIdentifier;
    }
	public void setOauthIdentifier(String oauthIdentifier) {
        this.oauthIdentifier = oauthIdentifier;
    }
    public String getOauthRedirectUrl() {
        return oauthRedirectUrl;
    }
	public void setOauthRedirectUrl(String oauthRedirectUrl) {
        this.oauthRedirectUrl = oauthRedirectUrl;
    }
    public String getPaltMessage() {
        return paltMessage;
    }
	public void setPaltMessage(String paltMessage) {
        this.paltMessage = paltMessage;
    }
}