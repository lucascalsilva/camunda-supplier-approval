package br.com.poc.suppliers.model;

import lombok.*;
import org.camunda.bpm.engine.variable.impl.value.FileValueImpl;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Documento {

    @EqualsAndHashCode.Include
    private String tipo;
    private Arquivo arquivo;
    private List<OCRInformation> ocrInformationList;




}
