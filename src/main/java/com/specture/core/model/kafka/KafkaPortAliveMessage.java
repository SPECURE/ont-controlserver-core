package com.specture.core.model.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.specture.core.enums.JobType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

import static com.specture.core.constant.ErrorMessage.JOB_TYPE_IS_NOT_DEFINED;

@Getter
@Setter
@Slf4j
public class KafkaPortAliveMessage {

    @NotNull(message = JOB_TYPE_IS_NOT_DEFINED)
    private JobType jobType;

    private String message;
    private String probe;
    private String port;
    private String tenant;

    private String site;
    private String provider;
    private String packageName;

    @JsonCreator
    public KafkaPortAliveMessage(
            @JsonProperty("message") String message,
            @JsonProperty("probe") String probe,
            @JsonProperty("port") String port,
            @JsonProperty("jobType") JobType jobType,
            @JsonProperty("tenant") String tenant,
            @JsonProperty("site") String site,
            @JsonProperty("provider") String provider,
            @JsonProperty("packageName") String packageName
    ) {
        this.message = message;
        this.probe = probe;
        this.port = port;
        this.jobType = jobType;
        this.tenant = tenant;
        this.site = site;
        this.provider = provider;
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "KafkaPortAliveMessage{" +
                "jobType=" + jobType +
                ", message='" + message + '\'' +
                ", probe='" + probe + '\'' +
                ", port='" + port + '\'' +
                ", tenant='" + tenant + '\'' +
                ", site='" + site + '\'' +
                ", provider='" + provider + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }
}
