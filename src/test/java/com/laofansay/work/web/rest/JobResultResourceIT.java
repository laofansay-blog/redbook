package com.laofansay.work.web.rest;

import static com.laofansay.work.domain.JobResultAsserts.*;
import static com.laofansay.work.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laofansay.work.IntegrationTest;
import com.laofansay.work.domain.JobResult;
import com.laofansay.work.domain.enumeration.ChannelCate;
import com.laofansay.work.domain.enumeration.JobStatus;
import com.laofansay.work.repository.JobResultRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link JobResultResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobResultResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_URL = "AAAAAAAAAA";
    private static final String UPDATED_JOB_URL = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final JobStatus DEFAULT_STATUS = JobStatus.OVER;
    private static final JobStatus UPDATED_STATUS = JobStatus.READY;

    private static final LocalDate DEFAULT_JOB_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOB_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_JOB_NO = "AAAAAAAAAA";
    private static final String UPDATED_JOB_NO = "BBBBBBBBBB";

    private static final Instant DEFAULT_BUILDER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BUILDER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REPLAY = "AAAAAAAAAA";
    private static final String UPDATED_REPLAY = "BBBBBBBBBB";

    private static final String DEFAULT_REPLAY_THEME = "AAAAAAAAAA";
    private static final String UPDATED_REPLAY_THEME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_REPLAY_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_REPLAY_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_REPLAY_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_REPLAY_IMAGE_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_REPLAY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REPLAY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_EFF_REPLAY = false;
    private static final Boolean UPDATED_EFF_REPLAY = true;

    private static final Boolean DEFAULT_SETTLEMENT = false;
    private static final Boolean UPDATED_SETTLEMENT = true;

    private static final Boolean DEFAULT_SETTLEMENT_ORDER = false;
    private static final Boolean UPDATED_SETTLEMENT_ORDER = true;

    private static final Instant DEFAULT_SETTLEMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SETTLEMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ERROR_MSG = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_MSG = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ERROR_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ERROR_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ERROR_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ERROR_IMAGE_CONTENT_TYPE = "image/png";

    private static final ChannelCate DEFAULT_CHANNEL = ChannelCate.SmallRedBook;
    private static final ChannelCate UPDATED_CHANNEL = ChannelCate.ByteDance;

    private static final String ENTITY_API_URL = "/api/job-results";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private JobResultRepository jobResultRepository;

    @Autowired
    private MockMvc restJobResultMockMvc;

    private JobResult jobResult;

    private JobResult insertedJobResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobResult createEntity() {
        JobResult jobResult = new JobResult()
            .name(DEFAULT_NAME)
            .jobUrl(DEFAULT_JOB_URL)
            .authorName(DEFAULT_AUTHOR_NAME)
            .accountId(DEFAULT_ACCOUNT_ID)
            .customerId(DEFAULT_CUSTOMER_ID)
            .status(DEFAULT_STATUS)
            .jobDate(DEFAULT_JOB_DATE)
            .jobNo(DEFAULT_JOB_NO)
            .builderDate(DEFAULT_BUILDER_DATE)
            .replay(DEFAULT_REPLAY)
            .replayTheme(DEFAULT_REPLAY_THEME)
            .replayImage(DEFAULT_REPLAY_IMAGE)
            .replayImageContentType(DEFAULT_REPLAY_IMAGE_CONTENT_TYPE)
            .replayDate(DEFAULT_REPLAY_DATE)
            .effReplay(DEFAULT_EFF_REPLAY)
            .settlement(DEFAULT_SETTLEMENT)
            .settlementOrder(DEFAULT_SETTLEMENT_ORDER)
            .settlementDate(DEFAULT_SETTLEMENT_DATE)
            .errorMsg(DEFAULT_ERROR_MSG)
            .errorImage(DEFAULT_ERROR_IMAGE)
            .errorImageContentType(DEFAULT_ERROR_IMAGE_CONTENT_TYPE)
            .channel(DEFAULT_CHANNEL);
        return jobResult;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobResult createUpdatedEntity() {
        JobResult jobResult = new JobResult()
            .name(UPDATED_NAME)
            .jobUrl(UPDATED_JOB_URL)
            .authorName(UPDATED_AUTHOR_NAME)
            .accountId(UPDATED_ACCOUNT_ID)
            .customerId(UPDATED_CUSTOMER_ID)
            .status(UPDATED_STATUS)
            .jobDate(UPDATED_JOB_DATE)
            .jobNo(UPDATED_JOB_NO)
            .builderDate(UPDATED_BUILDER_DATE)
            .replay(UPDATED_REPLAY)
            .replayTheme(UPDATED_REPLAY_THEME)
            .replayImage(UPDATED_REPLAY_IMAGE)
            .replayImageContentType(UPDATED_REPLAY_IMAGE_CONTENT_TYPE)
            .replayDate(UPDATED_REPLAY_DATE)
            .effReplay(UPDATED_EFF_REPLAY)
            .settlement(UPDATED_SETTLEMENT)
            .settlementOrder(UPDATED_SETTLEMENT_ORDER)
            .settlementDate(UPDATED_SETTLEMENT_DATE)
            .errorMsg(UPDATED_ERROR_MSG)
            .errorImage(UPDATED_ERROR_IMAGE)
            .errorImageContentType(UPDATED_ERROR_IMAGE_CONTENT_TYPE)
            .channel(UPDATED_CHANNEL);
        return jobResult;
    }

    @BeforeEach
    public void initTest() {
        jobResult = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedJobResult != null) {
            jobResultRepository.delete(insertedJobResult);
            insertedJobResult = null;
        }
    }

    @Test
    void createJobResult() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the JobResult
        var returnedJobResult = om.readValue(
            restJobResultMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            JobResult.class
        );

        // Validate the JobResult in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertJobResultUpdatableFieldsEquals(returnedJobResult, getPersistedJobResult(returnedJobResult));

        insertedJobResult = returnedJobResult;
    }

    @Test
    void createJobResultWithExistingId() throws Exception {
        // Create the JobResult with an existing ID
        jobResult.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult)))
            .andExpect(status().isBadRequest());

        // Validate the JobResult in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobResult.setName(null);

        // Create the JobResult, which fails.

        restJobResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkJobUrlIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobResult.setJobUrl(null);

        // Create the JobResult, which fails.

        restJobResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkAuthorNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobResult.setAuthorName(null);

        // Create the JobResult, which fails.

        restJobResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkAccountIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobResult.setAccountId(null);

        // Create the JobResult, which fails.

        restJobResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCustomerIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobResult.setCustomerId(null);

        // Create the JobResult, which fails.

        restJobResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobResult.setStatus(null);

        // Create the JobResult, which fails.

        restJobResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkJobDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobResult.setJobDate(null);

        // Create the JobResult, which fails.

        restJobResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkJobNoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobResult.setJobNo(null);

        // Create the JobResult, which fails.

        restJobResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkBuilderDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobResult.setBuilderDate(null);

        // Create the JobResult, which fails.

        restJobResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkReplayIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobResult.setReplay(null);

        // Create the JobResult, which fails.

        restJobResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkReplayThemeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobResult.setReplayTheme(null);

        // Create the JobResult, which fails.

        restJobResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkChannelIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jobResult.setChannel(null);

        // Create the JobResult, which fails.

        restJobResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllJobResults() throws Exception {
        // Initialize the database
        insertedJobResult = jobResultRepository.save(jobResult);

        // Get all the jobResultList
        restJobResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobResult.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].jobUrl").value(hasItem(DEFAULT_JOB_URL)))
            .andExpect(jsonPath("$.[*].authorName").value(hasItem(DEFAULT_AUTHOR_NAME)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].jobDate").value(hasItem(DEFAULT_JOB_DATE.toString())))
            .andExpect(jsonPath("$.[*].jobNo").value(hasItem(DEFAULT_JOB_NO)))
            .andExpect(jsonPath("$.[*].builderDate").value(hasItem(DEFAULT_BUILDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].replay").value(hasItem(DEFAULT_REPLAY)))
            .andExpect(jsonPath("$.[*].replayTheme").value(hasItem(DEFAULT_REPLAY_THEME)))
            .andExpect(jsonPath("$.[*].replayImageContentType").value(hasItem(DEFAULT_REPLAY_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].replayImage").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_REPLAY_IMAGE))))
            .andExpect(jsonPath("$.[*].replayDate").value(hasItem(DEFAULT_REPLAY_DATE.toString())))
            .andExpect(jsonPath("$.[*].effReplay").value(hasItem(DEFAULT_EFF_REPLAY.booleanValue())))
            .andExpect(jsonPath("$.[*].settlement").value(hasItem(DEFAULT_SETTLEMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].settlementOrder").value(hasItem(DEFAULT_SETTLEMENT_ORDER.booleanValue())))
            .andExpect(jsonPath("$.[*].settlementDate").value(hasItem(DEFAULT_SETTLEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].errorMsg").value(hasItem(DEFAULT_ERROR_MSG)))
            .andExpect(jsonPath("$.[*].errorImageContentType").value(hasItem(DEFAULT_ERROR_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].errorImage").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_ERROR_IMAGE))))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL.toString())));
    }

    @Test
    void getJobResult() throws Exception {
        // Initialize the database
        insertedJobResult = jobResultRepository.save(jobResult);

        // Get the jobResult
        restJobResultMockMvc
            .perform(get(ENTITY_API_URL_ID, jobResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobResult.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.jobUrl").value(DEFAULT_JOB_URL))
            .andExpect(jsonPath("$.authorName").value(DEFAULT_AUTHOR_NAME))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.jobDate").value(DEFAULT_JOB_DATE.toString()))
            .andExpect(jsonPath("$.jobNo").value(DEFAULT_JOB_NO))
            .andExpect(jsonPath("$.builderDate").value(DEFAULT_BUILDER_DATE.toString()))
            .andExpect(jsonPath("$.replay").value(DEFAULT_REPLAY))
            .andExpect(jsonPath("$.replayTheme").value(DEFAULT_REPLAY_THEME))
            .andExpect(jsonPath("$.replayImageContentType").value(DEFAULT_REPLAY_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.replayImage").value(Base64.getEncoder().encodeToString(DEFAULT_REPLAY_IMAGE)))
            .andExpect(jsonPath("$.replayDate").value(DEFAULT_REPLAY_DATE.toString()))
            .andExpect(jsonPath("$.effReplay").value(DEFAULT_EFF_REPLAY.booleanValue()))
            .andExpect(jsonPath("$.settlement").value(DEFAULT_SETTLEMENT.booleanValue()))
            .andExpect(jsonPath("$.settlementOrder").value(DEFAULT_SETTLEMENT_ORDER.booleanValue()))
            .andExpect(jsonPath("$.settlementDate").value(DEFAULT_SETTLEMENT_DATE.toString()))
            .andExpect(jsonPath("$.errorMsg").value(DEFAULT_ERROR_MSG))
            .andExpect(jsonPath("$.errorImageContentType").value(DEFAULT_ERROR_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.errorImage").value(Base64.getEncoder().encodeToString(DEFAULT_ERROR_IMAGE)))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL.toString()));
    }

    @Test
    void getNonExistingJobResult() throws Exception {
        // Get the jobResult
        restJobResultMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingJobResult() throws Exception {
        // Initialize the database
        insertedJobResult = jobResultRepository.save(jobResult);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jobResult
        JobResult updatedJobResult = jobResultRepository.findById(jobResult.getId()).orElseThrow();
        updatedJobResult
            .name(UPDATED_NAME)
            .jobUrl(UPDATED_JOB_URL)
            .authorName(UPDATED_AUTHOR_NAME)
            .accountId(UPDATED_ACCOUNT_ID)
            .customerId(UPDATED_CUSTOMER_ID)
            .status(UPDATED_STATUS)
            .jobDate(UPDATED_JOB_DATE)
            .jobNo(UPDATED_JOB_NO)
            .builderDate(UPDATED_BUILDER_DATE)
            .replay(UPDATED_REPLAY)
            .replayTheme(UPDATED_REPLAY_THEME)
            .replayImage(UPDATED_REPLAY_IMAGE)
            .replayImageContentType(UPDATED_REPLAY_IMAGE_CONTENT_TYPE)
            .replayDate(UPDATED_REPLAY_DATE)
            .effReplay(UPDATED_EFF_REPLAY)
            .settlement(UPDATED_SETTLEMENT)
            .settlementOrder(UPDATED_SETTLEMENT_ORDER)
            .settlementDate(UPDATED_SETTLEMENT_DATE)
            .errorMsg(UPDATED_ERROR_MSG)
            .errorImage(UPDATED_ERROR_IMAGE)
            .errorImageContentType(UPDATED_ERROR_IMAGE_CONTENT_TYPE)
            .channel(UPDATED_CHANNEL);

        restJobResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobResult.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedJobResult))
            )
            .andExpect(status().isOk());

        // Validate the JobResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedJobResultToMatchAllProperties(updatedJobResult);
    }

    @Test
    void putNonExistingJobResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobResult.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobResult.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchJobResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobResult.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jobResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamJobResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobResult.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobResultMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobResult)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateJobResultWithPatch() throws Exception {
        // Initialize the database
        insertedJobResult = jobResultRepository.save(jobResult);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jobResult using partial update
        JobResult partialUpdatedJobResult = new JobResult();
        partialUpdatedJobResult.setId(jobResult.getId());

        partialUpdatedJobResult
            .name(UPDATED_NAME)
            .jobUrl(UPDATED_JOB_URL)
            .customerId(UPDATED_CUSTOMER_ID)
            .status(UPDATED_STATUS)
            .builderDate(UPDATED_BUILDER_DATE)
            .replay(UPDATED_REPLAY)
            .errorMsg(UPDATED_ERROR_MSG);

        restJobResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJobResult))
            )
            .andExpect(status().isOk());

        // Validate the JobResult in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJobResultUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedJobResult, jobResult),
            getPersistedJobResult(jobResult)
        );
    }

    @Test
    void fullUpdateJobResultWithPatch() throws Exception {
        // Initialize the database
        insertedJobResult = jobResultRepository.save(jobResult);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jobResult using partial update
        JobResult partialUpdatedJobResult = new JobResult();
        partialUpdatedJobResult.setId(jobResult.getId());

        partialUpdatedJobResult
            .name(UPDATED_NAME)
            .jobUrl(UPDATED_JOB_URL)
            .authorName(UPDATED_AUTHOR_NAME)
            .accountId(UPDATED_ACCOUNT_ID)
            .customerId(UPDATED_CUSTOMER_ID)
            .status(UPDATED_STATUS)
            .jobDate(UPDATED_JOB_DATE)
            .jobNo(UPDATED_JOB_NO)
            .builderDate(UPDATED_BUILDER_DATE)
            .replay(UPDATED_REPLAY)
            .replayTheme(UPDATED_REPLAY_THEME)
            .replayImage(UPDATED_REPLAY_IMAGE)
            .replayImageContentType(UPDATED_REPLAY_IMAGE_CONTENT_TYPE)
            .replayDate(UPDATED_REPLAY_DATE)
            .effReplay(UPDATED_EFF_REPLAY)
            .settlement(UPDATED_SETTLEMENT)
            .settlementOrder(UPDATED_SETTLEMENT_ORDER)
            .settlementDate(UPDATED_SETTLEMENT_DATE)
            .errorMsg(UPDATED_ERROR_MSG)
            .errorImage(UPDATED_ERROR_IMAGE)
            .errorImageContentType(UPDATED_ERROR_IMAGE_CONTENT_TYPE)
            .channel(UPDATED_CHANNEL);

        restJobResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJobResult))
            )
            .andExpect(status().isOk());

        // Validate the JobResult in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJobResultUpdatableFieldsEquals(partialUpdatedJobResult, getPersistedJobResult(partialUpdatedJobResult));
    }

    @Test
    void patchNonExistingJobResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobResult.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jobResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchJobResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobResult.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jobResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamJobResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobResult.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobResultMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(jobResult)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteJobResult() throws Exception {
        // Initialize the database
        insertedJobResult = jobResultRepository.save(jobResult);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the jobResult
        restJobResultMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobResult.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return jobResultRepository.count();
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

    protected JobResult getPersistedJobResult(JobResult jobResult) {
        return jobResultRepository.findById(jobResult.getId()).orElseThrow();
    }

    protected void assertPersistedJobResultToMatchAllProperties(JobResult expectedJobResult) {
        assertJobResultAllPropertiesEquals(expectedJobResult, getPersistedJobResult(expectedJobResult));
    }

    protected void assertPersistedJobResultToMatchUpdatableProperties(JobResult expectedJobResult) {
        assertJobResultAllUpdatablePropertiesEquals(expectedJobResult, getPersistedJobResult(expectedJobResult));
    }
}
