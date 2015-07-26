package com.jobvacancy.repository;

import com.jobvacancy.domain.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the JobOffer entity.
 */
public interface JobOfferRepository extends JpaRepository<JobOffer,Long>{

}
