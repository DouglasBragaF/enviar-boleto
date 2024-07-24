package com.bolete.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Boleto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String ciclo;
  private String parcela;
  private String pdf;
  private String aluno;

  @ManyToOne
  @JoinColumn(name = "id_titular")
  private Titular titular;

  public Boleto() {
  }

  public Boleto(String ciclo, String parcela, String pdf, String aluno) {
    this.ciclo = ciclo;
    this.parcela = parcela;
    this.pdf = pdf;
    this.aluno = aluno;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCiclo() {
    return ciclo;
  }

  public void setCiclo(String ciclo) {
    this.ciclo = ciclo;
  }

  public String getParcela() {
    return parcela;
  }

  public void setParcela(String parcela) {
    this.parcela = parcela;
  }

  public String getPdf() {
    return pdf;
  }

  public void setPdf(String pdf) {
    this.pdf = pdf;
  }

  public String getAluno() {
    return aluno;
  }

  public void setAluno(String aluno) {
    this.aluno = aluno;
  }

  public Titular getTitular() {
    return titular;
  }

  public void setTitular(Titular titular) {
    this.titular = titular;
  }

}