package br.com.rogeriolara.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

// @Data é uma anotation do lombok que define os getters e setters para todos os atributos
// É possível também utilizar o @Getters ou @Setters
// também é possível definir por atributo com @Getters e @Setters
@Data
// Criação da tabela
@Entity(name = "tb_users")
public class UserModel {

    // UUID gera um id único
    // @Id define a chave primária da tabela
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    // @Column define o nome da coluna caso queira, se não cria com o nome da
    // variavel/ unique=true define um constraint na coluna
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
