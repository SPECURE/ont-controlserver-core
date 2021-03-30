package com.specure.core.model;

import com.specure.core.enums.ServerNetworkType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class FilterChip {
    private List<String> packages;
    private List<String> probes;
    private List<String> sites;
    private List<String> providers;
    private List<String> servers;
    private List<String> ports;
    private List<ServerNetworkType> serverType;
    private LocalDateTime from;
    private LocalDateTime to;
    private Pageable pageable;
}
