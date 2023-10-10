package br.com.rogeriolara.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

// O repositório é responsável por inserir os dados no banco de dados
// Dessa forma, utiliza-se de uma interface que herda de JpaRepository<Entidade, ID da classe>
public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    // Define uma função de busca por username, retorna o usermodel
    UserModel findByUsername(String username);
}
