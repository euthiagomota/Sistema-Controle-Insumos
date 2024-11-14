package br.com.SistemaControleInsumos.infra.security;


import br.com.SistemaControleInsumos.entities.User;
import br.com.SistemaControleInsumos.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    UserRepository userRepository;
    private static final int BASIC_LENGTH = 6;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Basic dXN1YXJpbzoxMjM0NTY=
        var headerAuthorization = request.getHeader("Authorization");

        if (headerAuthorization == null || !headerAuthorization.startsWith("Basic ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // dXN1YXJpbzoxMjM0NTY=
        var basicToken = headerAuthorization.substring(BASIC_LENGTH);
        byte[] basicTokenDecoded = Base64.getDecoder().decode(basicToken);

        // usuario:123456
        String basicTokenValue = new String(basicTokenDecoded);

        // ["usuario", "123456"]
        String[] basicAuthsSplit = basicTokenValue.split(":");

        Optional<User> user = this.userRepository.findByEmail(basicAuthsSplit[0]);
        if (user.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        User u = user.get();
        Boolean encryptedPassword = new BCryptPasswordEncoder().matches(basicAuthsSplit[1], u.getPassword());
        if (!encryptedPassword) {
            filterChain.doFilter(request, response);
            return;
        }

        var authToken = new UsernamePasswordAuthenticationToken(basicAuthsSplit[0], null, null);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
