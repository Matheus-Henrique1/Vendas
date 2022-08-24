package matheus.com.vendas.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import matheus.com.vendas.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UsuarioServiceImpl usuarioService;

    @Lazy
    @Autowired
    public SecurityConfig(UsuarioServiceImpl usuarioService){
        this.usuarioService = usuarioService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.
                userDetailsService(usuarioService)
                    .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/clientes/**")
                    .hasAnyRole("USER", "ADMIN")
                .antMatchers("/pedidos/**")
                    .hasAnyRole("USER", "ADMIN")
                .antMatchers("/produtos/**")
                    .hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/usuarios/**")
                    .permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }


}
