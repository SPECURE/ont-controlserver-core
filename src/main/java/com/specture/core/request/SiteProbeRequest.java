package com.specture.core.request;

import lombok.*;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteProbeRequest {

    @NonNull
    private Set<String> probeIds;

}