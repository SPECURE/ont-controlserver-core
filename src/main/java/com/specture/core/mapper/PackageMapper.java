package com.specture.core.mapper;

import com.specture.core.enums.PackageType;
import com.specture.core.request.PackageRequest;
import com.specture.core.response.PackagePortResponse;
import com.specture.core.response.PackageResponse;
import com.specture.core.model.Package;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Objects;

@Mapper(componentModel = "spring", uses = {TechnologyMapper.class, ProviderMapper.class, PackageType.class})
public interface PackageMapper {

    @Mapping(target = "threshold", qualifiedByName = "UnlimitedToZero")
    @Mapping(target = "download", qualifiedByName = "UnlimitedToZero")
    @Mapping(target = "upload", qualifiedByName = "UnlimitedToZero")
    @Mapping(target = "throttleSpeedDownload", qualifiedByName = "UnlimitedToZero")
    @Mapping(target = "throttleSpeedUpload", qualifiedByName = "UnlimitedToZero")
    @Mapping(target = "isAssignedToPort", expression = "java(((pack.getProbePorts() != null) && (pack.getProbePorts().size() > 0)))")
    @Mapping(target = "packageType", expression = "java(pack.getPackageType().getValue())")
    PackageResponse packageToPackageResponse(Package pack);

    @Mapping(target = "threshold", qualifiedByName = "ZeroToUnlimited")
    @Mapping(target = "download", qualifiedByName = "ZeroToUnlimited")
    @Mapping(target = "upload", qualifiedByName = "ZeroToUnlimited")
    @Mapping(target = "throttleSpeedDownload", qualifiedByName = "ZeroToUnlimited")
    @Mapping(target = "throttleSpeedUpload", qualifiedByName = "ZeroToUnlimited")
    @Mapping(target = "packageType", expression = "java(com.specture.core.enums.PackageType.fromString(packageRequest.getPackageType()))")
    Package packageRequestToPackage(PackageRequest packageRequest);

    @Mapping(target = "port", source = "pack.id")
    @Mapping(target = "packageType", source = "pack.packageType.value")
    PackagePortResponse packageToPackagePortResponse(Package pack);

    @Named("UnlimitedToZero")
    default Integer unlimitedToZero(Integer value) {
        return value.equals(Integer.MAX_VALUE) ? 0 : value;
    }

    @Named("ZeroToUnlimited")
    default Integer zeroToUnlimited(Integer value) {
        return Objects.equals(0, value) ? Integer.MAX_VALUE : value;
    }
}
