package com.specture.core.repository;

import com.specture.core.model.qos.MeasurementQos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementQosRepository extends JpaRepository<MeasurementQos, Long> {

}
