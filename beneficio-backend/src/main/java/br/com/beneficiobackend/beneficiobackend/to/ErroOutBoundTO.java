package br.com.beneficiobackend.beneficiobackend.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErroOutBoundTO {
    private HttpStatus status;
    private String message;
}
