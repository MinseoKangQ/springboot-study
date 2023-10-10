package dev.MinseoKangQ.jpa.handler;

import dev.MinseoKangQ.jpa.exception.BaseException;
import dev.MinseoKangQ.jpa.exception.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class PostControllerAdvice {

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleException(BaseException exception) {
        return new ErrorResponseDto(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto handleValidException(
            MethodArgumentNotValidException exception) {
        return new ErrorResponseDto(exception.getMessage());
    }

}
