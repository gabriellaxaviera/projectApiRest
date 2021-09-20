package io.project.api.rest.controller;

import io.project.api.domain.model.Usuario;
import io.project.api.exception.InvalidPasswordException;
import io.project.api.rest.dto.CredentialsDTO;
import io.project.api.rest.dto.TokenDTO;
import io.project.api.security.JWTService;
import io.project.api.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios/")
public class UsuarioController {

    @Autowired
    UsuarioServiceImpl usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTService jwtService;

    @PostMapping
    public ResponseEntity<?> cadastraUsuario(@RequestBody @Valid Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        usuarioService.salvarUsuarioNoBanco(usuario);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("auth")
    public ResponseEntity<TokenDTO> autenticar(@RequestBody CredentialsDTO credentialsDTO) {
        try {
            Usuario usuario = Usuario.builder()
                    .login(credentialsDTO.getLogin())
                    .senha(credentialsDTO.getSenha()).build();

            usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);

            TokenDTO tokenDTO = new TokenDTO(usuario.getLogin(), token);
            return ResponseEntity.ok().body(tokenDTO);

        } catch (UsernameNotFoundException | InvalidPasswordException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, exception.getMessage());
        }
    }
}
