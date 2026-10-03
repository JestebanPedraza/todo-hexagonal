package com.app.todo_hexagonal.infrastructure.adapter.in.rest.dto;


import com.app.todo_hexagonal.application.port.in.CreateTaskCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class CreateTaskRequest {

    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    public CreateTaskCommand toCommand() {
        return new CreateTaskCommand(title, description);
    }
}