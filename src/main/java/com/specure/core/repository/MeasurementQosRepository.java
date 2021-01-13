package com.specure.core.repository;

import com.specure.core.model.qos.MeasurementQos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementQosRepository extends JpaRepository<MeasurementQos, Long> {

}
