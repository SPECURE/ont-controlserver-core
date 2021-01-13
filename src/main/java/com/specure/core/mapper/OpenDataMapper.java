package com.specure.core.mapper;

import com.specure.core.model.OpenDataExport;
import com.specure.core.model.OpenData;

public interface OpenDataMapper {

    OpenDataExport openDataToOpenDataExport(OpenData openData);
}
