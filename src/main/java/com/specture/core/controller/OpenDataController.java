package com.specture.core.controller;

import com.specture.core.constant.URIConstants;
import com.specture.core.service.OpenDataService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;


@RequiredArgsConstructor
@RestController
public class OpenDataController {

    private final OpenDataService exportService;

    @ApiOperation("Get export file of monthly open data.")
    @GetMapping(value = URIConstants.EXPORT_MONTHLY)
    public ResponseEntity<Object> getMonthlyExportFile(
            @PathVariable @NotNull Integer year,
            @PathVariable @NotNull Integer month,
            @PathVariable @NotNull String fileExtension
    ) {
        return exportService.getOpenDataMonthlyExport(year, month, fileExtension);
    }

    @ApiOperation("Get export file of full open data.")
    @GetMapping(value = URIConstants.EXPORT_FULL)
    public ResponseEntity<Object> getFullExportFile(
            @PathVariable @NotNull String fileExtension
    ) {
        return exportService.getOpenDataFullExport(fileExtension);
    }

}
