package br.com.beneficio.ejb.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import br.com.beneficio.ejb.entity.Beneficio;
import br.com.beneficio.ejb.exception.BusinessException;
import br.com.beneficio.ejb.to.BeneficioTO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class BeneficioServiceBean implements BeneficioServiceRemote {
	
	@PersistenceContext(unitName = "beneficioPU")
    private EntityManager em;

	@Override
	public void transfer(Long fromId, Long toId, BigDecimal amount) throws BusinessException {
        
      
        if (fromId == null || toId == null || fromId <= 0 || toId <= 0) {
        	throw new BusinessException("IDs de conta inválidos ou nulos.");
        }        
        
        if (fromId.equals(toId)) {
        	throw new BusinessException("Contas de origem e destino não podem ser as mesmas.");
        }
        
        
        if (amount == null) {
            throw new BusinessException("Valor da transferência não pode ser nulo.");
        }
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) { 
            throw new BusinessException("O valor da transferência deve ser positivo.");
        }

        Beneficio from = em.find(Beneficio.class, fromId);
        Beneficio to   = em.find(Beneficio.class, toId);
        
        if (from == null) {
            throw new BusinessException("Conta de origem não encontrada)");
        }
        
        if (to == null) {
            throw new BusinessException("Conta de destino não encontrada");
        }
        
        
        if (amount.compareTo(from.getValor()) > 0) {
        	throw new BusinessException("Saldo insuficiente na conta " + fromId);
        }
        
        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        em.merge(from);
        em.merge(to);
    }
    
	
    @Override
	public List<BeneficioTO> listAll() {
    	List<Beneficio> entidades = em.createQuery("SELECT b FROM Beneficio b", Beneficio.class)
                .getResultList();


    	return entidades.stream()
    			.map(this::fromEntity) 
    			.collect(Collectors.toList());
    }
    
    
    @Override
	public BeneficioTO findById(Long id) {
        Beneficio beneficioExits = em.find(Beneficio.class, id);
        
        if(beneficioExits==null) {
        	return null;
        }
        
        return fromEntity(beneficioExits);
    }
    
    
    
    private  BeneficioTO fromEntity(Beneficio entidade) {
        if (entidade == null) {
            return null;
        }
        
        return new BeneficioTO(
            entidade.getId(),
            entidade.getNome(),
            entidade.getDescricao(),
            entidade.getValor(),
            entidade.getAtivo()
            
        );
    }
    
    
}
