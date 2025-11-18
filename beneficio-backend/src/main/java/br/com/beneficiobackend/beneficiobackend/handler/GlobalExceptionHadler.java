package br.com.beneficiobackend.beneficiobackend.handler;

import br.com.beneficio.ejb.exception.BusinessException;
import br.com.beneficiobackend.beneficiobackend.to.ErroOutBoundTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHadler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroOutBoundTO> handlerException(Exception ex) {

        ErroOutBoundTO errorOutbound = new ErroOutBoundTO(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        log.error("INTERNAL_SERVER_ERROR: " + ex.getMessage());
        log.error(ex.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorOutbound);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErroOutBoundTO> handlerResourceNotFoundException(BusinessException ex) {
        ErroOutBoundTO errorOutbound = new ErroOutBoundTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        log.error("BAD_REQUEST: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorOutbound);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroOutBoundTO> handlerResourceNotFoundException(IllegalArgumentException ex) {
        ErroOutBoundTO errorOutbound = new ErroOutBoundTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        log.error("BAD_REQUEST: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorOutbound);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroOutBoundTO> handlerMethodArgumentNotValidException (MethodArgumentNotValidException ex) {

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        String erroMessage = "";

        for (FieldError fieldError : fieldErrors) {
            erroMessage = "Field " + fieldError.getField() + " "
                    + fieldError.getDefaultMessage();
            break;
        }

        ErroOutBoundTO errorOutbound = new ErroOutBoundTO(HttpStatus.BAD_REQUEST, erroMessage);
        log.error("BAD_REQUEST: " + erroMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorOutbound);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErroOutBoundTO> handlerNoResourceFoundException(NoResourceFoundException ex) {
        ErroOutBoundTO errorOutbound = new ErroOutBoundTO(HttpStatus.NOT_FOUND, "Endpoint Inexistente");
        log.error("NOT_FOUND: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorOutbound);
    }
}
