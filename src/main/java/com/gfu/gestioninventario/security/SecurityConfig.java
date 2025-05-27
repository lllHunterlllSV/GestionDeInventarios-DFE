package com.gfu.gestioninventario.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.gfu.gestioninventario.Services.CustomUserDetailsService;

@Configuration
public class SecurityConfig {
    
    //ENCRIPTADOR Y COMPARADOR DE CONTRASE;A
    @Bean
    public PasswordEncoder PasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //AUTENTICACION DEL LOGIN
    @Bean
    public DaoAuthenticationProvider authProvider(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    //DEFINE LAS AUTORIZACIONES DE ACCESO
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomLoginSuccessHandler successHandler) throws Exception{
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()


                            // Rutas críticas (solo ADMIN)
                            .requestMatchers(
                                    "/productos/eliminar/**",
                                    "/proveedores/eliminar/**",
                                    "/clientes/eliminar/**",
                                    "/configuracion/usuarios/eliminar/**"

                            ).hasRole("ADMIN")

                  // Rutas de configuración de usuarios (solo ADMIN)
                            .requestMatchers("/configuracion/usuarios/**").hasRole("ADMIN")
                      // Configuración general (puedes ampliar si algún día hay más roles en configuración)
                            .requestMatchers("/configuracion/**").hasAnyRole("ADMIN")
                        // Productos: crear, ver, editar (ADMIN y MANAGER)
                            .requestMatchers("/productos/**").hasAnyRole("ADMIN","MANAGER")
                     // Proveedores: crear, ver, editar (ADMIN y MANAGER)
                            .requestMatchers("/proveedores/**").hasAnyRole("ADMIN","MANAGER")
                       // Clientes: editar (ADMIN y MANAGER)
                            .requestMatchers("/clientes/editar/**").hasAnyRole("ADMIN","MANAGER")
                       // Clientes: ver/listar (todos los roles)
                            .requestMatchers("/clientes/lista").hasAnyRole("ADMIN","SALES","MANAGER")
                       // Clientes: crear, guardar, etc. (ADMIN y MANAGER)
                            .requestMatchers("/clientes/**").hasAnyRole("ADMIN","MANAGER")
                       // Compras (ADMIN y MANAGER)
                            .requestMatchers("/compras/**").hasAnyRole("ADMIN","MANAGER")
                            .requestMatchers("/usuario/**").hasAnyRole("ADMIN","SALES","MANAGER")
                    //Rutas de alerta
                    .requestMatchers("/alertas/stock-bajo").hasAnyRole("ADMIN", "MANAGER")
                    .requestMatchers("/stock/**").hasAnyRole("ADMIN", "MANAGER")




                            .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")
                    .loginProcessingUrl("/login")

                    .usernameParameter("usuario")
                    .passwordParameter("contrasena")
                    .successHandler(successHandler)
                    .defaultSuccessUrl("/dashboard", true)

                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            ) .exceptionHandling(ex -> ex
                        .accessDeniedPage("/accessDenied")
                );

            return http.build();
    }


}
