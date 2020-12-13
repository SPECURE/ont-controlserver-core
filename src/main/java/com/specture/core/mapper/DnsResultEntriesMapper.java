package com.specture.core.mapper;

import com.specture.core.model.qos.DnsResultEntries;
import com.specture.core.request.measurement.qos.request.DnsResultEntriesRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DnsResultEntriesMapper {
    DnsResultEntries dnsResultEntriesRequestToDnsResultEntries(DnsResultEntriesRequest dnsResultEntriesRequest);
}
