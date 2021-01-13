package com.specure.core.repository;

import com.specure.core.enums.MeasurementStatus;
import com.specure.core.model.OpenData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OpenDataRepository extends JpaRepository<OpenData, Long> {

    List<OpenData> findAllByTimeBetweenAndStatus(Timestamp timeStart, Timestamp timeEnd, MeasurementStatus status);

    List<OpenData> findAllByStatus(MeasurementStatus status);
}
