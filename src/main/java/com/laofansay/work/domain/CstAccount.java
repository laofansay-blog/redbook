package com.laofansay.work.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laofansay.work.domain.enumeration.CstStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 小红书用户
 */
@Schema(description = "小红书用户")
@Document(collection = "cst_account")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CstAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * 名称
     */
    @Schema(description = "名称", required = true)
    @NotNull
    @Field("name")
    private String name;

    /**
     * 账号提供方
     */
    @Schema(description = "账号提供方", required = true)
    @NotNull
    @Field("provider")
    private String provider;

    /**
     * 账号
     */
    @Schema(description = "账号", required = true)
    @NotNull
    @Field("rb_account")
    private String rbAccount;

    /**
     * 密码
     */
    @Schema(description = "密码", required = true)
    @NotNull
    @Field("rb_pwd")
    private String rbPwd;

    /**
     * token
     */
    @Schema(description = "token")
    @Field("rb_token")
    private String rbToken;

    /**
     * 状态
     */
    @Schema(description = "状态", required = true)
    @NotNull
    @Field("status")
    private CstStatus status;

    /**
     * 当天使用次数
     */
    @Schema(description = "当天使用次数", required = true)
    @NotNull
    @Field("times_by_day")
    private Integer timesByDay;

    /**
     * 添加时间
     */
    @Schema(description = "添加时间")
    @Field("created_date")
    private Instant createdDate;

    /**
     * 客户类型
     */
    @Schema(description = "客户类型", required = true)
    @NotNull
    @Field("channel")
    private String channel;

    @DBRef
    @Field("customer")
    @JsonIgnoreProperties(value = { "cstAccounts", "cstJobs", "channels" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public CstAccount id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CstAccount name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return this.provider;
    }

    public CstAccount provider(String provider) {
        this.setProvider(provider);
        return this;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getRbAccount() {
        return this.rbAccount;
    }

    public CstAccount rbAccount(String rbAccount) {
        this.setRbAccount(rbAccount);
        return this;
    }

    public void setRbAccount(String rbAccount) {
        this.rbAccount = rbAccount;
    }

    public String getRbPwd() {
        return this.rbPwd;
    }

    public CstAccount rbPwd(String rbPwd) {
        this.setRbPwd(rbPwd);
        return this;
    }

    public void setRbPwd(String rbPwd) {
        this.rbPwd = rbPwd;
    }

    public String getRbToken() {
        return this.rbToken;
    }

    public CstAccount rbToken(String rbToken) {
        this.setRbToken(rbToken);
        return this;
    }

    public void setRbToken(String rbToken) {
        this.rbToken = rbToken;
    }

    public CstStatus getStatus() {
        return this.status;
    }

    public CstAccount status(CstStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CstStatus status) {
        this.status = status;
    }

    public Integer getTimesByDay() {
        return this.timesByDay;
    }

    public CstAccount timesByDay(Integer timesByDay) {
        this.setTimesByDay(timesByDay);
        return this;
    }

    public void setTimesByDay(Integer timesByDay) {
        this.timesByDay = timesByDay;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public CstAccount createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getChannel() {
        return this.channel;
    }

    public CstAccount channel(String channel) {
        this.setChannel(channel);
        return this;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CstAccount customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CstAccount)) {
            return false;
        }
        return getId() != null && getId().equals(((CstAccount) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CstAccount{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", provider='" + getProvider() + "'" +
            ", rbAccount='" + getRbAccount() + "'" +
            ", rbPwd='" + getRbPwd() + "'" +
            ", rbToken='" + getRbToken() + "'" +
            ", status='" + getStatus() + "'" +
            ", timesByDay=" + getTimesByDay() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", channel='" + getChannel() + "'" +
            "}";
    }
}
