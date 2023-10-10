package dev.MinseoKangQ.jpa;

import dev.MinseoKangQ.jpa.exception.BaseException;
import dev.MinseoKangQ.jpa.exception.ErrorResponseDto;
import dev.MinseoKangQ.jpa.exception.PostNotInBoardException;
import dev.MinseoKangQ.jpa.exception.PostNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("except")
public class ExceptTestController {

    @GetMapping("{id}")
    public void throwException(@PathVariable int id) {
        switch (id) {
            case 1:
                throw new PostNotExistException();
            case 2:
                throw new PostNotInBoardException();
            default:
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

//    @ExceptionHandler(BaseException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponseDto handleBaseException(BaseException exception) {
//        return new ErrorResponseDto(exception.getMessage());
//    }
}
