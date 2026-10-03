package com.app.todo_hexagonal.application.port.in;

/**
 * Datos de entrada del caso de uso "crear tarea".
 * Desacopla a los adaptadores de entrada de la forma en que el dominio construye una Task.
 */
public record CreateTaskCommand(String title, String description) {
}