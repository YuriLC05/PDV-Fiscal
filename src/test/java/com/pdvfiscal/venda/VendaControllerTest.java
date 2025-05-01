package com.pdvfiscal.venda;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdvfiscal.entity.ItemVenda;
import com.pdvfiscal.entity.Produto;
import com.pdvfiscal.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class VendaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProdutoRepository produtoRepository;

    private Produto produto1;
    private Produto produto2;

    @BeforeEach
    public void setup() {
        produtoRepository.deleteAll();
        produto1 = new Produto();
        produto1.setNome("Produto Teste 1");
        produto1.setCodigo("P1");
        produto1.setPreco(new BigDecimal("10.00"));
        produto1.setEstoque(10);
        produto1 = produtoRepository.save(produto1);

        produto2 = new Produto();
        produto2.setNome("Produto Teste 2");
        produto2.setCodigo("P2");
        produto2.setPreco(new BigDecimal("20.00"));
        produto2.setEstoque(5);
        produto2 = produtoRepository.save(produto2);
    }

    @Test
    public void testRegistrarVendaComSucesso() throws Exception {
        ItemVenda item1 = new ItemVenda();
        item1.setProduto(produto1);
        item1.setQuantidade(2);

        ItemVenda item2 = new ItemVenda();
        item2.setProduto(produto2);
        item2.setQuantidade(1);

        mockMvc.perform(post("/api/vendas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Arrays.asList(item1, item2))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(40.00));
    }

    @Test
    public void testRegistrarVendaEstoqueInsuficiente() throws Exception {
        ItemVenda item = new ItemVenda();
        item.setProduto(produto2);
        item.setQuantidade(10); // maior que o estoque

        mockMvc.perform(post("/api/vendas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Arrays.asList(item))))
                .andExpect(status().isBadRequest());
    }
}
