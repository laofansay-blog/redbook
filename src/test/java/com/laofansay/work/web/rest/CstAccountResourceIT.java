package com.laofansay.work.web.rest;

import static com.laofansay.work.domain.CstAccountAsserts.*;
import static com.laofansay.work.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laofansay.work.IntegrationTest;
import com.laofansay.work.domain.CstAccount;
import com.laofansay.work.domain.enumeration.CstStatus;
import com.laofansay.work.repository.CstAccountRepository;
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
 * Integration tests for the {@link CstAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CstAccountResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_PROVIDER = "BBBBBBBBBB";

    private static final String DEFAULT_RB_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_RB_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_RB_PWD = "AAAAAAAAAA";
    private static final String UPDATED_RB_PWD = "BBBBBBBBBB";

    private static final String DEFAULT_RB_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_RB_TOKEN = "BBBBBBBBBB";

    private static final CstStatus DEFAULT_STATUS = CstStatus.ENABLED;
    private static final CstStatus UPDATED_STATUS = CstStatus.DISABLED;

    private static final Integer DEFAULT_TIMES_BY_DAY = 1;
    private static final Integer UPDATED_TIMES_BY_DAY = 2;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cst-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CstAccountRepository cstAccountRepository;

    @Autowired
    private MockMvc restCstAccountMockMvc;

    private CstAccount cstAccount;

    private CstAccount insertedCstAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CstAccount createEntity() {
        CstAccount cstAccount = new CstAccount()
            .name(DEFAULT_NAME)
            .provider(DEFAULT_PROVIDER)
            .rbAccount(DEFAULT_RB_ACCOUNT)
            .rbPwd(DEFAULT_RB_PWD)
            .rbToken(DEFAULT_RB_TOKEN)
            .status(DEFAULT_STATUS)
            .timesByDay(DEFAULT_TIMES_BY_DAY)
            .createdDate(DEFAULT_CREATED_DATE)
            .channel(DEFAULT_CHANNEL);
        return cstAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CstAccount createUpdatedEntity() {
        CstAccount cstAccount = new CstAccount()
            .name(UPDATED_NAME)
            .provider(UPDATED_PROVIDER)
            .rbAccount(UPDATED_RB_ACCOUNT)
            .rbPwd(UPDATED_RB_PWD)
            .rbToken(UPDATED_RB_TOKEN)
            .status(UPDATED_STATUS)
            .timesByDay(UPDATED_TIMES_BY_DAY)
            .createdDate(UPDATED_CREATED_DATE)
            .channel(UPDATED_CHANNEL);
        return cstAccount;
    }

    @BeforeEach
    public void initTest() {
        cstAccount = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCstAccount != null) {
            cstAccountRepository.delete(insertedCstAccount);
            insertedCstAccount = null;
        }
    }

    @Test
    void createCstAccount() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CstAccount
        var returnedCstAccount = om.readValue(
            restCstAccountMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstAccount)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CstAccount.class
        );

        // Validate the CstAccount in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCstAccountUpdatableFieldsEquals(returnedCstAccount, getPersistedCstAccount(returnedCstAccount));

        insertedCstAccount = returnedCstAccount;
    }

    @Test
    void createCstAccountWithExistingId() throws Exception {
        // Create the CstAccount with an existing ID
        cstAccount.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCstAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstAccount)))
            .andExpect(status().isBadRequest());

        // Validate the CstAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cstAccount.setName(null);

        // Create the CstAccount, which fails.

        restCstAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstAccount)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkProviderIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cstAccount.setProvider(null);

        // Create the CstAccount, which fails.

        restCstAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstAccount)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkRbAccountIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cstAccount.setRbAccount(null);

        // Create the CstAccount, which fails.

        restCstAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstAccount)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkRbPwdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cstAccount.setRbPwd(null);

        // Create the CstAccount, which fails.

        restCstAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstAccount)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cstAccount.setStatus(null);

        // Create the CstAccount, which fails.

        restCstAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstAccount)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTimesByDayIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cstAccount.setTimesByDay(null);

        // Create the CstAccount, which fails.

        restCstAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstAccount)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkChannelIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cstAccount.setChannel(null);

        // Create the CstAccount, which fails.

        restCstAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstAccount)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCstAccounts() throws Exception {
        // Initialize the database
        insertedCstAccount = cstAccountRepository.save(cstAccount);

        // Get all the cstAccountList
        restCstAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cstAccount.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].provider").value(hasItem(DEFAULT_PROVIDER)))
            .andExpect(jsonPath("$.[*].rbAccount").value(hasItem(DEFAULT_RB_ACCOUNT)))
            .andExpect(jsonPath("$.[*].rbPwd").value(hasItem(DEFAULT_RB_PWD)))
            .andExpect(jsonPath("$.[*].rbToken").value(hasItem(DEFAULT_RB_TOKEN)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].timesByDay").value(hasItem(DEFAULT_TIMES_BY_DAY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)));
    }

    @Test
    void getCstAccount() throws Exception {
        // Initialize the database
        insertedCstAccount = cstAccountRepository.save(cstAccount);

        // Get the cstAccount
        restCstAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, cstAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cstAccount.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.provider").value(DEFAULT_PROVIDER))
            .andExpect(jsonPath("$.rbAccount").value(DEFAULT_RB_ACCOUNT))
            .andExpect(jsonPath("$.rbPwd").value(DEFAULT_RB_PWD))
            .andExpect(jsonPath("$.rbToken").value(DEFAULT_RB_TOKEN))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.timesByDay").value(DEFAULT_TIMES_BY_DAY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL));
    }

    @Test
    void getNonExistingCstAccount() throws Exception {
        // Get the cstAccount
        restCstAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCstAccount() throws Exception {
        // Initialize the database
        insertedCstAccount = cstAccountRepository.save(cstAccount);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cstAccount
        CstAccount updatedCstAccount = cstAccountRepository.findById(cstAccount.getId()).orElseThrow();
        updatedCstAccount
            .name(UPDATED_NAME)
            .provider(UPDATED_PROVIDER)
            .rbAccount(UPDATED_RB_ACCOUNT)
            .rbPwd(UPDATED_RB_PWD)
            .rbToken(UPDATED_RB_TOKEN)
            .status(UPDATED_STATUS)
            .timesByDay(UPDATED_TIMES_BY_DAY)
            .createdDate(UPDATED_CREATED_DATE)
            .channel(UPDATED_CHANNEL);

        restCstAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCstAccount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCstAccount))
            )
            .andExpect(status().isOk());

        // Validate the CstAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCstAccountToMatchAllProperties(updatedCstAccount);
    }

    @Test
    void putNonExistingCstAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cstAccount.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCstAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cstAccount.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the CstAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCstAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cstAccount.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCstAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cstAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the CstAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCstAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cstAccount.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCstAccountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cstAccount)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CstAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCstAccountWithPatch() throws Exception {
        // Initialize the database
        insertedCstAccount = cstAccountRepository.save(cstAccount);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cstAccount using partial update
        CstAccount partialUpdatedCstAccount = new CstAccount();
        partialUpdatedCstAccount.setId(cstAccount.getId());

        partialUpdatedCstAccount
            .provider(UPDATED_PROVIDER)
            .rbPwd(UPDATED_RB_PWD)
            .rbToken(UPDATED_RB_TOKEN)
            .status(UPDATED_STATUS)
            .channel(UPDATED_CHANNEL);

        restCstAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCstAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCstAccount))
            )
            .andExpect(status().isOk());

        // Validate the CstAccount in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCstAccountUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCstAccount, cstAccount),
            getPersistedCstAccount(cstAccount)
        );
    }

    @Test
    void fullUpdateCstAccountWithPatch() throws Exception {
        // Initialize the database
        insertedCstAccount = cstAccountRepository.save(cstAccount);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cstAccount using partial update
        CstAccount partialUpdatedCstAccount = new CstAccount();
        partialUpdatedCstAccount.setId(cstAccount.getId());

        partialUpdatedCstAccount
            .name(UPDATED_NAME)
            .provider(UPDATED_PROVIDER)
            .rbAccount(UPDATED_RB_ACCOUNT)
            .rbPwd(UPDATED_RB_PWD)
            .rbToken(UPDATED_RB_TOKEN)
            .status(UPDATED_STATUS)
            .timesByDay(UPDATED_TIMES_BY_DAY)
            .createdDate(UPDATED_CREATED_DATE)
            .channel(UPDATED_CHANNEL);

        restCstAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCstAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCstAccount))
            )
            .andExpect(status().isOk());

        // Validate the CstAccount in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCstAccountUpdatableFieldsEquals(partialUpdatedCstAccount, getPersistedCstAccount(partialUpdatedCstAccount));
    }

    @Test
    void patchNonExistingCstAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cstAccount.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCstAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cstAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cstAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the CstAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCstAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cstAccount.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCstAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cstAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the CstAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCstAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cstAccount.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCstAccountMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cstAccount)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CstAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCstAccount() throws Exception {
        // Initialize the database
        insertedCstAccount = cstAccountRepository.save(cstAccount);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cstAccount
        restCstAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, cstAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cstAccountRepository.count();
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

    protected CstAccount getPersistedCstAccount(CstAccount cstAccount) {
        return cstAccountRepository.findById(cstAccount.getId()).orElseThrow();
    }

    protected void assertPersistedCstAccountToMatchAllProperties(CstAccount expectedCstAccount) {
        assertCstAccountAllPropertiesEquals(expectedCstAccount, getPersistedCstAccount(expectedCstAccount));
    }

    protected void assertPersistedCstAccountToMatchUpdatableProperties(CstAccount expectedCstAccount) {
        assertCstAccountAllUpdatablePropertiesEquals(expectedCstAccount, getPersistedCstAccount(expectedCstAccount));
    }
}
