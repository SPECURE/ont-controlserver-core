package com.specure.core.repository;

import com.specure.core.model.MeasurementServerDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementServerDescriptionRepository extends JpaRepository<MeasurementServerDescription, Long> { }
