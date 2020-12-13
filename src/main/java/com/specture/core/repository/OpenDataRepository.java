package com.specture.core.repository;

import com.specture.core.enums.MeasurementStatus;
import com.specture.core.model.OpenData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface OpenDataRepository extends JpaRepository<OpenData, Long> {

    List<OpenData> findAllByTimeBetweenAndStatus(Timestamp timeStart, Timestamp timeEnd, MeasurementStatus status);

    List<OpenData> findAllByStatus(MeasurementStatus status);
}
