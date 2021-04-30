package br.com.poc.suppliers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OCRInformation {

    private String documentId;
    private String documentName;
    private String information;
    private String value;
}
