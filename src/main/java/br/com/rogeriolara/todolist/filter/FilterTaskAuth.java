package br.com.rogeriolara.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rogeriolara.todolist.user.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

/*
Todas as requisições, antes de chegarem no controller, vão passar pelo filtro
 */
@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Pegar a autenticação (usuário e senha)
        var authorization = request.getHeader("Authorization");

        // Remoção da palavra "Basic" que retorna da header e os espaços
        var authEncoded = authorization.substring("Basic".length()).trim();

        // O retorno vem em um array de bytes
        byte[] authDecode = Base64.getDecoder().decode(authEncoded);
        // Conversão do array de bytes para uma string
        var authString = new String(authDecode);

        // O decode vem separando o usuário e a senha por um : -> split armazena em um array as posições 1 e 2
        String[] credentials = authString.split(":");
        // armazenando a posição 1
        String username = credentials[0];
        // armazenando a posição 2
        String password = credentials[1];

        // Validar usuário
        var user = this.userRepository.findByUsername(username);
        if (user == null){
            response.sendError(401);
        } else {
            // Validar senha
            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if (passwordVerify.verified){
                filterChain.doFilter(request, response);
            } else {
                response.sendError(401);
            }
        }

    }
}
