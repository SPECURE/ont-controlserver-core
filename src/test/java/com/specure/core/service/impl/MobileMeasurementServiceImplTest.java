package com.specure.core.service.impl;

import com.google.common.collect.Lists;
import com.specure.core.TestConstants;
import com.specure.core.repository.MobileMeasurementRepository;
import com.specure.core.service.MobileMeasurementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MobileMeasurementServiceImplTest {
    private MobileMeasurementService mobileMeasurementService;

    @MockBean
    private MobileMeasurementRepository mobileMeasurementRepository;

    @Before
    public void setUp() {
        mobileMeasurementService = new MobileMeasurementServiceImpl(mobileMeasurementRepository);
    }

    @Test
    public void getDeviceHistory_whenCommonData_expectModelList() {
        var modelList = Lists.newArrayList(TestConstants.DEFAULT_MOBILE_MEASUREMENT_MODEL);
        when(mobileMeasurementRepository.getDistinctModelByClientId(TestConstants.DEFAULT_ID))
                .thenReturn(modelList);
        var response = mobileMeasurementService.getDeviceHistory(TestConstants.DEFAULT_ID);

        assertEquals(modelList, response);
    }

    @Test
    public void getGroupNameByClientId_whenCommonData_expectNetworksGroupName() {
        var networkNameList = Lists.newArrayList(TestConstants.DEFAULT_MOBILE_MEASUREMENT_NETWORK);
        when(mobileMeasurementRepository.getDistinctGroupNameByClientId(TestConstants.DEFAULT_ID))
                .thenReturn(networkNameList);

        var response = mobileMeasurementService.getGroupNameByClientId(TestConstants.DEFAULT_ID);

        assertEquals(networkNameList, response);
    }
}