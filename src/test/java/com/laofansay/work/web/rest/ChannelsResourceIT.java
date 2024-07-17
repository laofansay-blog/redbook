package com.laofansay.work.web.rest;

import static com.laofansay.work.domain.ChannelsAsserts.*;
import static com.laofansay.work.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laofansay.work.IntegrationTest;
import com.laofansay.work.domain.Channels;
import com.laofansay.work.domain.enumeration.ChannelCate;
import com.laofansay.work.repository.ChannelsRepository;
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
 * Integration tests for the {@link ChannelsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChannelsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ChannelCate DEFAULT_CATEGORY = ChannelCate.SmallRedBook;
    private static final ChannelCate UPDATED_CATEGORY = ChannelCate.ByteDance;

    private static final Integer DEFAULT_RATE = 1;
    private static final Integer UPDATED_RATE = 2;

    private static final String DEFAULT_PROPS = "AAAAAAAAAA";
    private static final String UPDATED_PROPS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OPEN = false;
    private static final Boolean UPDATED_OPEN = true;

    private static final String ENTITY_API_URL = "/api/channels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ChannelsRepository channelsRepository;

    @Autowired
    private MockMvc restChannelsMockMvc;

    private Channels channels;

    private Channels insertedChannels;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Channels createEntity() {
        Channels channels = new Channels()
            .name(DEFAULT_NAME)
            .category(DEFAULT_CATEGORY)
            .rate(DEFAULT_RATE)
            .props(DEFAULT_PROPS)
            .open(DEFAULT_OPEN);
        return channels;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Channels createUpdatedEntity() {
        Channels channels = new Channels()
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY)
            .rate(UPDATED_RATE)
            .props(UPDATED_PROPS)
            .open(UPDATED_OPEN);
        return channels;
    }

    @BeforeEach
    public void initTest() {
        channels = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedChannels != null) {
            channelsRepository.delete(insertedChannels);
            insertedChannels = null;
        }
    }

    @Test
    void createChannels() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Channels
        var returnedChannels = om.readValue(
            restChannelsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(channels)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Channels.class
        );

        // Validate the Channels in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertChannelsUpdatableFieldsEquals(returnedChannels, getPersistedChannels(returnedChannels));

        insertedChannels = returnedChannels;
    }

    @Test
    void createChannelsWithExistingId() throws Exception {
        // Create the Channels with an existing ID
        channels.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChannelsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(channels)))
            .andExpect(status().isBadRequest());

        // Validate the Channels in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        channels.setName(null);

        // Create the Channels, which fails.

        restChannelsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(channels)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCategoryIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        channels.setCategory(null);

        // Create the Channels, which fails.

        restChannelsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(channels)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkRateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        channels.setRate(null);

        // Create the Channels, which fails.

        restChannelsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(channels)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkOpenIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        channels.setOpen(null);

        // Create the Channels, which fails.

        restChannelsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(channels)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllChannels() throws Exception {
        // Initialize the database
        insertedChannels = channelsRepository.save(channels);

        // Get all the channelsList
        restChannelsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(channels.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE)))
            .andExpect(jsonPath("$.[*].props").value(hasItem(DEFAULT_PROPS)))
            .andExpect(jsonPath("$.[*].open").value(hasItem(DEFAULT_OPEN.booleanValue())));
    }

    @Test
    void getChannels() throws Exception {
        // Initialize the database
        insertedChannels = channelsRepository.save(channels);

        // Get the channels
        restChannelsMockMvc
            .perform(get(ENTITY_API_URL_ID, channels.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(channels.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE))
            .andExpect(jsonPath("$.props").value(DEFAULT_PROPS))
            .andExpect(jsonPath("$.open").value(DEFAULT_OPEN.booleanValue()));
    }

    @Test
    void getNonExistingChannels() throws Exception {
        // Get the channels
        restChannelsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingChannels() throws Exception {
        // Initialize the database
        insertedChannels = channelsRepository.save(channels);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the channels
        Channels updatedChannels = channelsRepository.findById(channels.getId()).orElseThrow();
        updatedChannels.name(UPDATED_NAME).category(UPDATED_CATEGORY).rate(UPDATED_RATE).props(UPDATED_PROPS).open(UPDATED_OPEN);

        restChannelsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChannels.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedChannels))
            )
            .andExpect(status().isOk());

        // Validate the Channels in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedChannelsToMatchAllProperties(updatedChannels);
    }

    @Test
    void putNonExistingChannels() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        channels.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChannelsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, channels.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(channels))
            )
            .andExpect(status().isBadRequest());

        // Validate the Channels in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchChannels() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        channels.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChannelsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(channels))
            )
            .andExpect(status().isBadRequest());

        // Validate the Channels in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamChannels() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        channels.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChannelsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(channels)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Channels in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateChannelsWithPatch() throws Exception {
        // Initialize the database
        insertedChannels = channelsRepository.save(channels);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the channels using partial update
        Channels partialUpdatedChannels = new Channels();
        partialUpdatedChannels.setId(channels.getId());

        partialUpdatedChannels.category(UPDATED_CATEGORY).open(UPDATED_OPEN);

        restChannelsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChannels.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedChannels))
            )
            .andExpect(status().isOk());

        // Validate the Channels in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChannelsUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedChannels, channels), getPersistedChannels(channels));
    }

    @Test
    void fullUpdateChannelsWithPatch() throws Exception {
        // Initialize the database
        insertedChannels = channelsRepository.save(channels);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the channels using partial update
        Channels partialUpdatedChannels = new Channels();
        partialUpdatedChannels.setId(channels.getId());

        partialUpdatedChannels.name(UPDATED_NAME).category(UPDATED_CATEGORY).rate(UPDATED_RATE).props(UPDATED_PROPS).open(UPDATED_OPEN);

        restChannelsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChannels.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedChannels))
            )
            .andExpect(status().isOk());

        // Validate the Channels in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChannelsUpdatableFieldsEquals(partialUpdatedChannels, getPersistedChannels(partialUpdatedChannels));
    }

    @Test
    void patchNonExistingChannels() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        channels.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChannelsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, channels.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(channels))
            )
            .andExpect(status().isBadRequest());

        // Validate the Channels in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchChannels() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        channels.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChannelsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(channels))
            )
            .andExpect(status().isBadRequest());

        // Validate the Channels in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamChannels() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        channels.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChannelsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(channels)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Channels in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteChannels() throws Exception {
        // Initialize the database
        insertedChannels = channelsRepository.save(channels);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the channels
        restChannelsMockMvc
            .perform(delete(ENTITY_API_URL_ID, channels.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return channelsRepository.count();
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

    protected Channels getPersistedChannels(Channels channels) {
        return channelsRepository.findById(channels.getId()).orElseThrow();
    }

    protected void assertPersistedChannelsToMatchAllProperties(Channels expectedChannels) {
        assertChannelsAllPropertiesEquals(expectedChannels, getPersistedChannels(expectedChannels));
    }

    protected void assertPersistedChannelsToMatchUpdatableProperties(Channels expectedChannels) {
        assertChannelsAllUpdatablePropertiesEquals(expectedChannels, getPersistedChannels(expectedChannels));
    }
}
