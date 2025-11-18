package br.com.beneficiobackend.beneficiobackend.controller;

import br.com.beneficio.ejb.exception.BusinessException;
import br.com.beneficio.ejb.service.BeneficioServiceRemote;
import br.com.beneficio.ejb.to.BeneficioTO;
import br.com.beneficiobackend.beneficiobackend.service.BeneficioClientService;

import br.com.beneficiobackend.beneficiobackend.to.TransferRequestTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/beneficio")
@Tag(name = "Benefício", description = "API para gerenciamento de benefícios")
public class BeneficioController {

    @Autowired
    BeneficioServiceRemote beneficioServiceRemote;

    @Autowired
    BeneficioClientService beneficioClientService;

    @Operation(
            summary = "Lista todos os benefícios",
            description = "Retorna uma lista com todos os benefícios cadastrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping("/list")
    public List<BeneficioTO> listarBeneficio() {
     return beneficioClientService.beneficioTOList();
    }

    @Operation(
            summary = "Busca benefício por ID",
            description = "Retorna um benefício específico pelo seu identificador"
    )
    @GetMapping("/{id}")
    public BeneficioTO beneficioTO(@PathVariable Long id) {
        return beneficioServiceRemote.findById(id);
    }

    @Operation(
            summary = "Realiza a transferencia entre beneficios"
    )
    @PostMapping("/transfer")
    public void transferenciaBeneficio(@RequestBody TransferRequestTO transfer) throws BusinessException {
        beneficioClientService.transfer(transfer);
    }
}
