package com.specure.core.model.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.specure.core.enums.AdHocCampaignStatus;
import com.specure.core.enums.JobType;
import com.specure.core.constant.ErrorMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@Slf4j
public class KafkaAdHocCampaignMessage {

    @NotNull(message = ErrorMessage.JOB_TYPE_IS_NOT_DEFINED)
    private JobType jobType;

    private String campaign;
    private String probe;
    private String tenant;
    private String provider;
    private AdHocCampaignStatus status;

    private Timestamp start;
    private Timestamp finish;


    @JsonCreator
    public KafkaAdHocCampaignMessage(
            @JsonProperty("jobType") JobType jobType,
            @JsonProperty("campaign") String campaign,
            @JsonProperty("probe") String probe,
            @JsonProperty("tenant") String tenant,
            @JsonProperty("provider") String provider,
            @JsonProperty("status") String status,
            @JsonProperty("start") String start,
            @JsonProperty("finish") String finish
    ) {
        this.jobType = jobType;
        this.campaign = campaign;
        this.probe = probe;
        this.tenant = tenant;
        this.provider = provider;
        this.status = AdHocCampaignStatus.valueOf(status);
        this.start = new Timestamp(Long.parseLong(start));
        this.finish = new Timestamp(Long.parseLong(finish));
    }

    @Override
    public String toString() {
        return "KafkaAdHocCampaignMessage{" +
                "jobType=" + jobType +
                ", campaign='" + campaign + '\'' +
                ", probe='" + probe + '\'' +
                ", tenant='" + tenant + '\'' +
                ", provider='" + provider + '\'' +
                ", start=" + start +
                ", finish=" + finish +
                '}';
    }
}
