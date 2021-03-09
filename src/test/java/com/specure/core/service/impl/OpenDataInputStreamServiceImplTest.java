package com.specure.core.service.impl;

import com.specure.core.enums.DigitalSeparator;
import com.specure.core.enums.MeasurementStatus;
import com.specure.core.mapper.OpenDataMapper;
import com.specure.core.model.OpenData;
import com.specure.core.model.OpenDataExport;
import com.specure.core.repository.OpenDataRepository;
import com.specure.core.service.OpenDataInputStreamService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

import static com.specure.core.service.impl.OpenDataInputStreamServiceImpl.LABEL_DATA_SOURCE;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OpenDataInputStreamServiceImplTest {

    @MockBean
    private OpenDataRepository openDataRepository;
    @MockBean
    private OpenDataMapper openDataMapper;

    private OpenDataInputStreamService openDataInputStreamService;
    @Before
    public void setUp() {
        openDataInputStreamService = new OpenDataInputStreamServiceImpl(openDataRepository, openDataMapper);
    }

    @Test
    public void findAllByTimeBetweenAndStatus_whenInvoke_expectReturnOpenDataList() {

        Timestamp start = Timestamp.valueOf("2021-10-01 00:00:00");
        Timestamp finish = Timestamp.valueOf("2021-11-01 00:00:00");

        OpenData openData = OpenData.builder().build();
        OpenDataExport openDataExport = OpenDataExport.builder().build();

        when(openDataRepository.findAllByTimeBetweenAndStatus(eq(start), eq(finish), eq(MeasurementStatus.FINISHED)))
                .thenReturn(List.of(openData));
        when(openDataMapper.openDataToOpenDataExport(eq(openData)))
                .thenReturn(openDataExport);

        var result = openDataInputStreamService.getAllByTimeBetweenWithSeparator(start, finish, DigitalSeparator.COMMA, null);

        Assert.assertEquals(1, result.getOpenDataExport().size());
    }

    @Test
    public void findAllByStatus() {
        OpenData openData = OpenData.builder().build();
        OpenDataExport openDataExport = OpenDataExport.builder().build();

        when(openDataRepository.findAllByStatus(eq(MeasurementStatus.FINISHED)))
                .thenReturn(List.of(openData));
        when(openDataMapper.openDataToOpenDataExport(eq(openData)))
                .thenReturn(openDataExport);

        var result = openDataInputStreamService.getAllOpenDataWithSeparator( DigitalSeparator.COMMA, null);

        Assert.assertEquals(1, result.getOpenDataExport().size());
    }

    @Test
    public void getSourceLabel_whenInvoke_expectReturnLabel() {
        Assert.assertEquals(LABEL_DATA_SOURCE, openDataInputStreamService.getSourceLabel());
    }

    @Test
    public void getOpenDataClass() {
        Assert.assertEquals(OpenDataExport.class, openDataInputStreamService.getOpenDataClass());
    }
}
