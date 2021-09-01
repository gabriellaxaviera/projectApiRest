package io.project.api.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public PasswordEncoder passwordEncoder() {
        //pacote de pass encoder bcrypt
        return new BCryptPasswordEncoder();
    }

    @Override //autenticacao, user e senha
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()//usuario em memoria
                .passwordEncoder(passwordEncoder())
                .withUser("Fulano").password(passwordEncoder().encode("123"))
                .roles("user") //perfil de usuario
        ;
    }

    @Override //autorizacao, usuario autenticado tem autorização ou nao para metodos
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
