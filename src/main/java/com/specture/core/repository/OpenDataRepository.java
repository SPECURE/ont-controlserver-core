package com.specture.core.repository;

import com.specture.core.enums.MeasurementStatus;
import com.specture.core.model.OpenData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OpenDataRepository extends JpaRepository<OpenData, Long> {

    List<OpenData> findAllByTimeBetweenAndStatus(Timestamp timeStart, Timestamp timeEnd, MeasurementStatus status);

    List<OpenData> findAllByStatus(MeasurementStatus status);
}
