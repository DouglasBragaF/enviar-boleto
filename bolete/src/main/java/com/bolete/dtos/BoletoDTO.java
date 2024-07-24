package com.bolete.dtos;

import com.bolete.models.Boleto;
import com.bolete.models.Titular;

public record BoletoDTO(
    Integer id,
    String ciclo,
    String parcela,
    String pdf,
    String aluno,
    Titular titular) {

  public Boleto toEntity() {
    Boleto boleto = new Boleto();
    boleto.setId(this.id);
    boleto.setCiclo(this.ciclo);
    boleto.setParcela(this.parcela);
    boleto.setPdf(this.pdf);
    boleto.setAluno(this.aluno);
    boleto.setTitular(this.titular);
    return boleto;
  }

  public static BoletoDTO toDTO(Boleto boleto) {
    return new BoletoDTO(
        boleto.getId(),
        boleto.getCiclo(),
        boleto.getParcela(),
        boleto.getPdf(),
        boleto.getAluno(),
        boleto.getTitular());
  }
}
