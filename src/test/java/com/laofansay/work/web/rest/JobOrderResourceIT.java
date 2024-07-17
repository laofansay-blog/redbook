package com.laofansay.work.web.rest;

import static com.laofansay.work.domain.JobOrderAsserts.*;
import static com.laofansay.work.web.rest.TestUtil.createUpdateProxyForBean;
import static com.laofansay.work.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laofansay.work.IntegrationTest;
import com.laofansay.work.domain.JobOrder;
import com.laofansay.work.domain.enumeration.PaymentStatus;
import com.laofansay.work.repository.JobOrderRepository;
import com.laofansay.work.service.JobOrderService;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link JobOrderResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class JobOrderResourceIT {

    private static final String DEFAULT_SETTLEMENT_ORDER_NO = "AAAAAAAAAA";
    private static final String UPDATED_SETTLEMENT_ORDER_NO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.WAIT;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.PAYING;

    private static final Instant DEFAULT_SETTLEMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SETTLEMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_PAYMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final String DEFAULT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/job-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private JobOrderRepository jobOrderRepository;

    @Mock
    private JobOrderRepository jobOrderRepositoryMock;

    @Mock
    private JobOrderService jobOrderServiceMock;

    @Autowired
    private MockMvc restJobOrderMockMvc;

    private JobOrder jobOrder;

    private JobOrder insertedJobOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobOrder createEntity() {
        JobOrder jobOrder = new JobOrder()
            .settlementOrderNo(DEFAULT_SETTLEMENT_ORDER_NO)
            .amount(DEFAULT_AMOUNT)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .settlementDate(DEFAULT_SETTLEMENT_DATE)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .customerId(DEFAULT_CUSTOMER_ID)
            .channel(DEFAULT_CHANNEL);
        return jobOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobOrder createUpdatedEntity() {
        JobOrder jobOrder = new JobOrder()
            .settlementOrderNo(UPDATED_SETTLEMENT_ORDER_NO)
            .amount(UPDATED_AMOUNT)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .settlementDate(UPDATED_SETTLEMENT_DATE)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .customerId(UPDATED_CUSTOMER_ID)
            .channel(UPDATED_CHANNEL);
        return jobOrder;
    }

    @BeforeEach
    public void initTest() {
        jobOrder = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedJobOrder != null) {
            jobOrderRepository.delete(insertedJobOrder);
            insertedJobOrder = null;
        }
    }

    @Test
    void createJobOrder() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the JobOrder
        var returnedJobOrder = om.readValue(
            restJobOrderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobOrder)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            JobOrder.class
        );

        // Validate the JobOrder in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertJobOrderUpdatableFieldsEquals(returnedJobOrder, getPersistedJobOrder(returnedJobOrder));

        insertedJobOrder = returnedJobOrder;
    }

    @Test
    void createJobOrderWithExistingId() throws Exception {
        // Create the JobOrder with an existing ID
        jobOrder.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobOrder)))
            .andExpect(status().isBadRequest());

        // Validate the JobOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkSettlementOrderNoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobOrder.setSettlementOrderNo(null);

        // Create the JobOrder, which fails.

        restJobOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkAmountIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobOrder.setAmount(null);

        // Create the JobOrder, which fails.

        restJobOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPaymentStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobOrder.setPaymentStatus(null);

        // Create the JobOrder, which fails.

        restJobOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkSettlementDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobOrder.setSettlementDate(null);

        // Create the JobOrder, which fails.

        restJobOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPaymentDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobOrder.setPaymentDate(null);

        // Create the JobOrder, which fails.

        restJobOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCustomerIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobOrder.setCustomerId(null);

        // Create the JobOrder, which fails.

        restJobOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkChannelIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobOrder.setChannel(null);

        // Create the JobOrder, which fails.

        restJobOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllJobOrders() throws Exception {
        // Initialize the database
        insertedJobOrder = jobOrderRepository.save(jobOrder);

        // Get all the jobOrderList
        restJobOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobOrder.getId())))
            .andExpect(jsonPath("$.[*].settlementOrderNo").value(hasItem(DEFAULT_SETTLEMENT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].settlementDate").value(hasItem(DEFAULT_SETTLEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllJobOrdersWithEagerRelationshipsIsEnabled() throws Exception {
        when(jobOrderServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJobOrderMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(jobOrderServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllJobOrdersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(jobOrderServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJobOrderMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(jobOrderRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getJobOrder() throws Exception {
        // Initialize the database
        insertedJobOrder = jobOrderRepository.save(jobOrder);

        // Get the jobOrder
        restJobOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, jobOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobOrder.getId()))
            .andExpect(jsonPath("$.settlementOrderNo").value(DEFAULT_SETTLEMENT_ORDER_NO))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.settlementDate").value(DEFAULT_SETTLEMENT_DATE.toString()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL));
    }

    @Test
    void getNonExistingJobOrder() throws Exception {
        // Get the jobOrder
        restJobOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingJobOrder() throws Exception {
        // Initialize the database
        insertedJobOrder = jobOrderRepository.save(jobOrder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jobOrder
        JobOrder updatedJobOrder = jobOrderRepository.findById(jobOrder.getId()).orElseThrow();
        updatedJobOrder
            .settlementOrderNo(UPDATED_SETTLEMENT_ORDER_NO)
            .amount(UPDATED_AMOUNT)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .settlementDate(UPDATED_SETTLEMENT_DATE)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .customerId(UPDATED_CUSTOMER_ID)
            .channel(UPDATED_CHANNEL);

        restJobOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobOrder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedJobOrder))
            )
            .andExpect(status().isOk());

        // Validate the JobOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedJobOrderToMatchAllProperties(updatedJobOrder);
    }

    @Test
    void putNonExistingJobOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobOrder.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobOrder.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchJobOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobOrder.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jobOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamJobOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobOrder.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobOrderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobOrder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateJobOrderWithPatch() throws Exception {
        // Initialize the database
        insertedJobOrder = jobOrderRepository.save(jobOrder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jobOrder using partial update
        JobOrder partialUpdatedJobOrder = new JobOrder();
        partialUpdatedJobOrder.setId(jobOrder.getId());

        partialUpdatedJobOrder
            .settlementOrderNo(UPDATED_SETTLEMENT_ORDER_NO)
            .amount(UPDATED_AMOUNT)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .channel(UPDATED_CHANNEL);

        restJobOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJobOrder))
            )
            .andExpect(status().isOk());

        // Validate the JobOrder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJobOrderUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedJobOrder, jobOrder), getPersistedJobOrder(jobOrder));
    }

    @Test
    void fullUpdateJobOrderWithPatch() throws Exception {
        // Initialize the database
        insertedJobOrder = jobOrderRepository.save(jobOrder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jobOrder using partial update
        JobOrder partialUpdatedJobOrder = new JobOrder();
        partialUpdatedJobOrder.setId(jobOrder.getId());

        partialUpdatedJobOrder
            .settlementOrderNo(UPDATED_SETTLEMENT_ORDER_NO)
            .amount(UPDATED_AMOUNT)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .settlementDate(UPDATED_SETTLEMENT_DATE)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .customerId(UPDATED_CUSTOMER_ID)
            .channel(UPDATED_CHANNEL);

        restJobOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJobOrder))
            )
            .andExpect(status().isOk());

        // Validate the JobOrder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJobOrderUpdatableFieldsEquals(partialUpdatedJobOrder, getPersistedJobOrder(partialUpdatedJobOrder));
    }

    @Test
    void patchNonExistingJobOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobOrder.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jobOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchJobOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobOrder.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jobOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamJobOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobOrder.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobOrderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(jobOrder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteJobOrder() throws Exception {
        // Initialize the database
        insertedJobOrder = jobOrderRepository.save(jobOrder);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the jobOrder
        restJobOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return jobOrderRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected JobOrder getPersistedJobOrder(JobOrder jobOrder) {
        return jobOrderRepository.findById(jobOrder.getId()).orElseThrow();
    }

    protected void assertPersistedJobOrderToMatchAllProperties(JobOrder expectedJobOrder) {
        assertJobOrderAllPropertiesEquals(expectedJobOrder, getPersistedJobOrder(expectedJobOrder));
    }

    protected void assertPersistedJobOrderToMatchUpdatableProperties(JobOrder expectedJobOrder) {
        assertJobOrderAllUpdatablePropertiesEquals(expectedJobOrder, getPersistedJobOrder(expectedJobOrder));
    }
}
