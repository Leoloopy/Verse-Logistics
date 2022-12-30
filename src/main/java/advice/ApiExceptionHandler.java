package advice;

import com.sun.source.tree.BreakTree;
import exceptions.UserAlreadyExistException;
import exceptions.UserNotfoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentException(MethodArgumentNotValidException e){
        Map<String, String> errorMap = new HashMap<>();
        var errors = e.getBindingResult().getFieldError();
        Stream.of(errors).filter(Objects::nonNull).forEach(err -> {
            errorMap.put(err.getField(), err.getDefaultMessage());
        });
        return errorMap;
    }

    @ExceptionHandler(value = UserNotfoundException.class)
    public ResponseEntity<Object> userNotFound(UserNotfoundException e){
    ApiException apiException = new ApiException(
            e.getMessage(),HttpStatus.NOT_FOUND, ZonedDateTime.now()
    );
    return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);

    }



}
