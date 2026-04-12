package product.product.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Boolean> handleException(RuntimeException ex){

        return ResponseEntity.status(404).body(false);
    }
}
