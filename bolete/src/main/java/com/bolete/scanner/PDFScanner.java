package com.bolete.scanner;

import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Escaneia uma pasta e conta os PDFs a cada minuto
 */
@Component
public class PDFScanner {

    @Value("${bolete.pasta-boletos}")
    private String folderPath;

    @Scheduled(fixedRate = 60000) 
    public void scanFolder() {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            int pdfCount = 0;
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".pdf")) {
                    pdfCount++;
                }
            }
            System.out.println("Número de arquivos PDF na pasta: " + pdfCount);
        } else {
            System.out.println("A pasta não existe ou não pode ser lida.");
        }
    }
}
