package com.bolete.scanner;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bolete.service.PDFService;

/**
 * Escaneia uma pasta e processa os PDFs a cada minuto.
 */
@Component
public class PDFScanner {

    @Value("${bolete.pasta-boletos}")
    private String folderPath;

    @Autowired
    private PDFService pdfService;

    @Scheduled(fixedRate = 60000)
    public void scanFolder() throws ParseException {
        pdfService.processPDFsInFolder(folderPath);
        System.out.println("SCANNING");
    }
}
