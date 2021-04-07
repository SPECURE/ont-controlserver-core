package com.specure.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.specure.core.enums.Direction;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SpeedDetail {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seqGenSpeedDetail")
    @SequenceGenerator(name = "seqGenSpeedDetail", sequenceName = "seqSpeedDetail", initialValue = 500000000)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "measurement_id")
    @JsonBackReference
    private Measurement measurement;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    private Integer thread;
    private Long time;
    private Integer bytes;
}
