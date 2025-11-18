package br.com.beneficio.ejb.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.beneficio.ejb.entity.Beneficio;
import br.com.beneficio.ejb.exception.BusinessException;
import br.com.beneficio.ejb.to.BeneficioTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
class BeneficioServiceBeanTest {
	
	@Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Beneficio> typedQuery;

    @InjectMocks
    private BeneficioServiceBean service;

    private Beneficio beneficioOrigem;
    private Beneficio beneficioDestino;

    @BeforeEach
    void setUp() {
        beneficioOrigem = new Beneficio("Vale Alimentação", "Benefício de alimentação", new BigDecimal("1000.00"));
        beneficioOrigem.setId(1L);
        beneficioOrigem.setVersion(0L);

        beneficioDestino = new Beneficio("Vale Transporte", "Benefício de transporte", new BigDecimal("500.00"));
        beneficioDestino.setId(2L);
        beneficioDestino.setVersion(0L);
    }
    
    @Test
    void testListAll_Empty() {
        
        when(em.createQuery("SELECT b FROM Beneficio b", Beneficio.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList());

        List<BeneficioTO> resultado = service.listAll();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

	
	@Test
    void testTransfer_Success() throws BusinessException {
        
        BigDecimal valorTransferencia = new BigDecimal("200.00");
        when(em.find(Beneficio.class, 1L)).thenReturn(beneficioOrigem);
        when(em.find(Beneficio.class, 2L)).thenReturn(beneficioDestino);

        
        service.transfer(1L, 2L, valorTransferencia);

        
        assertEquals(new BigDecimal("800.00"), beneficioOrigem.getValor());
        assertEquals(new BigDecimal("700.00"), beneficioDestino.getValor());
       
    }
	@Test
	  void testTransfer_InsufficientBalance() {
	        
	        when(em.find(Beneficio.class, 1L)).thenReturn(beneficioOrigem);
	        when(em.find(Beneficio.class, 2L)).thenReturn(beneficioDestino);

	       
	        BusinessException exception = assertThrows(
	            BusinessException.class,
	            () -> service.transfer(1L, 2L, new BigDecimal("2000.00"))
	        );
	        assertEquals("Saldo insuficiente na conta 1", exception.getMessage());
	    }
	
	@Test
    void testTransfer_NegativeAmount() {
        
        BusinessException exception = assertThrows(
            BusinessException.class,
            () -> service.transfer(1L, 2L, new BigDecimal("-100.00"))
        );
        assertEquals("O valor da transferência deve ser positivo.", exception.getMessage());
    }



	@Test
	void testFindById_Success() {
	        // Arrange
	        when(em.find(Beneficio.class, 1L)).thenReturn(beneficioOrigem);

	        // Act
	        BeneficioTO resultado = service.findById(1L);

	        // Assert
	        assertNotNull(resultado);
	        assertEquals(1L, resultado.getId());
	        assertEquals("Vale Alimentação", resultado.getNome());
	        assertEquals(new BigDecimal("1000.00"), resultado.getValor());
	    }

	 @Test
	 void testFindById_NotFound() {
	        // Arrange
	        when(em.find(Beneficio.class, 999L)).thenReturn(null);

	        // Act
	        BeneficioTO resultado = service.findById(999L);

	        // Assert
	        assertNull(resultado);
	    }

}
