package com.pdvfiscal.repository;

import com.pdvfiscal.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    // Métodos customizados podem ser adicionados aqui

    @Query("SELECT v FROM Venda v WHERE v.dataHora BETWEEN :inicio AND :fim")
    List<Venda> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT iv.produto.id, SUM(iv.quantidade) FROM ItemVenda iv WHERE iv.venda.dataHora BETWEEN :inicio AND :fim GROUP BY iv.produto.id")
    List<Object[]> relatorioPorProduto(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
