package com.pdvfiscal.repository;

import com.pdvfiscal.entity.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    // Métodos customizados podem ser adicionados aqui
}
