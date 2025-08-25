// src/main/kotlin/com/Aerotech/demo/security/JwtUtil.kt
    package com.Aerotech.demo.security

    import com.Aerotech.demo.entities.RolUsuario
    import io.jsonwebtoken.Jwts
    import io.jsonwebtoken.SignatureAlgorithm
    import io.jsonwebtoken.security.Keys
    import java.util.*
    import jakarta.servlet.FilterChain
    import jakarta.servlet.ServletRequest
    import jakarta.servlet.ServletResponse
    import jakarta.servlet.http.HttpServletRequest
    import jakarta.servlet.http.HttpServletResponse
    import org.springframework.context.annotation.Bean
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
    import org.springframework.security.core.authority.SimpleGrantedAuthority
    import org.springframework.security.core.context.SecurityContextHolder
    import org.springframework.stereotype.Component
    import org.springframework.web.filter.GenericFilterBean
    import org.springframework.web.filter.OncePerRequestFilter
    import javax.crypto.spec.SecretKeySpec
    import java.nio.charset.StandardCharsets
    import kotlin.text.get

@Component
class JwtUtil(
    private val secret: String = "mi_clave_secreta_muy_segura_1234567890"
) {
    private val key = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.jcaName)
    private val EXPIRATION = 1000 * 60 * 60 // 1 hora

    fun generarToken(username: String, rol: RolUsuario): String =
        Jwts.builder()
            .setSubject(username)
            .claim("rol", rol)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

    fun validarToken(token: String): Boolean =
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }

    fun obtenerUsername(token: String): String =
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.subject

    fun obtenerRol(token: String): String {
        val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        return claims["rol"].toString()
    }
}

    // Usa una instancia de JwtUtil en el filtro
    @Component
    class JwtFilter(private val jwtUtil: JwtUtil) : OncePerRequestFilter() {

        override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
        ) {
            val authHeader = request.getHeader("Authorization")

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                val token = authHeader.substring(7)

                if (jwtUtil.validarToken(token)) {
                    val username = jwtUtil.obtenerUsername(token)
                    val rol = jwtUtil.obtenerRol(token)

                    // 🔑 Spring Security requiere que los roles tengan prefijo "ROLE_"
                    val authorities = listOf(SimpleGrantedAuthority("ROLE_$rol"))

                    val auth = UsernamePasswordAuthenticationToken(username, null, authorities)
                    SecurityContextHolder.getContext().authentication = auth
                }
            }

            filterChain.doFilter(request, response)
        }
    }