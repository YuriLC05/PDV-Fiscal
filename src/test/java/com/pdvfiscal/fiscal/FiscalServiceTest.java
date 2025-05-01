package com.pdvfiscal.fiscal;

import com.pdvfiscal.service.FiscalService;
import com.pdvfiscal.entity.Venda;
import com.pdvfiscal.repository.VendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FiscalServiceTest {
    @Mock
    private FiscalConfig fiscalConfig;
    @Mock
    private VendaRepository vendaRepository;
    @InjectMocks
    private FiscalService fiscalService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEmitirNFeSalvaDadosFiscaisNaVenda() {
        Venda venda = new Venda();
        venda.setId(1L);
        when(vendaRepository.save(any(Venda.class))).thenAnswer(invocation -> invocation.getArgument(0));

        fiscalService.emitirNFe(venda);

        assertNotNull(venda.getChaveNfe(), "Chave NFe deve ser gerada");
        assertNotNull(venda.getProtocoloNfe(), "Protocolo NFe deve ser gerado");
        assertNotNull(venda.getXmlNfe(), "XML NFe deve ser gerado");
        verify(vendaRepository, atLeastOnce()).save(venda);
    }
}
