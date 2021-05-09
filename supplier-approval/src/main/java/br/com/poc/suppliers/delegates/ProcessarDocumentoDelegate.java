package br.com.poc.suppliers.delegates;

import br.com.poc.suppliers.model.Documento;
import br.com.poc.suppliers.model.OCRFile;
import br.com.poc.suppliers.model.OCRRequest;
import br.com.poc.suppliers.model.OCRResult;
import br.com.poc.suppliers.services.OCRService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Component("processarDocumento")
@RequiredArgsConstructor
@Slf4j
public class ProcessarDocumentoDelegate implements JavaDelegate {

    private final OCRService ocrService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Documento documento = (Documento) delegateExecution.getVariable("documento");
        List<Documento> listaDocumentos = (List<Documento>) delegateExecution.getVariable("listaDocumentos");
        Integer documentoIndex = listaDocumentos.indexOf(documento);
        if(documento.getArquivo() != null) {
            String documentId = UUID.randomUUID().toString();
            String businessName = "Empresa S/A Camunda I";
            String email = "lucasc.alm.silva@gmail.com";
            String name = "Camunda Pessoa II";

            OCRFile ocrFile = OCRFile.builder().fileName(documento.getArquivo().getNomeArquivo())
                    .data(Base64.getEncoder().encodeToString(documento.getArquivo().getConteudo()))
                    .type(documento.getTipo()).build();

            OCRRequest ocrRequest = OCRRequest.builder().document(documentId).businessName(businessName)
                    .email(email).name(name).files(Arrays.asList(ocrFile)).build();

            OCRResult ocrResult = ocrService.processDocument(ocrRequest);
            documento.setOcrInformationList(ocrResult.getData());
            listaDocumentos.set(documentoIndex, documento);
            log.info("OCRResult: {}", ocrResult);
        }
    }
}
