package br.com.poc.suppliers.services;

import br.com.poc.suppliers.model.OCRRequest;
import br.com.poc.suppliers.model.OCRResult;

public interface OCRService {

    OCRResult processDocument(OCRRequest ocrRequest);
}
