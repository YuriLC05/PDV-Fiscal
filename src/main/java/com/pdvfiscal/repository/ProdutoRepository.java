package com.pdvfiscal.repository;

import com.pdvfiscal.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Métodos customizados podem ser adicionados aqui
}
