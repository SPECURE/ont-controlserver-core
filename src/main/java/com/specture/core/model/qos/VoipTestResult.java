package com.specture.core.model.qos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VoipTestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int voipResultOutShortSeq;
    private int voipResultInSequenceError;
    private int voipObjectiveSampleRate;
    private int voipObjectiveBitsPerSample;
    private int voipResultOutLongSeq;
    private long voipResultOutMaxJitter;
    private long durationNs;
    private String voipResultStatus;
    private int voipResultInNumPackets;
    private int voipResultOutSequenceError;
    private String voipResultJitter;
    private long voipResultInMaxJitter;
    private long voipResultInSkew;
    private long voipResultOutMaxDelta;
    private int voipObjectiveInPort;
    private long qosTestUid;
    private int voipResultInShortSeq;
    private long startTimeNs;
    private int voipObjectivePayload;
    private long voipResultInMaxDelta;
    private int voipObjectiveOutPort;
    private long voipResultOutSkew;
    private int voipResultInLongSeq;
    private String voipResultPacketLoss;

    private long voipResultOutMeanJitter;
    private long voipResultInMeanJitter;
    private long voipObjectiveDelay;
    private long voipObjectiveCallDuration;
    private long voipResultOutNumPackets;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "measurement_qos_id")
    private MeasurementQos measurementQos;
}
