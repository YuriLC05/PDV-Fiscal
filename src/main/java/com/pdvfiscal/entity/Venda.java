package com.pdvfiscal.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vendas")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<ItemVenda> itens;

    @Column(nullable = true)
    private String chaveNfe;

    @Column(nullable = true)
    private String protocoloNfe;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String xmlNfe;

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public List<ItemVenda> getItens() { return itens; }
    public void setItens(List<ItemVenda> itens) { this.itens = itens; }
    public String getChaveNfe() { return chaveNfe; }
    public void setChaveNfe(String chaveNfe) { this.chaveNfe = chaveNfe; }
    public String getProtocoloNfe() { return protocoloNfe; }
    public void setProtocoloNfe(String protocoloNfe) { this.protocoloNfe = protocoloNfe; }
    public String getXmlNfe() { return xmlNfe; }
    public void setXmlNfe(String xmlNfe) { this.xmlNfe = xmlNfe; }
}
