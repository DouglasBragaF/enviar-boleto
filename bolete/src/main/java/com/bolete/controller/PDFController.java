package com.bolete.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bolete.service.PDFService;

@RestController
@RequestMapping("/api/boletos")
public class PDFController {

  @Autowired
  private PDFService pdfService;

  @PostMapping("/processar")
  public void processarBoletos(@RequestParam String folderPath) throws ParseException {
    pdfService.processPDFsInFolder(folderPath);
  }
}
