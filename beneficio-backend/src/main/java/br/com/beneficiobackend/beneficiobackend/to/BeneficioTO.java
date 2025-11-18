package br.com.beneficiobackend.beneficiobackend.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeneficioTO {

    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal valor;

    private Boolean ativo;
}
