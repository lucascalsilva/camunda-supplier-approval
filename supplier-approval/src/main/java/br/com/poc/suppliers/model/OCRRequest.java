package br.com.poc.suppliers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OCRRequest {

    private String document;
    private String businessName;
    private String email;
    private String name;
    private List<OCRFile> files;
}
