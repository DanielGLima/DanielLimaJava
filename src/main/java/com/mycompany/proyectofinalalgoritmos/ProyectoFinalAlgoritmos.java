/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.proyectofinalalgoritmos;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;


public class ProyectoFinalAlgoritmos {

    public static void main(String[] args) {
    Scanner scann = new Scanner(System.in);
    
    realizarConfiguracionInicial();
    boolean t = true;
    
    while (t) {
        // Menú
        System.out.println("Menú Principal");
        System.out.println("1. Iniciar Sesión");
        System.out.println("2. Registro");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
        int opc =  scann.nextInt();
         scann.nextLine();
        
        switch (opc) {
            case 1:
                String correo = iniciarSesion();
                String rol = identificarRol(correo);
                if (rol.equals("Administrador")) {
                    Scanner tc2 = new Scanner(System.in);
                    System.out.println("Opciones disponibles según su Rol: " + rol);
                    int opc2 = 0;
                    
                    do {
                        // Opciones para el rol actual
                        System.out.println("Rol Administrador");
                        System.out.println("1. Crear Elecciones");
                        System.out.println("2. Inscribir Candidato");
                        System.out.println("3. Eliminar Candidato");
                        System.out.println("4. Salir");
                        opc2 = tc2.nextInt();
                        
                        switch (opc2) {
                            case 1:
                                crearEleccion();
                                break;
                            case 2:
                                System.out.println("Nombre de la Eleccion");
                                String nombreEleccion = tc2.next();
                                inscribirCandidato(nombreEleccion);
                                break;
                            case 3:
                                System.out.println("Nombre de la Eleccion");
                                String nombreEleccion2 = tc2.next();
                                System.out.println("Nombre de Candidato");
                                String nombreCandidato = tc2.next();
                                eliminarCandidato(nombreEleccion2, nombreCandidato);
                                break;
                            case 4:
                                opc = 5;
                                break;
                            default:
                                System.out.println("Opción inválida");
                        }
                    } while (opc != 5);
                    
                } else if (rol.equals("Votante")) {
                    System.out.println("Opciones disponibles según su Rol: " + rol);
                    System.out.println("1. Votar");
                    int v =  scann.nextInt();
                    
                    if (v == 1) {
                        System.out.println("Escriba el nombre de la Eleccion");
                        String nombreEleccion =  scann.next();
                        System.out.println("Escriba el nombre del Candidato");
                        String nombreCandidato =  scann.next();
                        System.out.println("Escriba su nombre");
                        String nombreVotante =  scann.next();
                        emitirVoto(nombreEleccion, nombreCandidato, nombreVotante);
                    }
                } else if (rol.equals("Auditor")) {
                    System.out.println("Opciones disponibles según su Rol: " + rol);
                    System.out.println("1. Generar Reportes");
                    int gr =  scann.nextInt();
                    
                    if (gr == 1) {
                        mostrarInforme();
                    }
                    
                } else if (rol.equals("RegistradorDeVotante")) {
                    System.out.println("Opciones disponibles según su Rol: " + rol);
                    System.out.println("1. Registrar Votante");
                    int rv =  scann.nextInt();
                    
                    if (rv == 1) {
                        registrarVotante();
                    }
                }
                
                break;
            case 2:
                System.out.println("Registro");
                agregarUsuario();
                break;
            case 3:
                t = false;
                break;
            default:
                break;
        }
    } // fin true
}
   

    // Resto del código de las funciones y métodos.
    
    public static void realizarConfiguracionInicial() {
    File archivoConfig = new File("config.txt");

    if (archivoConfig.exists()) {
        System.out.println("La configuración ya ha sido realizada anteriormente.");
    } else {
        try (Scanner scanner = new Scanner(System.in);
             FileWriter fileWriter = new FileWriter(archivoConfig)) {

            System.out.println("Configuración inicial: Por favor, ingrese la contraseña de administrador:");
            String contraseniaAdmin = scanner.nextLine();

            fileWriter.write(contraseniaAdmin);

            System.out.println("Configuración inicial completada. La contraseña de administrador se ha guardado en 'config'.");
        } catch (IOException e) {
            System.err.println("Error al realizar la configuración inicial.");
            e.printStackTrace();
        }
    }
}
    
    
    public static String iniciarSesion() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Inicio de Sesión:");
    System.out.print("Ingrese su correo: ");
    String correoIngresado = scanner.nextLine();
    System.out.print("Ingrese su contraseña: ");
    String contraseniaIngresada = scanner.nextLine();

    if (validarCredenciales(correoIngresado, contraseniaIngresada)) {
        System.out.println("Inicio de sesión exitoso.");
        // Aquí debes determinar el rol del usuario y mostrar las funcionalidades correspondientes.
    } else {
        System.out.println("Credenciales incorrectas. Inicio de sesión fallido.");
    }
    return correoIngresado;
}

private static boolean validarCredenciales(String correo, String contrasenia) {
    try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length >= 4) { // Verificar que haya suficientes elementos
                String correoRegistrado = partes[2];
                String contraseniaRegistrada = partes[3];

                if (correoRegistrado.equals(correo) && contraseniaRegistrada.equals(contrasenia)) {
                    return true;
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Error al leer el archivo de usuarios.");
        e.printStackTrace();
    }
    return false;
}
  

public static void agregarUsuario() {
    Scanner scanner = new Scanner(System.in);

    try {
        System.out.println("Ingrese nombre: ");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese apellido: ");
        String apellido = scanner.nextLine();

        System.out.println("Ingrese correo: ");
        String correo = scanner.nextLine();

        System.out.println("Ingrese contraseña: ");
        String pass = scanner.nextLine();

        System.out.println("Ingrese rol: ");
        String rol = scanner.nextLine();

        String usuarioNuevo = nombre + "," + apellido + "," + correo + "," + pass + "," + rol;
        escribirUsuarioEnArchivo(usuarioNuevo);

        System.out.println("Usuario agregado con éxito.");
    } catch (IOException e) {
        System.err.println("Error al escribir en el archivo.");
        e.printStackTrace();
    }
}

public static void escribirUsuarioEnArchivo(String usuarioNuevo) throws IOException {
    // Crear un FileWriter para escribir en el archivo
    try (FileWriter fileWriter = new FileWriter("usuarios.txt", true);
         PrintWriter printWriter = new PrintWriter(fileWriter)) {
        // Escribir los datos en el archivo
        printWriter.println(usuarioNuevo);
    }
}

public static String identificarRol(String correoBuscado) {
    String archivo = "usuarios.txt";

    try (BufferedReader br = new BufferedReader(new FileReader(archivo)) ) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] valores = linea.split(",");

            if (valores.length >= 5) {
                String correo = valores[2].trim();

                if (correo.equals(correoBuscado)) {
                    return valores[4].trim();
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return "Correo no encontrado";
}


public static void crearEleccion() {
    Scanner scanner = new Scanner(System.in);

    try {
        System.out.println("Creación de Elección:");
        System.out.println("Ingrese el Título de la Elección:");
        String titulo = scanner.nextLine();

        System.out.println("Ingrese el Propósito de la Elección:");
        String proposito = scanner.nextLine();

        System.out.println("Ingrese una Descripción de la Elección:");
        String descripcion = scanner.nextLine();

        System.out.println("Ingrese un Código de Identificación Único:");
        String codigoIdentificacion = scanner.nextLine();

        System.out.println("Ingrese la Fecha y Hora de Inicio de Inscripción (YYYY-MM-DD HH:MM):");
        String fechaInicioInscripcion = scanner.nextLine();

        System.out.println("Ingrese la Fecha y Hora de Fin de Inscripción (YYYY-MM-DD HH:MM):");
        String fechaFinInscripcion = scanner.nextLine();

        String eleccionNueva = titulo + "," + proposito + "," + descripcion + "," + codigoIdentificacion + "," +
                fechaInicioInscripcion + "," + fechaFinInscripcion;

        escribirEleccionEnArchivo(eleccionNueva);

        System.out.println("Elección creada con éxito.");
    } catch (IOException e) {
        System.err.println("Error al crear la elección.");
        e.printStackTrace();
    }
}

public static void escribirEleccionEnArchivo(String eleccionNueva) throws IOException {
    // Crear un FileWriter para escribir en el archivo "elecciones.txt"
    try (FileWriter fileWriter = new FileWriter("elecciones.txt", true);
         PrintWriter printWriter = new PrintWriter(fileWriter)) {
        printWriter.println(eleccionNueva);
    }
}

public static boolean eleccionExiste(String nombreEleccion) {
    try {
        // Leer el archivo de elecciones y verificar si el nombre coincide
        File archivoElecciones = new File("elecciones.txt");
        Scanner scanner = new Scanner(archivoElecciones);

        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine();
            String[] partes = linea.split(",");
            String nombreEleccionEnArchivo = partes[0];

            if (nombreEleccionEnArchivo.equals(nombreEleccion)) {
                scanner.close();
                return true; // La elección existe
            }
        }

        scanner.close();
    } catch (IOException e) {
        System.err.println("Error al verificar si la elección existe.");
        e.printStackTrace();
    }

    return false; // La elección no existe
}

public static void inscribirCandidato(String nombreEleccion) {
    Scanner scanner = new Scanner(System.in);

    try {
        if (eleccionExiste(nombreEleccion)) {
            System.out.println("Inscripción de Candidato en la Elección '" + nombreEleccion + "':");

            System.out.println("Ingrese el Nombre del Candidato:");
            String nombreCandidato = scanner.nextLine();

            System.out.println("Ingrese la Formación del Candidato:");
            String formacionCandidato = scanner.nextLine();

            System.out.println("Ingrese la Experiencia Profesional del Candidato:");
            String experienciaCandidato = scanner.nextLine();

            String candidatoNuevo = nombreCandidato + "," + formacionCandidato + "," + experienciaCandidato + "," + nombreEleccion;

            escribirCandidatoEnArchivo(candidatoNuevo);

            System.out.println("Candidato inscrito con éxito en la Elección '" + nombreEleccion + "'.");
        } else {
            System.out.println("La elección '" + nombreEleccion + "' no existe o no está disponible para inscripción de candidatos.");
        }
    } catch (IOException e) {
        System.err.println("Error al inscribir el candidato.");
        e.printStackTrace();
    }
}

public static void escribirCandidatoEnArchivo(String candidatoNuevo) throws IOException {
    try (FileWriter fileWriter = new FileWriter("candidatos.txt", true);
         PrintWriter printWriter = new PrintWriter(fileWriter)) {
        printWriter.println(candidatoNuevo);
    }
}


public static void eliminarCandidato(String nombreEleccion, String nombreCandidato) {
    try {
        File archivoCandidatos = new File("candidatos.txt");
        File archivoTemporal = new File("temporal.txt");

        Scanner scanner = new Scanner(archivoCandidatos);
        FileWriter fileWriter = new FileWriter(archivoTemporal, true);

        boolean encontrado = false;

        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine();
            String[] partes = linea.split(",");
            String candidatoNombre = partes[0];
            String eleccionNombre = partes[3];

            if (!candidatoNombre.equals(nombreCandidato) || !eleccionNombre.equals(nombreEleccion)) {
                fileWriter.write(linea + System.lineSeparator());
            } else {
                encontrado = true;
            }
        }

        scanner.close();
        fileWriter.close();

        if (encontrado) {
            archivoCandidatos.delete();
            archivoTemporal.renameTo(archivoCandidatos);
            System.out.println("Candidato '" + nombreCandidato + "' eliminado de la Elección '" + nombreEleccion + "'.");
        } else {
            System.out.println("Candidato '" + nombreCandidato + "' no encontrado en la Elección '" + nombreEleccion + "'.");
        }
    } catch (IOException e) {
        System.err.println("Error al eliminar el candidato.");
        e.printStackTrace();
    }
}

public static boolean esMayorDe18(String fechaNacimiento) {
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    try {
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formato);
        LocalDate fechaHoy = LocalDate.now();
        int edad = fechaHoy.getYear() - fechaNac.getYear();
        return edad >= 18;
    } catch (Exception e) {
        System.err.println("Error al verificar la edad.");
        return false;
    }
}

public static String generarContraseniaAleatoria() {
    String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder contrasenia = new StringBuilder();
    Random random = new Random();

    for (int i = 0; i < 16; i++) {
        int index = random.nextInt(caracteres.length());
        contrasenia.append(caracteres.charAt(index));
    }

    return contrasenia.toString();
}



public static void registrarVotante() {
    Scanner scanner = new Scanner(System.in);

    try {
        System.out.println("Registro de Votante:");
        System.out.println("Ingrese Nombres Completos:");
        String nombres = scanner.nextLine();

        System.out.println("Ingrese Apellidos Completos:");
        String apellidos = scanner.nextLine();

        System.out.println("Ingrese CUI:");
        String cui = scanner.nextLine();

        System.out.println("Ingrese Sexo:");
        String sexo = scanner.nextLine();

        System.out.println("Ingrese Fecha de Nacimiento (DD/MM/AAAA):");
        String fechaNacimiento = scanner.nextLine();

        if (esMayorDe18(fechaNacimiento)) {
            System.out.println("Ingrese Dirección de Residencia:");
            String direccionResidencia = scanner.nextLine();

            System.out.println("Ingrese Departamento de Residencia:");
            String departamentoResidencia = scanner.nextLine();

            System.out.println("Ingrese Municipio de Residencia:");
            String municipioResidencia = scanner.nextLine();

            System.out.println("Ingrese País de Residencia:");
            String paisResidencia = scanner.nextLine();

            String contrasenia = generarContraseniaAleatoria();

            escribirVotanteEnArchivo(nombres, apellidos, cui, sexo, fechaNacimiento, direccionResidencia,
                    departamentoResidencia, municipioResidencia, paisResidencia, contrasenia);

            System.out.println("Votante registrado con éxito. Contraseña generada: " + contrasenia);
        } else {
            System.out.println("El votante debe ser mayor de 18 años para registrarse.");
        }
    } catch (IOException e) {
        System.err.println("Error al registrar el votante.");
        e.printStackTrace();
    }
}

public static void escribirVotanteEnArchivo(String nombres, String apellidos, String cui, String sexo,
        String fechaNacimiento, String direccionResidencia, String departamentoResidencia,
        String municipioResidencia, String paisResidencia, String contrasenia) throws IOException {

    try (FileWriter fileWriter = new FileWriter("votante.txt", true);
         PrintWriter printWriter = new PrintWriter(fileWriter)) {

        printWriter.write(nombres + "," + apellidos + "," + cui + "," + sexo + "," + fechaNacimiento + "," +
                direccionResidencia + "," + departamentoResidencia + "," + municipioResidencia + "," +
                paisResidencia + "," + contrasenia + System.lineSeparator());
    }
}

public static void mostrarInforme() {
    System.out.println("Informe del Auditor");
    System.out.println("");
    System.out.println("");
    System.out.println("Usuarios Registrados:");
    System.out.println("");
    System.out.println("");
    mostrarContenidoArchivo("usuarios.txt");
    System.out.println("");
    System.out.println("");
    System.out.println("\nElecciones Disponibles:");
    mostrarContenidoArchivo("elecciones.txt");
    System.out.println("");
    System.out.println("");
    System.out.println("\nVotos Emitidos:");
    mostrarContenidoArchivo("votos.txt");
}

// mostrar el contenido de un archivo
    public static void mostrarContenidoArchivo(String nombreArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + nombreArchivo);
            e.printStackTrace();
        }
    }

public static void emitirVoto(String nombreEleccion, String nombreCandidato, String nombreVotante) {
    try {
        FileWriter fileWriter = new FileWriter("votos.txt", true);
        fileWriter.write(
            nombreEleccion + "," + nombreCandidato + "," + nombreVotante + System.lineSeparator()
        );
        fileWriter.close();

        System.out.println("Voto emitido con éxito en la elección '" + nombreEleccion + "' por el candidato '" + nombreCandidato + "'.");
    } catch (IOException e) {
        System.err.println("Error al emitir el voto.");
        e.printStackTrace();
    }
}




    
    
} // finClase

