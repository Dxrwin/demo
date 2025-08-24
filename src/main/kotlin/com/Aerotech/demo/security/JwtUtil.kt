// src/main/kotlin/com/Aerotech/demo/security/JwtUtil.kt
    package com.Aerotech.demo.security

    import io.jsonwebtoken.Jwts
    import io.jsonwebtoken.SignatureAlgorithm
    import io.jsonwebtoken.security.Keys
    import java.util.*
    import jakarta.servlet.FilterChain
    import jakarta.servlet.ServletRequest
    import jakarta.servlet.ServletResponse
    import jakarta.servlet.http.HttpServletRequest
    import org.springframework.context.annotation.Bean
    import org.springframework.web.filter.GenericFilterBean


    class JwtUtil(
        private val secret: String = "mi_clave_secreta",
        private val expirationMs: Long = 86400000 // 1 día
    ) {
        private val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
        private val EXPIRATION = 1000 * 60 * 60 // 1 hora

        fun generarToken(username: String): String =
            Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date())
                .setExpiration(Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
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
    }

    // Usa una instancia de JwtUtil en el filtro
    class JwtFilter(private val jwtUtil: JwtUtil) : GenericFilterBean() {
        override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
            val httpRequest = request as HttpServletRequest
            val authHeader = httpRequest.getHeader("Authorization")
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                val token = authHeader.substring(7)
                if (!jwtUtil.validarToken(token)) {
                    throw RuntimeException("Token JWT inválido")
                }
            }
            chain.doFilter(request, response)
        }
    }