package com.laofansay.work.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laofansay.work.domain.enumeration.ChannelCate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 渠道
 */
@Schema(description = "渠道")
@Document(collection = "channels")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Channels implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * 渠道名称
     */
    @Schema(description = "渠道名称", required = true)
    @NotNull
    @Field("name")
    private String name;

    /**
     * 渠道类别
     */
    @Schema(description = "渠道类别", required = true)
    @NotNull
    @Field("category")
    private ChannelCate category;

    /**
     * 比例
     */
    @Schema(description = "比例", required = true)
    @NotNull
    @Field("rate")
    private Integer rate;

    @Field("props")
    private String props;

    /**
     * 渠道是否开放
     */
    @Schema(description = "渠道是否开放", required = true)
    @NotNull
    @Field("open")
    private Boolean open;

    @DBRef
    @Field("customer")
    @JsonIgnoreProperties(value = { "cstAccounts", "cstJobs", "channels" }, allowSetters = true)
    private Set<Customer> customers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Channels id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Channels name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChannelCate getCategory() {
        return this.category;
    }

    public Channels category(ChannelCate category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(ChannelCate category) {
        this.category = category;
    }

    public Integer getRate() {
        return this.rate;
    }

    public Channels rate(Integer rate) {
        this.setRate(rate);
        return this;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getProps() {
        return this.props;
    }

    public Channels props(String props) {
        this.setProps(props);
        return this;
    }

    public void setProps(String props) {
        this.props = props;
    }

    public Boolean getOpen() {
        return this.open;
    }

    public Channels open(Boolean open) {
        this.setOpen(open);
        return this;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Set<Customer> getCustomers() {
        return this.customers;
    }

    public void setCustomers(Set<Customer> customers) {
        if (this.customers != null) {
            this.customers.forEach(i -> i.setChannels(null));
        }
        if (customers != null) {
            customers.forEach(i -> i.setChannels(this));
        }
        this.customers = customers;
    }

    public Channels customers(Set<Customer> customers) {
        this.setCustomers(customers);
        return this;
    }

    public Channels addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.setChannels(this);
        return this;
    }

    public Channels removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.setChannels(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Channels)) {
            return false;
        }
        return getId() != null && getId().equals(((Channels) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Channels{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            ", rate=" + getRate() +
            ", props='" + getProps() + "'" +
            ", open='" + getOpen() + "'" +
            "}";
    }
}
