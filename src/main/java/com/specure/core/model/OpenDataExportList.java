package com.specure.core.model;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@XmlRootElement()
public class OpenDataExportList {

    private List<OpenDataExport> openDataExport;
}
