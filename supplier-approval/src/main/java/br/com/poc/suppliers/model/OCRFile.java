package br.com.poc.suppliers.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OCRFile {

    private String fileName;
    private String type;
    private String data;
}
