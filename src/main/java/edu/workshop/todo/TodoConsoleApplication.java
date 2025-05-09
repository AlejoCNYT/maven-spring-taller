package edu.workshop.todo;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoConsoleApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner commandLineRunner(TodoManager todoManager) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("==================================");
            System.out.println("💻 CONSOLA DE TAREAS PENDIENTES 💻");
            System.out.println("==================================");
            
            // Añadir algunas tareas de ejemplo
            todoManager.addTask("Aprender Maven");
            todoManager.addTask("Estudiar Spring Boot");
            todoManager.addTask("Implementar Git Flow");
            
            boolean exit = false;
            while (!exit) {
                showMenu();
                int option = getUserOption(scanner);
                
                switch (option) {
                    case 1:
                        listTasks(todoManager);
                        break;
                    case 2:
                        addTask(scanner, todoManager);
                        break;
                    case 3:
                        completeTask(scanner, todoManager);
                        break;
                    case 4:
                        deleteTask(scanner, todoManager);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("⚠️ Opción inválida. Intente de nuevo.");
                }
            }
            
            System.out.println("¡Hasta pronto! 👋");
            scanner.close();
            // Para asegurar que la aplicación termine después de cerrar el menú
            System.exit(0);
        };
    }
    
private static void showMenu() {
    System.out.println("\n--- MENÚ PRINCIPAL ---");
    System.out.println("1. [L] Listar tareas");
    System.out.println("2. [+] Añadir tarea");
    System.out.println("3. [✓] Marcar tarea como completada");
    System.out.println("4. [X] Eliminar tarea");
    System.out.println("5. [S] Salir");
    System.out.print("Seleccione una opción: ");
}
    
    private static int getUserOption(Scanner scanner) {
    try {
        return Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
        return -1;
    }
}

private static void listTasks(TodoManager todoManager) {
    System.out.println("\n[L] LISTA DE TAREAS:");
    System.out.println("------------------");
    
    var tasks = todoManager.getAllTasks();
    if (tasks.isEmpty()) {
        System.out.println("No hay tareas pendientes. ¡Añade una nueva tarea!");
        return;
    }
    
    for (Task task : tasks) {
        System.out.println(task);
    }
}

private static void addTask(Scanner scanner, TodoManager todoManager) {
    System.out.print("\n[+] Ingrese descripción de la nueva tarea: ");
    String description = scanner.nextLine();
    
    if (description.trim().isEmpty()) {
        System.out.println("[!] La descripción no puede estar vacía.");
        return;
    }
    
    Task newTask = todoManager.addTask(description);
    System.out.println("[+] Tarea añadida: " + newTask);
}

private static void completeTask(Scanner scanner, TodoManager todoManager) {
    listTasks(todoManager);
    System.out.print("\n[✓] Ingrese ID de la tarea a completar: ");
    
    try {
        int id = Integer.parseInt(scanner.nextLine());
        if (todoManager.completeTask(id)) {
            System.out.println("[✓] ¡Tarea completada exitosamente!");
        } else {
            System.out.println("[!] Tarea no encontrada.");
        }
    } catch (NumberFormatException e) {
        System.out.println("[!] Por favor ingrese un número válido.");
    }
}

private static void deleteTask(Scanner scanner, TodoManager todoManager) {
    listTasks(todoManager);
    System.out.print("\n[X] Ingrese ID de la tarea a eliminar: ");
    
    try {
        int id = Integer.parseInt(scanner.nextLine());
        if (todoManager.deleteTask(id)) {
            System.out.println("[X] Tarea eliminada exitosamente.");
        } else {
            System.out.println("[!] Tarea no encontrada.");
        }
    } catch (NumberFormatException e) {
        System.out.println("[!] Por favor ingrese un número válido.");
    }
}
}