package com.specture.core.repository;

import com.specture.core.model.MeasurementServer;
import com.specture.core.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementServerRepository extends JpaRepository <MeasurementServer, Long> {
    Optional<MeasurementServer> findById(Long id);
    List<MeasurementServer> findByProvider(Provider provider);
    List<MeasurementServer> findByProviderNot(Provider provider);

    @Query(
            value = "SELECT * FROM measurement_server ms " +
                    "WHERE ms.id = (SELECT m.measurement_server_id FROM measurement m WHERE m.client_uuid = ?1 ORDER BY m.id DESC LIMIT 1)",
            nativeQuery = true
    )
    Optional<MeasurementServer> findByClientUUID(String client_uuid);

}
