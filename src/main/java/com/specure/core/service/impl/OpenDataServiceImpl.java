package com.specure.core.service.impl;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.specure.core.constant.OpenDataSource;
import com.specure.core.enums.DigitalSeparator;
import com.specure.core.exception.UnsupportedFileExtensionException;
import com.specure.core.model.OpenDataExportList;
import com.specure.core.service.OpenDataInputStreamService;
import com.specure.core.service.OpenDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.specure.core.constant.ErrorMessage.NO_OPEN_DATA_SOURCE;
import static com.specure.core.enums.FileExtension.valueOf;

@Slf4j
@RequiredArgsConstructor
@Service
public class OpenDataServiceImpl implements OpenDataService {

    public static final char DEFAULT_CSV_SEPARATOR = ';';
    private static final String FILENAME_LICENSE = "LICENSE-CC-BY-4.0.txt";

    @Value("classpath:license/" + FILENAME_LICENSE)
    private Resource licenseResource;

    private static final String FILENAME_MONTHLY_EXPORT = "open-data_%d-%d.%s";
    private static final String FILENAME_FULL_EXPORT = "open-data.%s";
    private static final String FILENAME_MONTHLY_EXPORT_ZIP = "open-data_%d-%d.zip";
    private static final String FILENAME_FULL_EXPORT_ZIP = "open-data.zip";

    private final List<OpenDataInputStreamService> openDataRepositoryList;

    @Override
    public ResponseEntity<Object> getOpenDataMonthlyExport(Integer year, Integer month, String fileExtension, DigitalSeparator digitalSeparator, char listSeparator) {

        String filename = String.format(FILENAME_MONTHLY_EXPORT, year, month, fileExtension);

        int toYear = (month == 12) ? year + 1 : year;
        int toMonth = (month == 12) ? 1 : month + 1;

        LocalDateTime fromTime = LocalDateTime.of(year, month, 1, 0, 0, 0, 0);
        LocalDateTime toTime = LocalDateTime.of(toYear, toMonth, 1, 0, 0, 0, 0);

        OpenDataInputStreamService inputStreamService = getOpenDataSource();
        OpenDataExportList<?> data = inputStreamService
                .getAllByTimeBetweenWithSeparator(Timestamp.valueOf(fromTime), Timestamp.valueOf(toTime), digitalSeparator);

        var inputStream = streamOpenData(data, filename, fileExtension, inputStreamService, listSeparator);

        // return open data
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + String.format(FILENAME_MONTHLY_EXPORT_ZIP, year, month) + "\"")
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(inputStream.available())
                .body(new InputStreamResource(inputStream));
    }

    @Override
    public ResponseEntity<Object> getOpenDataFullExport(String fileExtension, DigitalSeparator digitalSeparator, char listSeparator) {

        String filename = String.format(FILENAME_FULL_EXPORT, fileExtension);

        OpenDataInputStreamService inputStreamService = getOpenDataSource();
        OpenDataExportList<?> data = inputStreamService.getAllOpenDataWithSeparator(digitalSeparator);
        ByteArrayInputStream openDataStream = streamOpenData(data, filename, fileExtension, inputStreamService, listSeparator);

        // return open data
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + FILENAME_FULL_EXPORT_ZIP + "\"")
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(openDataStream.available())
                .body(new InputStreamResource(openDataStream));
    }


    public OpenDataInputStreamService getOpenDataSource() {
        return getOpenDataSourceByLabel(OpenDataSource.DATABASE_MEASUREMENT);
    }
    protected OpenDataInputStreamService getOpenDataSourceByLabel(String label) {
        return openDataRepositoryList
                .stream()
                .filter(repository -> repository.getSourceLabel().equals(label))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(NO_OPEN_DATA_SOURCE));
    }

    private ByteArrayInputStream streamOpenData(OpenDataExportList<?> openDataList, String filename, String fileExtension, OpenDataInputStreamService inputStreamService, char listSeparator) {

        ByteArrayOutputStream zip = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(zip);

        try {
            // add open data export file
            createOpenDataExportFile(openDataList, filename, fileExtension, out, inputStreamService, listSeparator);

            // add license file
            createLicenseFile(out);

            // close
            out.close();
        } catch (IOException | JAXBException ex) {
            log.error("An error occurred during exporting data to {}! Exception: {}", fileExtension, ex.getMessage());
        }

        return new ByteArrayInputStream(zip.toByteArray());
    }

    private void createOpenDataExportFile(OpenDataExportList<?> openDataList, String filename, String fileExtension, ZipOutputStream out, OpenDataInputStreamService inputStreamService, char listSeparator) throws IOException, JAXBException {

        // create new zip entry
        out.putNextEntry(new ZipEntry(filename));

        final Class<?> openDataClass = inputStreamService.getOpenDataClass();

        switch (valueOf(fileExtension)) {
            case csv:
                exportToCSV(openDataList, out, openDataClass, listSeparator);
                break;
            case json:
                exportToJson(openDataList, out);
                break;

            case xml:
                exportToXml(openDataList, out, openDataClass);
                break;

            default:
                throw new UnsupportedFileExtensionException(fileExtension);

        }

        // flush and close zip entry
        out.flush();
        out.closeEntry();
    }

    private void exportToXml(OpenDataExportList<?> openDataList, ZipOutputStream out, Class<?> openDataClass) throws JAXBException {
        Marshaller marshaller = JAXBContext.newInstance(OpenDataExportList.class, openDataClass).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(openDataList, out);
    }

    private void exportToJson(OpenDataExportList<?> openDataList, ZipOutputStream out) throws IOException {
        JsonMapper objectMapper = JsonMapper.builder()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .configure(JsonWriteFeature.QUOTE_FIELD_NAMES, false)
                .build();

        out.write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(openDataList.getOpenDataExport()));
    }

    private void exportToCSV(OpenDataExportList<?> openDataList, ZipOutputStream out,Class<?> openDataClass, char listSeparator) throws IOException {
        CsvMapper objectMapper = CsvMapper.builder()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .enable(CsvParser.Feature.TRIM_SPACES)
                .enable(CsvParser.Feature.ALLOW_TRAILING_COMMA)
                .enable(CsvParser.Feature.INSERT_NULLS_FOR_MISSING_COLUMNS)
                .disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .build();

        CsvSchema csvSchema = objectMapper
                .typedSchemaFor(openDataClass)
                .withColumnSeparator(listSeparator)
                .withHeader();

        out.write(objectMapper.writerWithDefaultPrettyPrinter().with(csvSchema).writeValueAsBytes(openDataList.getOpenDataExport()));
    }

    private void createLicenseFile(ZipOutputStream out) throws IOException {
        // create new zip entry
        out.putNextEntry(new ZipEntry(FILENAME_LICENSE));

        // content
        out.write(licenseResource.getInputStream().readAllBytes());

        // flush and close zip entry
        out.flush();
        out.closeEntry();
    }

}
