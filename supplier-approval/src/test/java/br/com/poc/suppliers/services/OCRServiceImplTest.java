package br.com.poc.suppliers.services;

import br.com.poc.suppliers.model.OCRFile;
import br.com.poc.suppliers.model.OCRRequest;
import br.com.poc.suppliers.model.OCRResult;
import connectjar.org.apache.commons.codec.binary.Base64;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
class OCRServiceImplTest {

    @Autowired
    OCRService ocrService;

    @Test
    void testProcessDocument() throws IOException, URISyntaxException {
        /*RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.setConnectTimeout(Duration.ofMillis(3000)).setReadTimeout(Duration.ofMillis(3000)).build();

        OCRService ocrService = new OCRServiceImpl(restTemplate);*/

        URL resource = getClass().getClassLoader().getResource("test-files/examplo-cnd-1.pdf");
        File file = new File(resource.toURI());
        byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        String data = new String(encoded, StandardCharsets.US_ASCII);

        OCRFile ocrFile = OCRFile.builder().fileName("cnd-trabalhistas.pdf").type("CNDTRABALHISTAS").data(data).build();

        OCRRequest ocrRequest = OCRRequest.builder().document("54804482000162").businessName("Empresa S/A Camunda I)")
                .email("lucasc.alm.silva@gmail.com").name("Camunda Pessoa II").files(Arrays.asList(ocrFile)).build();

        OCRResult ocrResult = ocrService.processDocument(ocrRequest);
        log.info("OCRResult: {}", ocrResult.toString());
    }

}