package com.specture.core.controller;

import com.specture.core.annotation.ApiPageable;
import com.specture.core.annotation.PageableCase;
import com.specture.core.constant.Case;
import com.specture.core.constant.URIConstants;
import com.specture.core.model.EnrichedPageWithAggregations;
import com.specture.core.response.BasicTestResponse;
import com.specture.core.service.BasicTestService;
import com.specture.core.utils.PageableUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
public class BasicTestController {
    private final BasicTestService basicTestService;

    @PageableCase(value = Case.LOWERCASE)
    @ApiPageable
    @ApiOperation("Get list of basic tests.")
    @GetMapping(URIConstants.BASIC_TEST)
    public EnrichedPageWithAggregations<BasicTestResponse> getBasicTests(
            @RequestParam(required = false) @ApiParam(defaultValue = "[]") ArrayList<Long> packages,
            @RequestParam(required = false) @ApiParam(defaultValue = "[]") ArrayList<String> probes,
            @RequestParam(required = false) @ApiParam(defaultValue = "2020-08-06T14:16:30Z") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(required = false) @ApiParam(defaultValue = "2020-08-12T23:00:00Z") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @ApiIgnore @PageableDefault Pageable pageable
    ) {
        return basicTestService.getBasicTestsWithFilters(packages, probes, dateFrom, dateTo, PageableUtil.convertFromSnakeToCamel(pageable));
    }

    @PageableCase(value = Case.LOWERCASE)
    @ApiPageable
    @ApiOperation("Get list of mobile tests.")
    @GetMapping(URIConstants.MOBILE_TEST)
    public EnrichedPageWithAggregations<BasicTestResponse> getMobileTests(
            @RequestParam(required = false) @ApiParam(defaultValue = "[]") ArrayList<Long> packages,
            @RequestParam(required = false) @ApiParam(defaultValue = "[]") ArrayList<String> probes,
            @RequestParam(required = false) @ApiParam(defaultValue = "2020-08-06T14:16:30Z") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(required = false) @ApiParam(defaultValue = "2020-08-12T23:00:00Z") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @ApiIgnore @PageableDefault Pageable pageable
    ) {
        return basicTestService.getMobileTestsWithFilters(packages, probes, dateFrom, dateTo, PageableUtil.convertFromSnakeToCamel(pageable)); //todo Remove this util
    }

    @PageableCase(value = Case.LOWERCASE)
    @ApiPageable
    @ApiOperation("Get list of web tests.")
    @GetMapping(URIConstants.WEB_TEST)
    public Page<BasicTestResponse> getWebTests(
            @RequestParam(required = false) @ApiParam(defaultValue = "2020-08-06T14:16:30Z") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(required = false) @ApiParam(defaultValue = "2020-08-12T23:00:00Z") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @ApiIgnore @PageableDefault Pageable pageable
    ) {
        return basicTestService.getWebTests(dateFrom, dateTo, PageableUtil.convertFromSnakeToCamel(pageable)); //todo Remove this util
    }

    @PageableCase(value = Case.LOWERCASE)
    @ApiPageable
    @ApiOperation("Get history of basic tests by client uuid.")
    @GetMapping(URIConstants.BASIC_TEST_BY_UUID)
    public Page<BasicTestResponse> getBasicTestsByClientUuid(
            @ApiParam(
                    name = "uuid",
                    type = "String",
                    value = "client unique uuid",
                    example = "e0a84504-8ace-4e54-89f4-d896258f9c3d",
                    required = true) String uuid,
            @ApiIgnore @PageableDefault Pageable pageable
    ) {
        return basicTestService.getBasicTestsByUuid(PageableUtil.convertFromSnakeToCamel(pageable), uuid);
    }

}
