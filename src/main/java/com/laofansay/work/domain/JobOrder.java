package com.laofansay.work.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laofansay.work.domain.enumeration.PaymentStatus;
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
 * 小红书任务强求果
 */
@Schema(description = "小红书任务强求果")
@Document(collection = "job_order")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称", required = true)
    @NotNull
    @Field("settlement_order_no")
    private String settlementOrderNo;

    /**
     * 总金额
     */
    @Schema(description = "总金额", required = true)
    @NotNull
    @Field("amount")
    private BigDecimal amount;

    /**
     * 任务状态
     */
    @Schema(description = "任务状态", required = true)
    @NotNull
    @Field("payment_status")
    private PaymentStatus paymentStatus;

    /**
     * 结算时间
     */
    @Schema(description = "结算时间", required = true)
    @NotNull
    @Field("settlement_date")
    private Instant settlementDate;

    /**
     * 支付时间
     */
    @Schema(description = "支付时间", required = true)
    @NotNull
    @Field("payment_date")
    private Instant paymentDate;

    /**
     * 客户Id
     */
    @Schema(description = "客户Id", required = true)
    @NotNull
    @Field("customer_id")
    private Long customerId;

    /**
     * 客户渠道
     */
    @Schema(description = "客户渠道", required = true)
    @NotNull
    @Field("channel")
    private String channel;

    @DBRef
    @Field("jobResults")
    @JsonIgnoreProperties(value = { "cstJob", "jobOrders" }, allowSetters = true)
    private Set<JobResult> jobResults = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public JobOrder id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSettlementOrderNo() {
        return this.settlementOrderNo;
    }

    public JobOrder settlementOrderNo(String settlementOrderNo) {
        this.setSettlementOrderNo(settlementOrderNo);
        return this;
    }

    public void setSettlementOrderNo(String settlementOrderNo) {
        this.settlementOrderNo = settlementOrderNo;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public JobOrder amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public JobOrder paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Instant getSettlementDate() {
        return this.settlementDate;
    }

    public JobOrder settlementDate(Instant settlementDate) {
        this.setSettlementDate(settlementDate);
        return this;
    }

    public void setSettlementDate(Instant settlementDate) {
        this.settlementDate = settlementDate;
    }

    public Instant getPaymentDate() {
        return this.paymentDate;
    }

    public JobOrder paymentDate(Instant paymentDate) {
        this.setPaymentDate(paymentDate);
        return this;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public JobOrder customerId(Long customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getChannel() {
        return this.channel;
    }

    public JobOrder channel(String channel) {
        this.setChannel(channel);
        return this;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Set<JobResult> getJobResults() {
        return this.jobResults;
    }

    public void setJobResults(Set<JobResult> jobResults) {
        this.jobResults = jobResults;
    }

    public JobOrder jobResults(Set<JobResult> jobResults) {
        this.setJobResults(jobResults);
        return this;
    }

    public JobOrder addJobResult(JobResult jobResult) {
        this.jobResults.add(jobResult);
        return this;
    }

    public JobOrder removeJobResult(JobResult jobResult) {
        this.jobResults.remove(jobResult);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobOrder)) {
            return false;
        }
        return getId() != null && getId().equals(((JobOrder) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobOrder{" +
            "id=" + getId() +
            ", settlementOrderNo='" + getSettlementOrderNo() + "'" +
            ", amount=" + getAmount() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", settlementDate='" + getSettlementDate() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", customerId=" + getCustomerId() +
            ", channel='" + getChannel() + "'" +
            "}";
    }
}
