package com.laofansay.work.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laofansay.work.domain.enumeration.CstStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 客户
 */
@Schema(description = "客户")
@Document(collection = "customer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称", required = true)
    @NotNull
    @Field("name")
    private String name;

    /**
     * 客户手机号
     */
    @Schema(description = "客户手机号")
    @Field("mobile")
    private String mobile;

    /**
     * 客户邮箱
     */
    @Schema(description = "客户邮箱")
    @Field("email")
    private String email;

    /**
     * 客户介绍
     */
    @Schema(description = "客户介绍")
    @Field("introduce")
    private String introduce;

    /**
     * 账户余额
     */
    @Schema(description = "账户余额", required = true)
    @NotNull
    @Field("balance")
    private BigDecimal balance;

    /**
     * 可用次数
     */
    @Schema(description = "可用次数", required = true)
    @NotNull
    @Field("times")
    private Integer times;

    /**
     * 状态
     */
    @Schema(description = "状态", required = true)
    @NotNull
    @Field("status")
    private CstStatus status;

    /**
     * 添加时间
     */
    @Schema(description = "添加时间")
    @Field("created_date")
    private Instant createdDate;

    @DBRef
    @Field("cstAccount")
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<CstAccount> cstAccounts = new HashSet<>();

    @DBRef
    @Field("cstJob")
    @JsonIgnoreProperties(value = { "jobResults", "customer" }, allowSetters = true)
    private Set<CstJob> cstJobs = new HashSet<>();

    @DBRef
    @Field("channels")
    @JsonIgnoreProperties(value = { "customers" }, allowSetters = true)
    private Channels channels;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Customer id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Customer name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return this.mobile;
    }

    public Customer mobile(String mobile) {
        this.setMobile(mobile);
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public Customer email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduce() {
        return this.introduce;
    }

    public Customer introduce(String introduce) {
        this.setIntroduce(introduce);
        return this;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public Customer balance(BigDecimal balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getTimes() {
        return this.times;
    }

    public Customer times(Integer times) {
        this.setTimes(times);
        return this;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public CstStatus getStatus() {
        return this.status;
    }

    public Customer status(CstStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CstStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Customer createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Set<CstAccount> getCstAccounts() {
        return this.cstAccounts;
    }

    public void setCstAccounts(Set<CstAccount> cstAccounts) {
        if (this.cstAccounts != null) {
            this.cstAccounts.forEach(i -> i.setCustomer(null));
        }
        if (cstAccounts != null) {
            cstAccounts.forEach(i -> i.setCustomer(this));
        }
        this.cstAccounts = cstAccounts;
    }

    public Customer cstAccounts(Set<CstAccount> cstAccounts) {
        this.setCstAccounts(cstAccounts);
        return this;
    }

    public Customer addCstAccount(CstAccount cstAccount) {
        this.cstAccounts.add(cstAccount);
        cstAccount.setCustomer(this);
        return this;
    }

    public Customer removeCstAccount(CstAccount cstAccount) {
        this.cstAccounts.remove(cstAccount);
        cstAccount.setCustomer(null);
        return this;
    }

    public Set<CstJob> getCstJobs() {
        return this.cstJobs;
    }

    public void setCstJobs(Set<CstJob> cstJobs) {
        if (this.cstJobs != null) {
            this.cstJobs.forEach(i -> i.setCustomer(null));
        }
        if (cstJobs != null) {
            cstJobs.forEach(i -> i.setCustomer(this));
        }
        this.cstJobs = cstJobs;
    }

    public Customer cstJobs(Set<CstJob> cstJobs) {
        this.setCstJobs(cstJobs);
        return this;
    }

    public Customer addCstJob(CstJob cstJob) {
        this.cstJobs.add(cstJob);
        cstJob.setCustomer(this);
        return this;
    }

    public Customer removeCstJob(CstJob cstJob) {
        this.cstJobs.remove(cstJob);
        cstJob.setCustomer(null);
        return this;
    }

    public Channels getChannels() {
        return this.channels;
    }

    public void setChannels(Channels channels) {
        this.channels = channels;
    }

    public Customer channels(Channels channels) {
        this.setChannels(channels);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return getId() != null && getId().equals(((Customer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", email='" + getEmail() + "'" +
            ", introduce='" + getIntroduce() + "'" +
            ", balance=" + getBalance() +
            ", times=" + getTimes() +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
