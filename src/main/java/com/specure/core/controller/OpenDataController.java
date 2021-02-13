package com.specure.core.controller;

import com.specure.core.constant.URIConstants;
import com.specure.core.enums.DigitalSeparator;
import com.specure.core.service.OpenDataService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@RestController
public class OpenDataController {

    private final OpenDataService openDataServiceImpl;

    @ApiOperation("Get export file of monthly open data.")
    @GetMapping(value = URIConstants.EXPORT_MONTHLY)
    public ResponseEntity<Object> getMonthlyExportFile(
            @PathVariable @NotNull Integer year,
            @PathVariable @NotNull Integer month,
            @PathVariable @NotNull String fileExtension,
            @RequestParam(required = false, defaultValue = "COMMA") DigitalSeparator digitalSeparator
    ) {
        return openDataServiceImpl.getOpenDataMonthlyExport(year, month, fileExtension, digitalSeparator);
    }

    @ApiOperation("Get export file of full open data.")
    @GetMapping(value = URIConstants.EXPORT_FULL)
    public ResponseEntity<Object> getFullExportFile(
            @PathVariable @NotNull String fileExtension,
            @RequestParam(required = false, defaultValue = "COMMA") DigitalSeparator digitalSeparator
    ) {
        return openDataServiceImpl.getOpenDataFullExport(fileExtension, digitalSeparator);
    }

}
