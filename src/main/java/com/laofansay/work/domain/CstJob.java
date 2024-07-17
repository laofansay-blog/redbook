package com.laofansay.work.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laofansay.work.domain.enumeration.ChannelCate;
import com.laofansay.work.domain.enumeration.ExecuteType;
import com.laofansay.work.domain.enumeration.JobStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 小红书任务
 */
@Schema(description = "小红书任务")
@Document(collection = "cst_job")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CstJob implements Serializable {

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
     * 执行方式
     */
    @Schema(description = "执行方式", required = true)
    @NotNull
    @Field("execute_type")
    private ExecuteType executeType;

    /**
     * 任务类型
     */
    @Schema(description = "任务类型")
    @Field("category")
    private String category;

    /**
     * 状态状态
     */
    @Schema(description = "状态状态", required = true)
    @NotNull
    @Field("status")
    private JobStatus status;

    /**
     * 添加时间
     */
    @Schema(description = "添加时间", required = true)
    @NotNull
    @Field("created_date")
    private Instant createdDate;

    /**
     * 任务业务参数
     */
    @Schema(description = "任务业务参数", required = true)
    @NotNull
    @Field("job_props")
    private String jobProps;

    /**
     * 客户渠道
     */
    @Schema(description = "客户渠道", required = true)
    @NotNull
    @Field("channel")
    private ChannelCate channel;

    @DBRef
    @Field("jobResult")
    @JsonIgnoreProperties(value = { "cstJob", "jobOrders" }, allowSetters = true)
    private Set<JobResult> jobResults = new HashSet<>();

    @DBRef
    @Field("customer")
    @JsonIgnoreProperties(value = { "cstAccounts", "cstJobs", "channels" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public CstJob id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CstJob name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExecuteType getExecuteType() {
        return this.executeType;
    }

    public CstJob executeType(ExecuteType executeType) {
        this.setExecuteType(executeType);
        return this;
    }

    public void setExecuteType(ExecuteType executeType) {
        this.executeType = executeType;
    }

    public String getCategory() {
        return this.category;
    }

    public CstJob category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public JobStatus getStatus() {
        return this.status;
    }

    public CstJob status(JobStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public CstJob createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getJobProps() {
        return this.jobProps;
    }

    public CstJob jobProps(String jobProps) {
        this.setJobProps(jobProps);
        return this;
    }

    public void setJobProps(String jobProps) {
        this.jobProps = jobProps;
    }

    public ChannelCate getChannel() {
        return this.channel;
    }

    public CstJob channel(ChannelCate channel) {
        this.setChannel(channel);
        return this;
    }

    public void setChannel(ChannelCate channel) {
        this.channel = channel;
    }

    public Set<JobResult> getJobResults() {
        return this.jobResults;
    }

    public void setJobResults(Set<JobResult> jobResults) {
        if (this.jobResults != null) {
            this.jobResults.forEach(i -> i.setCstJob(null));
        }
        if (jobResults != null) {
            jobResults.forEach(i -> i.setCstJob(this));
        }
        this.jobResults = jobResults;
    }

    public CstJob jobResults(Set<JobResult> jobResults) {
        this.setJobResults(jobResults);
        return this;
    }

    public CstJob addJobResult(JobResult jobResult) {
        this.jobResults.add(jobResult);
        jobResult.setCstJob(this);
        return this;
    }

    public CstJob removeJobResult(JobResult jobResult) {
        this.jobResults.remove(jobResult);
        jobResult.setCstJob(null);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CstJob customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CstJob)) {
            return false;
        }
        return getId() != null && getId().equals(((CstJob) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CstJob{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", executeType='" + getExecuteType() + "'" +
            ", category='" + getCategory() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", jobProps='" + getJobProps() + "'" +
            ", channel='" + getChannel() + "'" +
            "}";
    }
}
