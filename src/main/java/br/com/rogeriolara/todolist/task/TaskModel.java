package br.com.rogeriolara.todolist.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/*
    ID
    Título
    Usuário(ID_USUARIO)
    Descrição
    Data de início
    Data de término
    Prioridade
     */

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;

    @Column(length = 50)
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    // yyyy-mm-ddTHH:mm:ss
    @Column(length = 30)
    private String priority;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private UUID idUser;
}
