package com.specture.core.request.measurement.qos.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("voip")
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
public class VoipTestResultRequest extends TestResult {

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
    private int qosTestUid;
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
}
