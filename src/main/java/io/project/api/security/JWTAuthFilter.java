package io.project.api.security;

import io.project.api.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthFilter extends OncePerRequestFilter {
    
    private JWTService jwtService;
    private UsuarioServiceImpl usuarioService;

    public JWTAuthFilter(JWTService jwtService, UsuarioServiceImpl usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal
            (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) 
            throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader("Authorization");

        if(authorization != null && authorization.startsWith("Bearer")){
            String token = authorization.split(" ")[1]; //posicao 1 representa o token após o nome bearer

            if(jwtService.tokenIsValid(token)){
                String loginUsuario = jwtService.obterLoginUsuario(token);
                UserDetails userByUsername = usuarioService.loadUserByUsername(loginUsuario);//busca usuario no banco com suas roles
                //incluir o usuario no contexto do spring security
                UsernamePasswordAuthenticationToken user =
                        new UsernamePasswordAuthenticationToken(userByUsername,null,userByUsername.getAuthorities());
                //indicando que é uma autenticacao de aplicacao web
                user.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(httpServletRequest));
                //contexto do spring
                SecurityContextHolder.getContext().setAuthentication(user);
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);

        }
    }
}
