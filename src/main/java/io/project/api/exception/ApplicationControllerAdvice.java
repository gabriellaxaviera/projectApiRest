package io.project.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException regraNegocioException) {
        String mensagemErro = regraNegocioException.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException pedidoNaoEncontradoException) {
        String mensagemErro = pedidoNaoEncontradoException.getMessage();
        return new ApiErrors(mensagemErro);
    }
}
