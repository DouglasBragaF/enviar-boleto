package com.bolete.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolete.dtos.BoletoDTO;
import com.bolete.models.Boleto;
import com.bolete.models.Titular;
import com.bolete.openai.services.ExtrairCamposService;
import com.bolete.repository.BoletoRepository;
import com.bolete.repository.TitularRepository;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.hibernate.mapping.Set;

@Service
public class PDFService {

  @Autowired
  private BoletoRepository boletoRepository;

  @Autowired
  private TitularRepository titularRepository;

  @Autowired
  private ExtrairCamposService extrairCamposService;

  public boolean hasTriedValidation = false;

  /**
   * Processa todos os arquivos PDF em uma pasta especificada e retorna as URLs
   * dos PDFs processados.
   * 
   * @param folderPath O caminho da pasta que contém os arquivos PDF.
   * @return Uma lista de URLs dos PDFs processados.
   */
  public List<String> processPDFsInFolder(String folderPath) throws ParseException {
    File folder = new File(folderPath);
    File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
    List<String> pdfUrls = new ArrayList<>();

    if (files != null) {
      for (File file : files) {
        try {
          extractBoletoFromPDF(file);
          // Boleto boleto = boletoDTO.toEntity();
          // boletoRepository.save(boleto);
          // pdfUrls.add(file.getAbsolutePath()); // Adiciona o caminho completo do PDF
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
  private void extractBoletoFromPDF(File file) throws IOException, ParseException {

    String parcela = "Exemplo Parcela"; // Extraído do PDF
    String pdfPath = file.getPath();
    String aluno = "Nome do Aluno Extraído"; // Extraído do PDF

    String contatoTitular = "Contato do Titular Extraído"; // Extraído do PDF

    PDDocument document = null;
  
    document = PDDocument.load(file);
    
    PDFTextStripper pdfStripper = new PDFTextStripper();
    // System.out.println("IMPRIMINDO PDF: " + file.getName());
    // System.out.println(pdfStripper.getText(document));
    String text = pdfStripper.getText(document);

    // tenta validação com chatGPT
    if(!hasTriedValidation) {
      extrairCamposService.extrairCampos(text);
      hasTriedValidation = true;

    }

    String nomeTitular = extractNomeTitular(text);

    System.out.println(nomeTitular);
    if(nomeTitular.isEmpty()) {
      System.out.println("Nome do titular não encontrado no PDF " + file.getName());
      return;
    }

    String vencimento = extractVencimento(text);

    System.out.println(vencimento);
    if(vencimento.isEmpty()) {
      System.out.println("Vencimento não encontrado no PDF " + file.getName());
      return;
    }


    // Buscar ou criar o Titular
    // Titular titular = titularRepository.findByNome(nomeTitular).orElseGet(() -> {
    //   Titular novoTitular = new Titular();
    //   novoTitular.setNome(nomeTitular);
    //   novoTitular.setContato(contatoTitular);
    //   return titularRepository.save(novoTitular);
    // });

    // A ideia é jogar o BoletoDTO para a api de titulares na nuvem, usando restTemplate
    // return new BoletoDTO(null, ciclo, parcela, pdfPath, aluno, titular);
  }


  private String extractVencimento(String fullText) throws ParseException {

    String regex = "\\b\\d{2}/\\d{2}/\\d{4}\\b";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(fullText);

    // idealmente tem que pensar em formas disso não quebrar
    // uma coisa é ver a data presente para comparar... se é um boleto
    // o vencimento sempre vai ser no futuro

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    Date minDate = null;
    Date maxDate = null;
    Date midDate = null;


    for(int i = 0; matcher.find(i); i = matcher.end()) {
      // System.out.println("Data encontrada " + matcher.group());

      String data = matcher.group();
      Date date = dateFormat.parse(data);
      if (minDate == null || date.before(minDate) || date.equals(minDate)) {
          minDate = date;
      } else {
        if (maxDate == null || date.after(maxDate) || date.equals(maxDate)) {
            maxDate = date;
        } else {
          midDate = date;
        }
      }
    }

    String identificador = dateFormat.format(midDate);
    int indexData = fullText.indexOf(identificador);
    if(indexData == -1) {
      System.out.println("Não há identificador necessário no PDF!");
    } else {
      return identificador;
    }
    return "";
  }

  private String extractNomeTitular(String fullText) {
    
    String identificador = "Pagador";
    int pagador = fullText.indexOf(identificador);
    if(pagador == -1) {
      System.out.println("Não há identificador necessário no PDF!");
    }

    String subString = fullText.substring(pagador + identificador.length()).trim();

    String[] lines = subString.split("\n");
    if(lines.length > 0) {
      
      String nomePagador = lines[0].split(" - ")[0].trim();
      return nomePagador;
    } else {
      System.out.println("Nome não encontrado");
    }

    return "";
  }


}
