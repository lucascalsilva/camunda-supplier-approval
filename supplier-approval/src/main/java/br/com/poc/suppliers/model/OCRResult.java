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
public class OCRResult {

    private String companyId;
    private List<OCRInformation> data;
}
