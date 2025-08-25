package com.Aerotech.demo.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SeguridadConfig(
    private val jwtFilter: JwtFilter
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/autenticacion/registrar", "/autenticacion/iniciar-sesion").permitAll()



                    .requestMatchers("/vuelos/crear",
                                                   "/vuelos/actualizar/{id}/{request}",
                                                    "/vuelos/consultar-todos",
                                                        "/reservas/todas",
                                                         "/vuelos/actualizar-estado/{id}/{request}",
                                                            "/vuelos/consultar-pasajeros").hasRole("EMPLEADO")

                    .requestMatchers("/vuelos/buscar-origen/{origen}",
                                                  "/vuelos/buscar-destino/{destino}",
                                                  "/vuelos/rango-fechas/{fechaInicio}/{fechaFin}",
                                                    "/reservas/reserva/{id_cliente}",
                                                    "/reservas/mis-reservas/{id_cliente}",
                                                    "/reservas/cancelar/{id_cliente}/{id}").hasRole("CLIENTE")

                    .requestMatchers("/**").hasRole("ADMINISTRADOR")

                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}

