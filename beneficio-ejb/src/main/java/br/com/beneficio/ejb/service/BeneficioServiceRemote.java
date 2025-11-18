package br.com.beneficio.ejb.service;

import java.math.BigDecimal;
import java.util.List;

import br.com.beneficio.ejb.entity.Beneficio;
import br.com.beneficio.ejb.exception.BusinessException;
import br.com.beneficio.ejb.to.BeneficioTO;
import jakarta.ejb.Remote;
@Remote
public interface BeneficioServiceRemote {

	void transfer(Long fromId, Long toId, BigDecimal amount) throws BusinessException;

	
	List<BeneficioTO> listAll();

	
	BeneficioTO findById(Long id);

	
	

}