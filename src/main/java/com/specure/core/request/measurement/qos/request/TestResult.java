package com.specure.core.request.measurement.qos.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "test_type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = VoipTestResultRequest.class, name = "voip"),
        @JsonSubTypes.Type(value = TcpTestResultRequest.class, name = "tcp"),
        @JsonSubTypes.Type(value = UdpTestResultRequest.class, name = "udp"),
        @JsonSubTypes.Type(value = NonTransparentProxyTestResultRequest.class, name = "non_transparent_proxy"),
        @JsonSubTypes.Type(value = HttpProxyTestResultRequest.class, name = "http_proxy"),
        @JsonSubTypes.Type(value = DnsTestResultRequest.class, name = "dns")
})
@Getter
@Setter
public class TestResult {
    private String test_type;
}
