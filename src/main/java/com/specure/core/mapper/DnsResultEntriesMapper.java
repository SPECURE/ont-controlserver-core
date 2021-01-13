package com.specure.core.mapper;

import com.specure.core.model.qos.DnsResultEntries;
import com.specure.core.request.measurement.qos.request.DnsResultEntriesRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DnsResultEntriesMapper {
    DnsResultEntries dnsResultEntriesRequestToDnsResultEntries(DnsResultEntriesRequest dnsResultEntriesRequest);
}
