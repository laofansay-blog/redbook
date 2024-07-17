package com.laofansay.work.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laofansay.work.domain.enumeration.ChannelCate;
import com.laofansay.work.domain.enumeration.JobStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 小红书任务强果
 */
@Schema(description = "小红书任务强果")
@Document(collection = "job_result")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称", required = true)
    @NotNull
    @Field("name")
    private String name;

    /**
     * 任务url
     */
    @Schema(description = "任务url", required = true)
    @NotNull
    @Field("job_url")
    private String jobUrl;

    /**
     * 任务作者名称
     */
    @Schema(description = "任务作者名称", required = true)
    @NotNull
    @Field("author_name")
    private String authorName;

    /**
     * 账户Id
     */
    @Schema(description = "账户Id", required = true)
    @NotNull
    @Field("account_id")
    private String accountId;

    /**
     * 账户Id
     */
    @Schema(description = "账户Id", required = true)
    @NotNull
    @Field("customer_id")
    private String customerId;

    /**
     * 任务状态
     */
    @Schema(description = "任务状态", required = true)
    @NotNull
    @Field("status")
    private JobStatus status;

    /**
     * 任务日期
     */
    @Schema(description = "任务日期", required = true)
    @NotNull
    @Field("job_date")
    private LocalDate jobDate;

    /**
     * 任务编号
     */
    @Schema(description = "任务编号", required = true)
    @NotNull
    @Field("job_no")
    private String jobNo;

    /**
     * 生成时间时间
     */
    @Schema(description = "生成时间时间", required = true)
    @NotNull
    @Field("builder_date")
    private Instant builderDate;

    /**
     * 回复内容
     */
    @Schema(description = "回复内容", required = true)
    @NotNull
    @Field("replay")
    private String replay;

    /**
     * 回复主题
     */
    @Schema(description = "回复主题", required = true)
    @NotNull
    @Field("replay_theme")
    private String replayTheme;

    /**
     * 回复结果截图
     */
    @Schema(description = "回复结果截图")
    @Field("replay_image")
    private byte[] replayImage;

    @Field("replay_image_content_type")
    private String replayImageContentType;

    /**
     * 回复时间
     */
    @Schema(description = "回复时间")
    @Field("replay_date")
    private Instant replayDate;

    /**
     * 有效的回复
     */
    @Schema(description = "有效的回复")
    @Field("eff_replay")
    private Boolean effReplay;

    /**
     * 是否结算
     */
    @Schema(description = "是否结算")
    @Field("settlement")
    private Boolean settlement;

    /**
     * 结算订单号
     */
    @Schema(description = "结算订单号")
    @Field("settlement_order")
    private Boolean settlementOrder;

    /**
     * 结算时间
     */
    @Schema(description = "结算时间")
    @Field("settlement_date")
    private Instant settlementDate;

    /**
     * 异常描述
     */
    @Schema(description = "异常描述")
    @Field("error_msg")
    private String errorMsg;

    /**
     * 异常截图
     */
    @Schema(description = "异常截图")
    @Field("error_image")
    private byte[] errorImage;

    @Field("error_image_content_type")
    private String errorImageContentType;

    /**
     * 客户渠道
     */
    @Schema(description = "客户渠道", required = true)
    @NotNull
    @Field("channel")
    private ChannelCate channel;

    @DBRef
    @Field("cstJob")
    @JsonIgnoreProperties(value = { "jobResults", "customer" }, allowSetters = true)
    private CstJob cstJob;

    @DBRef
    @Field("jobOrders")
    @JsonIgnoreProperties(value = { "jobResults" }, allowSetters = true)
    private Set<JobOrder> jobOrders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public JobResult id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public JobResult name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobUrl() {
        return this.jobUrl;
    }

    public JobResult jobUrl(String jobUrl) {
        this.setJobUrl(jobUrl);
        return this;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public JobResult authorName(String authorName) {
        this.setAuthorName(authorName);
        return this;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public JobResult accountId(String accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public JobResult customerId(String customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public JobStatus getStatus() {
        return this.status;
    }

    public JobResult status(JobStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public LocalDate getJobDate() {
        return this.jobDate;
    }

    public JobResult jobDate(LocalDate jobDate) {
        this.setJobDate(jobDate);
        return this;
    }

    public void setJobDate(LocalDate jobDate) {
        this.jobDate = jobDate;
    }

    public String getJobNo() {
        return this.jobNo;
    }

    public JobResult jobNo(String jobNo) {
        this.setJobNo(jobNo);
        return this;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public Instant getBuilderDate() {
        return this.builderDate;
    }

    public JobResult builderDate(Instant builderDate) {
        this.setBuilderDate(builderDate);
        return this;
    }

    public void setBuilderDate(Instant builderDate) {
        this.builderDate = builderDate;
    }

    public String getReplay() {
        return this.replay;
    }

    public JobResult replay(String replay) {
        this.setReplay(replay);
        return this;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    public String getReplayTheme() {
        return this.replayTheme;
    }

    public JobResult replayTheme(String replayTheme) {
        this.setReplayTheme(replayTheme);
        return this;
    }

    public void setReplayTheme(String replayTheme) {
        this.replayTheme = replayTheme;
    }

    public byte[] getReplayImage() {
        return this.replayImage;
    }

    public JobResult replayImage(byte[] replayImage) {
        this.setReplayImage(replayImage);
        return this;
    }

    public void setReplayImage(byte[] replayImage) {
        this.replayImage = replayImage;
    }

    public String getReplayImageContentType() {
        return this.replayImageContentType;
    }

    public JobResult replayImageContentType(String replayImageContentType) {
        this.replayImageContentType = replayImageContentType;
        return this;
    }

    public void setReplayImageContentType(String replayImageContentType) {
        this.replayImageContentType = replayImageContentType;
    }

    public Instant getReplayDate() {
        return this.replayDate;
    }

    public JobResult replayDate(Instant replayDate) {
        this.setReplayDate(replayDate);
        return this;
    }

    public void setReplayDate(Instant replayDate) {
        this.replayDate = replayDate;
    }

    public Boolean getEffReplay() {
        return this.effReplay;
    }

    public JobResult effReplay(Boolean effReplay) {
        this.setEffReplay(effReplay);
        return this;
    }

    public void setEffReplay(Boolean effReplay) {
        this.effReplay = effReplay;
    }

    public Boolean getSettlement() {
        return this.settlement;
    }

    public JobResult settlement(Boolean settlement) {
        this.setSettlement(settlement);
        return this;
    }

    public void setSettlement(Boolean settlement) {
        this.settlement = settlement;
    }

    public Boolean getSettlementOrder() {
        return this.settlementOrder;
    }

    public JobResult settlementOrder(Boolean settlementOrder) {
        this.setSettlementOrder(settlementOrder);
        return this;
    }

    public void setSettlementOrder(Boolean settlementOrder) {
        this.settlementOrder = settlementOrder;
    }

    public Instant getSettlementDate() {
        return this.settlementDate;
    }

    public JobResult settlementDate(Instant settlementDate) {
        this.setSettlementDate(settlementDate);
        return this;
    }

    public void setSettlementDate(Instant settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public JobResult errorMsg(String errorMsg) {
        this.setErrorMsg(errorMsg);
        return this;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public byte[] getErrorImage() {
        return this.errorImage;
    }

    public JobResult errorImage(byte[] errorImage) {
        this.setErrorImage(errorImage);
        return this;
    }

    public void setErrorImage(byte[] errorImage) {
        this.errorImage = errorImage;
    }

    public String getErrorImageContentType() {
        return this.errorImageContentType;
    }

    public JobResult errorImageContentType(String errorImageContentType) {
        this.errorImageContentType = errorImageContentType;
        return this;
    }

    public void setErrorImageContentType(String errorImageContentType) {
        this.errorImageContentType = errorImageContentType;
    }

    public ChannelCate getChannel() {
        return this.channel;
    }

    public JobResult channel(ChannelCate channel) {
        this.setChannel(channel);
        return this;
    }

    public void setChannel(ChannelCate channel) {
        this.channel = channel;
    }

    public CstJob getCstJob() {
        return this.cstJob;
    }

    public void setCstJob(CstJob cstJob) {
        this.cstJob = cstJob;
    }

    public JobResult cstJob(CstJob cstJob) {
        this.setCstJob(cstJob);
        return this;
    }

    public Set<JobOrder> getJobOrders() {
        return this.jobOrders;
    }

    public void setJobOrders(Set<JobOrder> jobOrders) {
        if (this.jobOrders != null) {
            this.jobOrders.forEach(i -> i.removeJobResult(this));
        }
        if (jobOrders != null) {
            jobOrders.forEach(i -> i.addJobResult(this));
        }
        this.jobOrders = jobOrders;
    }

    public JobResult jobOrders(Set<JobOrder> jobOrders) {
        this.setJobOrders(jobOrders);
        return this;
    }

    public JobResult addJobOrder(JobOrder jobOrder) {
        this.jobOrders.add(jobOrder);
        jobOrder.getJobResults().add(this);
        return this;
    }

    public JobResult removeJobOrder(JobOrder jobOrder) {
        this.jobOrders.remove(jobOrder);
        jobOrder.getJobResults().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobResult)) {
            return false;
        }
        return getId() != null && getId().equals(((JobResult) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobResult{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", jobUrl='" + getJobUrl() + "'" +
            ", authorName='" + getAuthorName() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            ", status='" + getStatus() + "'" +
            ", jobDate='" + getJobDate() + "'" +
            ", jobNo='" + getJobNo() + "'" +
            ", builderDate='" + getBuilderDate() + "'" +
            ", replay='" + getReplay() + "'" +
            ", replayTheme='" + getReplayTheme() + "'" +
            ", replayImage='" + getReplayImage() + "'" +
            ", replayImageContentType='" + getReplayImageContentType() + "'" +
            ", replayDate='" + getReplayDate() + "'" +
            ", effReplay='" + getEffReplay() + "'" +
            ", settlement='" + getSettlement() + "'" +
            ", settlementOrder='" + getSettlementOrder() + "'" +
            ", settlementDate='" + getSettlementDate() + "'" +
            ", errorMsg='" + getErrorMsg() + "'" +
            ", errorImage='" + getErrorImage() + "'" +
            ", errorImageContentType='" + getErrorImageContentType() + "'" +
            ", channel='" + getChannel() + "'" +
            "}";
    }
}
