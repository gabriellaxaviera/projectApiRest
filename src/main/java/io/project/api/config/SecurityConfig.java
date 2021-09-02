package io.project.api.config;

import io.project.api.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UsuarioServiceImpl usuarioService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        //pacote de pass encoder bcrypt
        return new BCryptPasswordEncoder();
    }

    @Override //autenticacao, user e senha
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override //autorizacao, usuario autenticado tem autorização ou nao para metodos
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/clientes/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/produtos/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/pedidos/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST,"/api/usuario")
                .permitAll()
                .anyRequest().authenticated() //caso nao tenha mapeado outras urls
                .and()
                .httpBasic();
    }
}
