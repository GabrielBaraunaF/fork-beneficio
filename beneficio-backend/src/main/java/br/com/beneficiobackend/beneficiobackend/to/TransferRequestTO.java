package br.com.beneficiobackend.beneficiobackend.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestTO {

    private Long fromId;
    private Long toId;
    private BigDecimal amount;
}
