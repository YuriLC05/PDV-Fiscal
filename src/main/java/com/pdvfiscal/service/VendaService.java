package com.pdvfiscal.service;

import com.pdvfiscal.entity.ItemVenda;
import com.pdvfiscal.entity.Produto;
import com.pdvfiscal.entity.Venda;
import com.pdvfiscal.repository.ItemVendaRepository;
import com.pdvfiscal.repository.ProdutoRepository;
import com.pdvfiscal.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ItemVendaRepository itemVendaRepository;
    @Autowired
    private FiscalService fiscalService;

    @Transactional
    public Venda registrarVenda(List<ItemVenda> itens) {
        Venda venda = new Venda();
        venda.setDataHora(LocalDateTime.now());
        venda.setItens(itens);
        BigDecimal total = BigDecimal.ZERO;
        for (ItemVenda item : itens) {
            Produto produto = produtoRepository.findById(item.getProduto().getId()).orElseThrow();
            if (produto.getEstoque() < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }
            produto.setEstoque(produto.getEstoque() - item.getQuantidade());
            produtoRepository.save(produto);
            item.setVenda(venda);
            item.setPrecoUnitario(produto.getPreco());
            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }
        venda.setTotal(total);
        Venda vendaSalva = vendaRepository.save(venda);
        itemVendaRepository.saveAll(itens);
        // Integração fiscal: emitir NFe ao registrar venda
        try {
            fiscalService.emitirNFe(vendaSalva); // Passa a venda real para emissão da NFe
        } catch (Exception e) {
            // Logar erro fiscal, mas não impedir venda (pode customizar conforme regra do negócio)
            System.err.println("Falha ao emitir NFe: " + e.getMessage());
        }
        return vendaSalva;
    }

    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

    public Optional<Venda> buscarPorId(Long id) {
        return vendaRepository.findById(id);
    }

    public List<Venda> relatorioPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return vendaRepository.findByPeriodo(inicio, fim);
    }

    public List<Object[]> relatorioPorProduto(LocalDateTime inicio, LocalDateTime fim) {
        return vendaRepository.relatorioPorProduto(inicio, fim);
    }
}
