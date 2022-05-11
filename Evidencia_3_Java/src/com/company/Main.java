package com.company;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in); //leer inputs de usuario
    public static ArrayList<Administrador> administradoresArray = new ArrayList<>(); //almacenar administradores
    public static ArrayList<Doctor> doctoresArray = new ArrayList<>(); //almacenar doctores
    public static ArrayList<Paciente> pacientesArray = new ArrayList<>(); //almacenar pacientes
    public static ArrayList<Cita> citasArray = new ArrayList<>(); //almacenar citas
    public static String separator = FileSystems.getDefault().getSeparator(); //separador que varia según el SO del usuario
    public static String citasString; //ubicación donde se almacenan las citas
    public static String doctoresString; //ubicación donde se almacenan los doctores
    public static String pacientesString; //ubicación donde se almacenan los pacientes

    public static void main(String[] args) throws IOException {

        //obtiene ubicación absoluta (en el IDE llega hasta Evidencia_3_Java y en
        //la build llega hasta Evidencia_3_Java\out\artifacts\Evidencia_3_Java_jar
        String ubicacionBuild = System.getProperty("user.dir");
        //mas que para separar servirá para cortar lo que no necesitamos (la ubicación de la build)
        //y quedarnos con lo que queremos (la ubicación del proyecto)
        String[] ubicacionSplit = ubicacionBuild.split("\\\\out\\\\artifacts\\\\Evidencia_3_Java_jar");
        String ubicacionProyecto = ubicacionSplit[0];

        //Ejemplo de como funcionan estos String:
        // ubicacionBuild es igual a    -->   E:\IdeaProjects\Evidencia_3_Java\out\artifacts\Evidencia_3_Java_jar
        // ubicacionSplit[0] es igual a -->   E:\IdeaProjects\Evidencia_3_Java
        // ubicacionSplit[1] es igual a -->   no existe, pues el split "recorta" el final
        // ubicacionProyecto es igual a -->   E:\IdeaProjects\Evidencia_3_Java

        //crea los archivos si no existen
        File citasFile = new File(ubicacionProyecto+separator+"db"+separator+"citas.txt");
        citasFile.createNewFile(); // if file already exists will do nothing
        File pacientesFile = new File(ubicacionProyecto+separator+"db"+separator+"pacientes.txt");
        pacientesFile.createNewFile();
        File doctoresFile = new File (ubicacionProyecto+separator+"db"+separator+"doctores.txt");
        doctoresFile.createNewFile();

        //Strings para referirse a los archivos
        citasString = citasFile.toString();
        doctoresString = doctoresFile.toString();
        pacientesString = pacientesFile.toString();

        //variables para validar credenciales de un administrador
        String admin = "", contrasena = "";
        boolean usuarioValido;

        //obtener administradores
        cargarAdministradores();
        //obtener doctores, pacientes y citas desde los archivos .txt
        cargarDoctores();
        cargarPacientes();
        cargarCitas();


        System.out.println("Bienvenido al Sistema de citas");

        //obtener credenciales
        System.out.println("\nINICIAR SESIÓN");
        System.out.println("Ingrese usuario:");
        admin = scanner.nextLine();
        System.out.println("Ingrese contraseña:");
        contrasena = scanner.nextLine();

        //validar credenciales
        usuarioValido = validarCredenciales(admin, contrasena);

        //muestra el menú si las credenciales son validas
        //si no, solo muestra un mensaje
        if (usuarioValido) {
            menu();
        } else {
            System.out.println("Acceso denegado, revisar credenciales");
        }

    }

    //agrega los administradores al array de administradores
    public static void cargarAdministradores() {
        administradoresArray.add(new Administrador(1, "Victor", "12345"));
        administradoresArray.add(new Administrador(2, "Alfredo", "Sacaste 100"));
    }

    //si ambos parámetros coinciden con los atributos de algún administrador se retorna true
    public static boolean validarCredenciales(String admin, String contrasena) {
        boolean usuarioValido = false;
        for(int i=0; i<administradoresArray.size(); i++) {
            if(administradoresArray.get(i).getNombre().equals(admin) &&
                    administradoresArray.get(i).getPassword().equals(contrasena)) {
                usuarioValido = true;
                break;
            }
        }
        return usuarioValido;
    }

    //imprime el menu al usuario, lee la opción que el usuario elige, hace la acción correspondiente y se repite en un ciclo
    //del 1 al 3 pide los atributos necesarios para después crear un nuevo objeto y agregarlo al array correspondiente
    //si el objeto es una cita, verifica que el doctor y el paciente existan en el registro, si no existen le notificará al usuario
    //y le recomendará crearlo.
    //del 4 al 6 muestra todos los objetos de cierta clase (citas, doctores o pacientes)
    //con el 7 se termina el ciclo, y por lo tanto, termina el programa
    //si se introduce algún otro input le pide al usuario ingresar un input valido
    public static void menu() {
        char seleccion;
        boolean terminar = false;
        int id;
        String nombre, especialidad, fecha, hora, motivo, myDoctorString="", myPacienteString="";
        Doctor myDoctor = null;
        Paciente myPaciente = null;

        System.out.print("Seleccione una opción:");

        while (!terminar) {
            System.out.println("\n1) Agregar un medico" +
                    "\n2) Agregar un paciente" +
                    "\n3) Crear una cita" +
                    "\n4) Ver todas las citas" +
                    "\n5) Ver medicos registrados" +
                    "\n6) Ver pacientes registrados" +
                    "\n7) Salir de la aplicación");
            seleccion = scanner.nextLine().charAt(0);

            switch (seleccion) {
                case '1':
                    System.out.println("Ingrese nuevo id:");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Ingrese nombre:");
                    nombre = scanner.nextLine();
                    System.out.println("Ingrese especialidad:");
                    especialidad = scanner.nextLine();
                    agregarDoctor(id, nombre, especialidad);
                    System.out.println("Medico agregado exitosamente");
                    break;
                case '2':
                    System.out.println("Ingrese nuevo id:");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Ingrese nombre:");
                    nombre = scanner.nextLine();
                    agregarPaciente(id,nombre);
                    System.out.println("Paciente agregado exitosamente");
                    break;
                case '3':
                    System.out.println("Ingrese nuevo id:");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Ingrese motivo de cita:");
                    motivo = scanner.nextLine();
                    System.out.println("Ingrese fecha de la cita:");
                    fecha = scanner.nextLine();
                    System.out.println("Ingrese hora de la cita:");
                    hora = scanner.nextLine();

                    System.out.println("Ingrese el nombre del medico que tomará la cita:");
                    myDoctorString = scanner.nextLine();
                    for(int i=0; i<doctoresArray.size(); i++) {
                        if(doctoresArray.get(i).getNombre().equals(myDoctorString)) {
                            myDoctor = doctoresArray.get(i);
                        }
                    }
                    if (myDoctor == null) {
                        System.out.println("El medico no existe, volviendo al menú principal\n" +
                                "Elija la opción 1 para dar de alta al medico");
                        break;
                    }

                    System.out.println("Ingrese el nombre del paciente que tendrá la cita:");
                    myPacienteString = scanner.nextLine();

                    for(int i=0; i<pacientesArray.size(); i++) {
                        if(pacientesArray.get(i).getNombre().equals(myPacienteString)) {
                            myPaciente = pacientesArray.get(i);
                        }
                    }
                    if (myPaciente == null) {
                        System.out.println("El paciente no existe, volviendo al menú principal\n" +
                                "Elija la opción 2 para dar de alta al paciente");
                        break;
                    }
                    crearCita(id,fecha,hora,motivo,myDoctor,myPaciente);

                    break;
                case '4':
                    verCitas();
                    break;
                case '5':
                    verDoctores();
                    break;
                case '6':
                    verPacientes();
                    break;
                case '7':
                    terminar = true;
                    break;
                default:
                    System.out.println("Introduce una opción valida, que sea un número entre 1 y 7");
            }
        }
    }

    //lee el archivo doctores.txt, convierte cada linea a un objeto de clase doctor y pasa cada objeto al arrayList
    //doctoresArray. También incluye manejo de excepciones por si hay errores con el bufferedReader
    public static void cargarDoctores() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(doctoresString));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] partes = line.split(",");
                doctoresArray.add(new Doctor(Integer.parseInt(partes[0]), partes[1], partes[2]));
            }
        } catch (IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
    }

    //lee el archivo pacientes.txt, convierte cada linea a un objeto de clase paciente y pasa cada objeto al arrayList
    //pacientesArray. También incluye manejo de excepciones por si hay errores con el bufferedReader
    public static void cargarPacientes() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(pacientesString));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] partes = line.split(",");
                pacientesArray.add(new Paciente(Integer.parseInt(partes[0]), partes[1]));
            }
        } catch (IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
    }

    //lee el archivo citas.txt, convierte cada linea a un objeto de clase cita y pasa cada objeto al arrayList
    //citasArray. El .txt incluye el nombre del doctor y el paciente, no una instancia, por lo que mediante ciclos for
    //se encuentran las instancias cuyos nombres coinciden con los del .txt
    //También incluye manejo de excepciones por si hay errores con el bufferedReader
    public static void cargarCitas() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(citasString));
            String line;
            Doctor myDoctor = null;
            Paciente myPaciente = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] partes = line.split(",");
                for(int i=0; i<doctoresArray.size(); i++) {
                    if(doctoresArray.get(i).getNombre().equals(partes[4])) {
                        myDoctor = doctoresArray.get(i);
                    }
                }
                for(int i=0; i<pacientesArray.size(); i++) {
                    if(pacientesArray.get(i).getNombre().equals(partes[5])) {
                        myPaciente = pacientesArray.get(i);
                    }
                }
                citasArray.add(new Cita(Integer.parseInt(partes[0]), partes[1], partes[2], partes[3], myDoctor, myPaciente));
            }
        } catch (IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
    }

    //sobreescribe linea por linea el archivo doctores.txt con cada elemento de doctoresArray
    //Incluye manejo de excepciones por si hubiera algún error con el bufferedWriter
    public static void SaveDoctor() {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(doctoresString));
            String line;
            for (int i=0; i<doctoresArray.size(); i++) {
                line = Integer.toString(doctoresArray.get(i).getId())+","+doctoresArray.get(i).getNombre()+
                ","+doctoresArray.get(i).getEspecialidad();
                bufferedWriter.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
    }

    //sobreescribe linea por linea el archivo pacientes.txt con cada elemento de pacientesArray
    //Incluye manejo de excepciones por si hubiera algún error con el bufferedWriter
    public static void SavePacientes() {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(pacientesString));
            String line;
            for (int i=0; i<pacientesArray.size(); i++) {
                line = Integer.toString(pacientesArray.get(i).getId())+","+pacientesArray.get(i).getNombre();
                bufferedWriter.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
    }

    //sobreescribe linea por linea el archivo citas.txt con cada elemento de citasArray
    //Incluye manejo de excepciones por si hubiera algún error con el bufferedWriter
    public static void SaveCitas() {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(citasString));
            String line;
            for (int i=0; i<citasArray.size(); i++) {
                line = Integer.toString(citasArray.get(i).getId())+","+citasArray.get(i).getFecha()+
                ","+citasArray.get(i).getHora()+","+citasArray.get(i).getMotivo()+","+
                citasArray.get(i).getDoctor().getNombre()+","+citasArray.get(i).getPaciente().getNombre();
                bufferedWriter.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
    }

    //crea un nuevo doctor con los parámetros, lo agrega a doctoresArray y sobreescribe el archivo con SaveDoctor()
    public static void agregarDoctor(int id, String nombre, String especialidad) {
        Doctor myDoctor = new Doctor(id, nombre, especialidad);
        doctoresArray.add(myDoctor);
        SaveDoctor();
    }

    //crea un nuevo paciente con los parámetros, lo agrega a pacientesArray y sobreescribe el archivo con SavePacientes()
    public static void agregarPaciente(int id, String nombre) {
        Paciente myPaciente = new Paciente(id, nombre);
        pacientesArray.add(myPaciente);
        SavePacientes();
    }

    //comprueba que el paciente y el doctor estén disponibles en la fecha y hora establecida y si es así crea una nueva
    //cita con los parámetros, lo agrega a citasArray, sobreescribe el archivo con SaveCitas() y confirma al usuario que se creó la cita
    //si no esta disponible el horario se le notifica al usuario
    public static void crearCita(int id, String fecha, String hora, String motivo, Doctor doctor, Paciente paciente) {
        boolean available = true;
        Cita myCita = new Cita(id, fecha, hora, motivo, doctor, paciente);
        for (int i=0; i<citasArray.size(); i++) {
            available = compararCita(myCita, citasArray.get(i));
            if (available) break;
        }
        if (available) {
            citasArray.add(myCita);
            SaveCitas();
            System.out.println("Cita creada exitosamente");
        } else System.out.println("Medico o paciente no disponible a esa hora, seleccione 4 para ver las citas existentes");
    }

    //si el id del doctor o del paciente es el mismo en ambas citas, se compara la fecha y la hora si son iguales devuelve false,
    //devuelve true de lo contrario
    public static boolean compararCita(Cita c1, Cita c2) {
        boolean available = true;

        if (c1.getDoctor().getId() == c2.getDoctor().getId() || c1.getPaciente().getId() == c1.getPaciente().getId()) {
            if (c1.getFecha().equals(c2.getFecha()) && c1.getHora().equals(c2.getHora())) {
                available = false;
            }
        }
        return available;
    }

    //Imprime todos los atributos de cada cita, para el doctor y el paciente imprime el nombre.
    //Si llegará a ocurrir que se creó una cita sin un paciente o doctor valido (esto solo puede ocurrir si se altera externamente
    //el .txt de doctor o paciente) se imprime que la cita es invalida.
    //Si el arrayList esta vacio se imprime que no hay citas
    public static void verCitas() {
        for (Cita cita : citasArray) {
            if (cita.getPaciente() != null && cita.getDoctor() != null) {
                System.out.println("---------------------------------------------------");
                System.out.println("Id: "+ cita.getId());
                System.out.println("Motivo cita: " + cita.getMotivo());
                System.out.println("Nombre paciente: " + cita.getPaciente().getNombre());
                System.out.println("Nombre medico: " + cita.getDoctor().getNombre());
                System.out.println("Fecha: " + cita.getFecha());
                System.out.println("Hora: " + cita.getHora());
            } else System.out.println("Cita invalida, paciente o doctor asignado no esta en el sistema");
        }

        if (citasArray.isEmpty()) System.out.println("No hay citas");
    }

    //Imprime todos los atributos de cada doctor, si no hay doctores le notifica al usuario
    public static void verDoctores() {
        for (Doctor doctor : doctoresArray) {
            System.out.println("---------------------------------------------------");
            System.out.println("Id: " + doctor.getId());
            System.out.println("Nombre: " + doctor.getNombre());
            System.out.println("Especialidad: " + doctor.getEspecialidad());
        }

        if (doctoresArray.isEmpty()) System.out.println("No hay doctores registrados");

    }

    //Imprime los atributos de cada paciente, si no hay pacientes le notifica al usuario
    public static void verPacientes() {
        for (Paciente paciente : pacientesArray) {
            System.out.println("---------------------------------------------------");
            System.out.println("Id: " + paciente.getId());
            System.out.println("Nombre: " + paciente.getNombre());
        }

        if (pacientesArray.isEmpty()) System.out.println("No hay paciente registrados");
    }
}