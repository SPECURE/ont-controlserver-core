package com.specture.core.mapper;

import com.specture.core.model.OpenDataExport;
import com.specture.core.model.OpenData;

public interface OpenDataMapper {

    OpenDataExport openDataToOpenDataExport(OpenData openData);
}
