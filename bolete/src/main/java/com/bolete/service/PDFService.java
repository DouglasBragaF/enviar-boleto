package com.bolete.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolete.dtos.BoletoDTO;
import com.bolete.models.Boleto;
import com.bolete.models.Titular;
import com.bolete.repository.BoletoRepository;
import com.bolete.repository.TitularRepository;

@Service
public class PDFService {

  @Autowired
  private BoletoRepository boletoRepository;

  @Autowired
  private TitularRepository titularRepository;

  /**
   * Processa todos os arquivos PDF em uma pasta especificada e retorna as URLs
   * dos PDFs processados.
   * 
   * @param folderPath O caminho da pasta que contém os arquivos PDF.
   * @return Uma lista de URLs dos PDFs processados.
   */
  public List<String> processPDFsInFolder(String folderPath) {
    File folder = new File(folderPath);
    File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
    List<String> pdfUrls = new ArrayList<>();

    if (files != null) {
      for (File file : files) {
        try {
          BoletoDTO boletoDTO = extractBoletoFromPDF(file);
          Boleto boleto = boletoDTO.toEntity();
          boletoRepository.save(boleto);
          pdfUrls.add(file.getAbsolutePath()); // Adiciona o caminho completo do PDF
        } catch (IOException e) {
          System.err.println("Erro ao processar o arquivo: " + file.getName());
          e.printStackTrace();
        }
      }
    } else {
      System.err.println("A pasta " + folderPath + " não existe ou não pode ser lida.");
    }

    return pdfUrls;
  }

  /**
   * Extrai informações de um arquivo PDF e cria um BoletoDTO.
   * 
   * @param file O arquivo PDF a ser processado.
   * @return Um BoletoDTO com as informações extraídas do PDF.
   * @throws IOException Se ocorrer um erro ao ler o arquivo PDF.
   */
  private BoletoDTO extractBoletoFromPDF(File file) throws IOException {
    // Aqui você pode usar bibliotecas como Apache PDFBox ou iText para ler o
    // conteúdo do PDF
    String ciclo = "Exemplo Ciclo"; // Extraído do PDF
    String parcela = "Exemplo Parcela"; // Extraído do PDF
    String pdfPath = file.getPath();
    String aluno = "Nome do Aluno Extraído"; // Extraído do PDF

    // Extraindo informações do titular do PDF
    String nomeTitular = "Nome do Titular Extraído"; // Extraído do PDF
    String contatoTitular = "Contato do Titular Extraído"; // Extraído do PDF

    // Buscar ou criar o Titular
    Titular titular = titularRepository.findByNome(nomeTitular).orElseGet(() -> {
      Titular novoTitular = new Titular();
      novoTitular.setNome(nomeTitular);
      novoTitular.setContato(contatoTitular);
      return titularRepository.save(novoTitular);
    });

    return new BoletoDTO(null, ciclo, parcela, pdfPath, aluno, titular);
  }
}
