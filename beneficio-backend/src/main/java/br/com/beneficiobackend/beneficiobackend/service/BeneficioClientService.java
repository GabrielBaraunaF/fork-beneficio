package br.com.beneficiobackend.beneficiobackend.service;

import br.com.beneficio.ejb.exception.BusinessException;
import br.com.beneficio.ejb.service.BeneficioServiceRemote;
import br.com.beneficio.ejb.to.BeneficioTO;
import br.com.beneficiobackend.beneficiobackend.to.TransferRequestTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BeneficioClientService {

    private final BeneficioServiceRemote ejb;

    public BeneficioClientService(BeneficioServiceRemote ejb) {
        this.ejb = ejb;
    }

    public List<BeneficioTO> beneficioTOList(){
        return ejb.listAll();
    }

    public void transfer(TransferRequestTO transfer) throws BusinessException {
        System.out.println(transfer.getFromId()+" "+transfer.getToId());
        ejb.transfer(transfer.getFromId(), transfer.getToId(), transfer.getAmount());
    }

    public BeneficioTO findById(Long id) {
        BeneficioTO beneficioTO = ejb.findById(id);
        System.out.println(beneficioTO.getValor());
        return beneficioTO;
    }
}
