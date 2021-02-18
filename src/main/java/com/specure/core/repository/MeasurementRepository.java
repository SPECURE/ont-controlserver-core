package com.specure.core.repository;

import com.specure.core.enums.Platform;
import com.specure.core.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    Optional<Measurement> findByToken(String token);

    Optional<Measurement> findByOpenTestUuid(String uuid);

    long countAllByTestSlot(int slot);

    @Query(
        value="SELECT mesur.*\n" +
            "FROM measurement mesur\n" +
            "INNER JOIN (SELECT measurement_server_id, MAX(time) AS MaxDateTime\n" +
            "    FROM measurement\n" +
            "    WHERE  measurement_server_id in ?1\n" +
            "    GROUP BY measurement_server_id\n" +
            "    ) groupedMesuremnts\n" +
            "ON mesur.measurement_server_id = groupedMesuremnts.measurement_server_id\n" +
            "AND mesur.time = groupedMesuremnts.MaxDateTime",
        nativeQuery = true
    )
    List<Measurement> findLastMeasurementsForMeasurementServerIds(List<Long> ids);

    long countAllByTimeAfterAndTimeBeforeAndPlatform(Timestamp start, Timestamp finish, Platform platform);

    @Query(
        value="SELECT mesur.*\n" +
            "FROM measurement mesur\n" +
            "INNER JOIN (SELECT measurement_server_id, MAX(time) AS MaxDateTime\n" +
            "    FROM measurement\n" +
            "    WHERE  measurement_server_id in ?1 AND status = 'FINISHED'\n" +
            "    GROUP BY measurement_server_id\n" +
            "    ) groupedMesuremnts\n" +
            "ON mesur.measurement_server_id = groupedMesuremnts.measurement_server_id\n" +
            "AND mesur.time = groupedMesuremnts.MaxDateTime",
        nativeQuery = true
    )
    List<Measurement> findTopByOrderByStatusAndMeasurementServerIdOrderByTime(List<Long> ids);
    void deleteByOpenTestUuid(String uuid);
}
