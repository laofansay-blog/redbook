package com.laofansay.work.web.rest;

import static com.laofansay.work.domain.CstJobAsserts.*;
import static com.laofansay.work.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laofansay.work.IntegrationTest;
import com.laofansay.work.domain.CstJob;
import com.laofansay.work.domain.enumeration.ChannelCate;
import com.laofansay.work.domain.enumeration.ExecuteType;
import com.laofansay.work.domain.enumeration.JobStatus;
import com.laofansay.work.repository.CstJobRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link CstJobResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CstJobResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ExecuteType DEFAULT_EXECUTE_TYPE = ExecuteType.ONCE;
    private static final ExecuteType UPDATED_EXECUTE_TYPE = ExecuteType.DAY;

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final JobStatus DEFAULT_STATUS = JobStatus.OVER;
    private static final JobStatus UPDATED_STATUS = JobStatus.READY;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_JOB_PROPS = "AAAAAAAAAA";
    private static final String UPDATED_JOB_PROPS = "BBBBBBBBBB";

    private static final ChannelCate DEFAULT_CHANNEL = ChannelCate.SmallRedBook;
    private static final ChannelCate UPDATED_CHANNEL = ChannelCate.ByteDance;

    private static final String ENTITY_API_URL = "/api/cst-jobs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CstJobRepository cstJobRepository;

    @Autowired
    private MockMvc restCstJobMockMvc;

    private CstJob cstJob;

    private CstJob insertedCstJob;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CstJob createEntity() {
        CstJob cstJob = new CstJob()
            .name(DEFAULT_NAME)
            .executeType(DEFAULT_EXECUTE_TYPE)
            .category(DEFAULT_CATEGORY)
            .status(DEFAULT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .jobProps(DEFAULT_JOB_PROPS)
            .channel(DEFAULT_CHANNEL);
        return cstJob;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CstJob createUpdatedEntity() {
        CstJob cstJob = new CstJob()
            .name(UPDATED_NAME)
            .executeType(UPDATED_EXECUTE_TYPE)
            .category(UPDATED_CATEGORY)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .jobProps(UPDATED_JOB_PROPS)
            .channel(UPDATED_CHANNEL);
        return cstJob;
    }

    @BeforeEach
    public void initTest() {
        cstJob = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCstJob != null) {
            cstJobRepository.delete(insertedCstJob);
            insertedCstJob = null;
        }
    }

    @Test
    void createCstJob() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CstJob
        var returnedCstJob = om.readValue(
            restCstJobMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstJob)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CstJob.class
        );

        // Validate the CstJob in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCstJobUpdatableFieldsEquals(returnedCstJob, getPersistedCstJob(returnedCstJob));

        insertedCstJob = returnedCstJob;
    }

    @Test
    void createCstJobWithExistingId() throws Exception {
        // Create the CstJob with an existing ID
        cstJob.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCstJobMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstJob)))
            .andExpect(status().isBadRequest());

        // Validate the CstJob in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cstJob.setName(null);

        // Create the CstJob, which fails.

        restCstJobMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstJob)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkExecuteTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cstJob.setExecuteType(null);

        // Create the CstJob, which fails.

        restCstJobMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstJob)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cstJob.setStatus(null);

        // Create the CstJob, which fails.

        restCstJobMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstJob)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cstJob.setCreatedDate(null);

        // Create the CstJob, which fails.

        restCstJobMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstJob)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkJobPropsIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cstJob.setJobProps(null);

        // Create the CstJob, which fails.

        restCstJobMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstJob)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkChannelIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cstJob.setChannel(null);

        // Create the CstJob, which fails.

        restCstJobMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstJob)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCstJobs() throws Exception {
        // Initialize the database
        insertedCstJob = cstJobRepository.save(cstJob);

        // Get all the cstJobList
        restCstJobMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cstJob.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].executeType").value(hasItem(DEFAULT_EXECUTE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].jobProps").value(hasItem(DEFAULT_JOB_PROPS)))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL.toString())));
    }

    @Test
    void getCstJob() throws Exception {
        // Initialize the database
        insertedCstJob = cstJobRepository.save(cstJob);

        // Get the cstJob
        restCstJobMockMvc
            .perform(get(ENTITY_API_URL_ID, cstJob.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cstJob.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.executeType").value(DEFAULT_EXECUTE_TYPE.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.jobProps").value(DEFAULT_JOB_PROPS))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL.toString()));
    }

    @Test
    void getNonExistingCstJob() throws Exception {
        // Get the cstJob
        restCstJobMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCstJob() throws Exception {
        // Initialize the database
        insertedCstJob = cstJobRepository.save(cstJob);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cstJob
        CstJob updatedCstJob = cstJobRepository.findById(cstJob.getId()).orElseThrow();
        updatedCstJob
            .name(UPDATED_NAME)
            .executeType(UPDATED_EXECUTE_TYPE)
            .category(UPDATED_CATEGORY)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .jobProps(UPDATED_JOB_PROPS)
            .channel(UPDATED_CHANNEL);

        restCstJobMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCstJob.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCstJob))
            )
            .andExpect(status().isOk());

        // Validate the CstJob in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCstJobToMatchAllProperties(updatedCstJob);
    }

    @Test
    void putNonExistingCstJob() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cstJob.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCstJobMockMvc
            .perform(put(ENTITY_API_URL_ID, cstJob.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstJob)))
            .andExpect(status().isBadRequest());

        // Validate the CstJob in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCstJob() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cstJob.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCstJobMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cstJob))
            )
            .andExpect(status().isBadRequest());

        // Validate the CstJob in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCstJob() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cstJob.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCstJobMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstJob)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CstJob in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCstJobWithPatch() throws Exception {
        // Initialize the database
        insertedCstJob = cstJobRepository.save(cstJob);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cstJob using partial update
        CstJob partialUpdatedCstJob = new CstJob();
        partialUpdatedCstJob.setId(cstJob.getId());

        partialUpdatedCstJob.name(UPDATED_NAME).category(UPDATED_CATEGORY).jobProps(UPDATED_JOB_PROPS);

        restCstJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCstJob.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCstJob))
            )
            .andExpect(status().isOk());

        // Validate the CstJob in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCstJobUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCstJob, cstJob), getPersistedCstJob(cstJob));
    }

    @Test
    void fullUpdateCstJobWithPatch() throws Exception {
        // Initialize the database
        insertedCstJob = cstJobRepository.save(cstJob);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cstJob using partial update
        CstJob partialUpdatedCstJob = new CstJob();
        partialUpdatedCstJob.setId(cstJob.getId());

        partialUpdatedCstJob
            .name(UPDATED_NAME)
            .executeType(UPDATED_EXECUTE_TYPE)
            .category(UPDATED_CATEGORY)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .jobProps(UPDATED_JOB_PROPS)
            .channel(UPDATED_CHANNEL);

        restCstJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCstJob.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCstJob))
            )
            .andExpect(status().isOk());

        // Validate the CstJob in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCstJobUpdatableFieldsEquals(partialUpdatedCstJob, getPersistedCstJob(partialUpdatedCstJob));
    }

    @Test
    void patchNonExistingCstJob() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cstJob.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCstJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cstJob.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cstJob))
            )
            .andExpect(status().isBadRequest());

        // Validate the CstJob in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCstJob() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cstJob.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCstJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cstJob))
            )
            .andExpect(status().isBadRequest());

        // Validate the CstJob in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCstJob() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cstJob.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCstJobMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cstJob)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CstJob in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCstJob() throws Exception {
        // Initialize the database
        insertedCstJob = cstJobRepository.save(cstJob);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cstJob
        restCstJobMockMvc
            .perform(delete(ENTITY_API_URL_ID, cstJob.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cstJobRepository.count();
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

    protected CstJob getPersistedCstJob(CstJob cstJob) {
        return cstJobRepository.findById(cstJob.getId()).orElseThrow();
    }

    protected void assertPersistedCstJobToMatchAllProperties(CstJob expectedCstJob) {
        assertCstJobAllPropertiesEquals(expectedCstJob, getPersistedCstJob(expectedCstJob));
    }

    protected void assertPersistedCstJobToMatchUpdatableProperties(CstJob expectedCstJob) {
        assertCstJobAllUpdatablePropertiesEquals(expectedCstJob, getPersistedCstJob(expectedCstJob));
    }
}
