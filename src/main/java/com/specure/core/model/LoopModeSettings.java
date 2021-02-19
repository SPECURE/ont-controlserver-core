package com.specure.core.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoopModeSettings implements Serializable {

    @Id
    @Column(name = "uid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(name = "test_uuid")
    protected UUID testUuid;

    @Column(name = "client_uuid")
    protected UUID clientUuid;

    @Column(name = "max_delay")
    protected Integer maxDelay;

    @Column(name = "max_movement")
    protected Integer maxMovement;

    @Column(name = "max_tests")
    protected Integer maxTests;

    @Column(name = "test_counter")
    protected Integer testCounter;

    @Column(name = "loop_uuid")
    private UUID loopUuid;
}
