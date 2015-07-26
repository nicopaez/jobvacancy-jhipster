package com.jobvacancy.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jobvacancy.domain.JobOffer;
import com.jobvacancy.repository.JobOfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing JobOffer.
 */
@RestController
@RequestMapping("/api")
public class JobOfferResource {

    private final Logger log = LoggerFactory.getLogger(JobOfferResource.class);

    @Inject
    private JobOfferRepository jobOfferRepository;

    /**
     * POST  /jobOffers -> Create a new jobOffer.
     */
    @RequestMapping(value = "/jobOffers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody JobOffer jobOffer) {
        log.debug("REST request to save JobOffer : {}", jobOffer);
        jobOfferRepository.save(jobOffer);
    }

    /**
     * GET  /jobOffers -> get all the jobOffers.
     */
    @RequestMapping(value = "/jobOffers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JobOffer> getAll() {
        log.debug("REST request to get all JobOffers");
        return jobOfferRepository.findAll();
    }

    /**
     * GET  /jobOffers/:id -> get the "id" jobOffer.
     */
    @RequestMapping(value = "/jobOffers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobOffer> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get JobOffer : {}", id);
        JobOffer jobOffer = jobOfferRepository.findOne(id);
        if (jobOffer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(jobOffer, HttpStatus.OK);
    }

    /**
     * DELETE  /jobOffers/:id -> delete the "id" jobOffer.
     */
    @RequestMapping(value = "/jobOffers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete JobOffer : {}", id);
        jobOfferRepository.delete(id);
    }
}
