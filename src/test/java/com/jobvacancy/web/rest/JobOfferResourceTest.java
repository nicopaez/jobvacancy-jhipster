package com.jobvacancy.web.rest;

import com.jobvacancy.Application;
import com.jobvacancy.domain.JobOffer;
import com.jobvacancy.repository.JobOfferRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JobOfferResource REST controller.
 *
 * @see JobOfferResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JobOfferResourceTest {

    private static final String DEFAULT_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_TITLE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_LOCATION = "SAMPLE_TEXT";
    private static final String UPDATED_LOCATION = "UPDATED_TEXT";

    @Inject
    private JobOfferRepository jobOfferRepository;

    private MockMvc restJobOfferMockMvc;

    private JobOffer jobOffer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobOfferResource jobOfferResource = new JobOfferResource();
        ReflectionTestUtils.setField(jobOfferResource, "jobOfferRepository", jobOfferRepository);
        this.restJobOfferMockMvc = MockMvcBuilders.standaloneSetup(jobOfferResource).build();
    }

    @Before
    public void initTest() {
        jobOffer = new JobOffer();
        jobOffer.setTitle(DEFAULT_TITLE);
        jobOffer.setDescription(DEFAULT_DESCRIPTION);
        jobOffer.setLocation(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    public void createJobOffer() throws Exception {
        // Validate the database is empty
        assertThat(jobOfferRepository.findAll()).hasSize(0);

        // Create the JobOffer
        restJobOfferMockMvc.perform(post("/api/jobOffers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
                .andExpect(status().isOk());

        // Validate the JobOffer in the database
        List<JobOffer> jobOffers = jobOfferRepository.findAll();
        assertThat(jobOffers).hasSize(1);
        JobOffer testJobOffer = jobOffers.iterator().next();
        assertThat(testJobOffer.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJobOffer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJobOffer.getLocation()).isEqualTo(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    public void getAllJobOffers() throws Exception {
        // Initialize the database
        jobOfferRepository.saveAndFlush(jobOffer);

        // Get all the jobOffers
        restJobOfferMockMvc.perform(get("/api/jobOffers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(jobOffer.getId().intValue()))
                .andExpect(jsonPath("$.[0].title").value(DEFAULT_TITLE.toString()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.[0].location").value(DEFAULT_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getJobOffer() throws Exception {
        // Initialize the database
        jobOfferRepository.saveAndFlush(jobOffer);

        // Get the jobOffer
        restJobOfferMockMvc.perform(get("/api/jobOffers/{id}", jobOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jobOffer.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobOffer() throws Exception {
        // Get the jobOffer
        restJobOfferMockMvc.perform(get("/api/jobOffers/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobOffer() throws Exception {
        // Initialize the database
        jobOfferRepository.saveAndFlush(jobOffer);

        // Update the jobOffer
        jobOffer.setTitle(UPDATED_TITLE);
        jobOffer.setDescription(UPDATED_DESCRIPTION);
        jobOffer.setLocation(UPDATED_LOCATION);
        restJobOfferMockMvc.perform(post("/api/jobOffers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
                .andExpect(status().isOk());

        // Validate the JobOffer in the database
        List<JobOffer> jobOffers = jobOfferRepository.findAll();
        assertThat(jobOffers).hasSize(1);
        JobOffer testJobOffer = jobOffers.iterator().next();
        assertThat(testJobOffer.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJobOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJobOffer.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void deleteJobOffer() throws Exception {
        // Initialize the database
        jobOfferRepository.saveAndFlush(jobOffer);

        // Get the jobOffer
        restJobOfferMockMvc.perform(delete("/api/jobOffers/{id}", jobOffer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JobOffer> jobOffers = jobOfferRepository.findAll();
        assertThat(jobOffers).hasSize(0);
    }
}
