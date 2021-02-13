package com.specure.core.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.List;


@XmlRootElement()
public class OpenDataExportList<T> {
    private List<T> openDataExport;

    public List<T> getOpenDataExport() {
        return openDataExport;
    }

    public void setOpenDataExport(List<T> openDataExport) {
        this.openDataExport = openDataExport;
    }

    public OpenDataExportList(List<T> openDataExport) {
        this.openDataExport = openDataExport;
    }

    public OpenDataExportList() {
        this.openDataExport = Collections.emptyList();
    }

}
