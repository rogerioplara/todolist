package br.com.rogeriolara.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Modificadores
 * public
 * private
 * protected
 */
@RestController
@RequestMapping("/user")
public class UserController {

    // Chamando a interface do repositório
    // @Autowired gerencia a instanciação/ciclo de vida do repositório
    @Autowired
    private IUserRepository userRepository;

    // Esses dados virão de dentro do body da requisição, precisa inserir o
    // @RequestBody antes dos argumentos como annotation
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        // Passando o usermodel que chegou no requset para dentro do repositório,
        // utilizando o .save
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if (user != null) {
            // Podemos retornar a mensagem de erro e o status code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        // Encriptação da senha com o BCrypt - utilizando os parâmetros default
        // Armazena em um array de char
        var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashed);

        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
