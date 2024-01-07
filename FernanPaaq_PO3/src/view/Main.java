package view;

import com.sun.security.jgss.GSSUtil;
import jdk.jshell.execution.Util;
import models.Administracion;
import models.Envio;
import utils.Utils;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int op, op2, op3, op4, op5, op6, op7, numeroSeguimiento, tlfTeclado, diasEntrega;
        boolean apagado = false, cierraSesion = false, salirModificar = false;
        char respuesta;
        String nombreTeclado, claveTeclado, usuario, provinciaTeclado, localidadTeclado, direccionTeclado, nuevoNombre, nuevaClave, conductor, respuestaEstado, nueva;
        Administracion administracion = new Administracion();
        Envio envioCopia = new Envio();


        do {
            System.out.print("""
                    Bienvenido a FernanPaaq
                    1. Iniciar sesión
                    2. Registrarse
                    3. Salir
                    Elija una opción: """);
            op = Integer.parseInt(scanner.nextLine());
            switch (op) {
                case 1:
                    //Aquí se reseta la variable que controla en encendido del menú
                    cierraSesion = false;
                    respuesta = 'N';
                    //Aquí se resetean el nombre y la clave introducidos
                    nombreTeclado = "";
                    claveTeclado = "";
                    System.out.print("Introduzca el nombre de usuario: ");
                    nombreTeclado = scanner.nextLine();
                    System.out.print("Introduzca la clave de usuario: ");
                    claveTeclado = scanner.nextLine();

                    //En los siguientes if se compruebe si es el administrador, conductor o usuario
                    if (administracion.login(nombreTeclado, claveTeclado).equals("administrador")) {
                        do {
                            System.out.print("""
                                    Bienvenido. Menú de administrador
                                    1. Registrar un nuevo envío
                                    2. Asignar un envío a un conductor
                                    3. Ver los datos de todos los usuarios registrados
                                    4. Ver los datos de todos los envíos
                                    5. Ver los datos de todos los conductores
                                    6. Modificar mi perfil
                                    7. Cerrar sesión
                                    Elija una opción: """);
                            op2 = Integer.parseInt(scanner.nextLine());
                            switch (op2) {
                                case 1:
                                    System.out.print("¿A qué usuario le quieres asignar el envío?: ");
                                    usuario = scanner.nextLine();
                                    if (administracion.comprobacionUsuario(usuario)) {
                                        System.out.print("Introduce el número de seguimiento: ");
                                        numeroSeguimiento = Integer.parseInt(scanner.nextLine());

                                        if (administracion.addEnvio(numeroSeguimiento, usuario))
                                            System.out.println("Envío registrado correctamente");
                                        else System.out.println("No se pueden registrar más envíos");
                                    } else System.out.println("Usuario incorrecto o no es posible realizar envíos");

                                    break;
                                case 2:
                                    System.out.println("""
                                            === Asignación de envíos sin conductor ===""");
                                    System.out.println(administracion.pintaEnviosSinRegistro());
                                    System.out.print("Seleccione el envío a asignar: ");
                                    op6 = Integer.parseInt(scanner.nextLine());
                                    envioCopia = administracion.buscarEnvio(op6);
                                    if (op6 > administracion.cuentaPaquetes()) {
                                        System.out.println("Número introducido incorrecto");
                                    } else {
                                        System.out.println("=== Asignación del paquete " + administracion.buscarNumero(op6) + " ===");
                                        System.out.println(administracion.pintaInfoConductores());
                                        System.out.println("Seleccione el conductor: ");
                                        op7 = Integer.parseInt(scanner.nextLine());
                                        conductor = administracion.buscarConductor(op7, envioCopia);
                                        if (conductor.equals(""))
                                            System.out.println("No se pueden asignar más paquetes");
                                        else {
                                            System.out.print("Asignado a " + conductor + ", indique los días aproximados para realizar la entrega: ");
                                            diasEntrega = Integer.parseInt(scanner.nextLine());
                                            administracion.modificarFechaEnvio(diasEntrega, administracion.buscarNumero(op6));
                                        }
                                    }

                                    break;
                                case 3:
                                    System.out.println(administracion.pintaUsuarios());
                                    Utils.pulsaParaCuntinuar();
                                    break;
                                case 4:
                                    System.out.println(administracion.pintaEnvios());
                                    Utils.pulsaParaCuntinuar();
                                    break;
                                case 5:
                                    System.out.println(administracion.pintaConductores());
                                    Utils.pulsaParaCuntinuar();
                                    break;
                                case 6:
                                    //Se resetea la bandera que controla el menú por si se entra varias veces
                                    salirModificar = false;
                                    nuevoNombre = null;
                                    nombreTeclado = null;
                                    claveTeclado = null;
                                    nuevaClave = null;
                                    do {
                                        System.out.print("""
                                                === Modificar datos de administrador ===
                                                1. Cambiar nombre de administrador
                                                2. Cambiar clave de administrador
                                                3. Salir
                                                Elige la opción deseada: """);
                                        op5 = Integer.parseInt(scanner.nextLine());
                                        switch (op5) {
                                            case 1:
                                                System.out.print("Introduce el nuevo nombre: ");
                                                nuevoNombre = scanner.nextLine();
                                                nombreTeclado = nuevoNombre;
                                                administracion.setNombre(nuevoNombre);
                                                break;
                                            case 2:
                                                System.out.print("Introduce la nueva clave: ");
                                                nuevaClave = scanner.nextLine();
                                                claveTeclado = nuevaClave;
                                                administracion.setClave(nuevaClave);
                                                break;
                                            case 3:
                                                Utils.saliendo();
                                                salirModificar = true;
                                                break;

                                            default:
                                                System.out.println("Opción incorrecta");
                                                Utils.pulsaParaCuntinuar();
                                                break;
                                        }
                                    } while (!salirModificar);

                                    break;
                                case 7:
                                    //Se pregunta si se está seguro de cerrar sesión por si le ha dado por error
                                    System.out.println("¿Está seguro de cerrar la sesión? (Y/N)");
                                    respuesta = scanner.nextLine().toUpperCase().charAt(0);
                                    if (respuesta == 'Y') {
                                        cierraSesion = true;
                                        Utils.cierraSesion();
                                    } else {
                                        cierraSesion = false;
                                        System.out.println("El programa sigue en ejecución");
                                        Utils.pulsaParaCuntinuar();
                                    }
                                    break;

                                default:
                                    break;
                            }

                        } while (!cierraSesion);

                    }
                    if (administracion.login(nombreTeclado, claveTeclado).equals("conductor")) {
                        do {
                            System.out.print("""
                                    Bienvenido. Menú de conductor
                                    1. Ver la infomación de mis envíos
                                    2. Cambiar el estado de un envío
                                    3. Ver el histórico de paquetes entregados
                                    4. Modificar mi perfil
                                    5. Cerrar sesión
                                    Elija una opción: """);
                            op3 = Integer.parseInt(scanner.nextLine());
                            switch (op3) {
                                case 1:
                                    System.out.println(administracion.pintaEnviosConductor(nombreTeclado));
                                    Utils.pulsaParaCuntinuar();
                                    break;
                                case 2:
                                    System.out.println("""
                                            === Actualización del estado de envíos ===""");
                                    System.out.println(administracion.pintaEnviosActualizacion());
                                    System.out.print("Seleccione el envío a modificar: ");
                                    op6 = Integer.parseInt(scanner.nextLine());
                                    System.out.println("=== Estado del pedido " + administracion.buscarNumero(op6) + " ===");
                                    System.out.println("""
                                            1. En oficina de origen
                                            2. En almacén
                                            3. En reparto
                                            4. Entregado""");
                                    System.out.println("Seleccione el estado: ");
                                    respuestaEstado = scanner.nextLine();
                                    if (respuestaEstado.equals("1")) respuestaEstado = "En oficina de origen";
                                    if (respuestaEstado.equals("2")) respuestaEstado = "En almacén";
                                    if (respuestaEstado.equals("3")) respuestaEstado = "En reparto";
                                    if (respuestaEstado.equals("4")) respuestaEstado = "Entregado";
                                    administracion.cambiaEstado(administracion.buscarNumero(op6), respuestaEstado);

                                    System.out.println("Envío " + administracion.buscarNumero(op6) + ": " + respuestaEstado);
                                    break;
                                case 3:
                                    System.out.println(administracion.historicoPaquetes(nombreTeclado));
                                    Utils.pulsaParaCuntinuar();
                                    break;
                                case 4:
                                    //Se resetea la bandera que controla el menú por si se entra varias veces
                                    salirModificar = false;
                                    nuevoNombre = null;
                                    nuevaClave = null;
                                    do {
                                        System.out.print("""
                                                === Modificar datos de conductor ===
                                                1. Cambiar nombre de conductor
                                                2. Cambiar clave de conductor
                                                3. Salir
                                                Elige la opción deseada: """);
                                        op5 = Integer.parseInt(scanner.nextLine());
                                        switch (op5) {
                                            case 1:
                                                System.out.print("Introduce el nuevo nombre: ");
                                                nuevoNombre = scanner.nextLine();
                                                administracion.cambioNombreConductor(nuevoNombre, nombreTeclado);
                                                nombreTeclado = nuevoNombre;
                                                break;
                                            case 2:
                                                System.out.print("Introduce la nueva clave: ");
                                                nuevaClave = scanner.nextLine();
                                                administracion.cambioClaveConductor(nuevaClave, claveTeclado);
                                                claveTeclado = nuevaClave;
                                                break;
                                            case 3:
                                                Utils.saliendo();
                                                salirModificar = true;
                                                break;

                                            default:
                                                System.out.println("Opción incorrecta");
                                                Utils.pulsaParaCuntinuar();
                                                break;
                                        }
                                    } while (!salirModificar);
                                    break;
                                case 5:
                                    //Se pregunta si se está seguro de cerrar sesión por si le ha dado por error
                                    System.out.println("¿Está seguro de cerrar la sesión? (Y/N)");
                                    respuesta = scanner.nextLine().toUpperCase().charAt(0);
                                    if (respuesta == 'Y') {
                                        cierraSesion = true;
                                        Utils.cierraSesion();
                                    } else {
                                        cierraSesion = false;
                                        System.out.println("El programa sigue en ejecución");
                                        Utils.pulsaParaCuntinuar();
                                    }
                                    break;

                                default:
                                    break;
                            }
                        } while (!cierraSesion);

                    }
                    if (administracion.login(nombreTeclado, claveTeclado).equals("usuario")) {
                        do {
                            System.out.print("""
                                    Bienvenido. Menú de usuario
                                    1. Seguir mis envíos
                                    2. Modificar mis datos de entrega para un envío
                                    3. Ver mi perfil
                                    4. Modificar mi perfil
                                    5. Cerrar sesión
                                    Elija una opción: """);
                            op3 = Integer.parseInt(scanner.nextLine());
                            switch (op3) {
                                case 1:
                                    System.out.println(administracion.enviosUsuario(nombreTeclado));
                                    break;
                                case 2:
                                    //Se resetea la bandera que controla el menú por si se entra varias veces
                                    salirModificar = false;

                                    do {
                                        System.out.print("""
                                                === Modificar datos de entrega ===
                                                1. Cambiar dirección
                                                2. Cambiar localidad
                                                3. Cambiar provincia
                                                4. Salir
                                                Elige la opción deseada: """);
                                        op5 = Integer.parseInt(scanner.nextLine());
                                        switch (op5) {
                                            case 1:
                                                System.out.print("Introduce la nueva dirección: ");
                                                nueva = scanner.nextLine();
                                                administracion.cambiarDireccion(nueva, nombreTeclado);
                                                break;
                                            case 2:
                                                System.out.print("Introduce la nueva localidad: ");
                                                nueva = scanner.nextLine();
                                                administracion.cambiarLocalidad(nueva, nombreTeclado);
                                                break;
                                            case 3:
                                                System.out.print("Introduce la nueva provincia: ");
                                                nueva = scanner.nextLine();
                                                administracion.cambiarProvincia(nueva, nombreTeclado);
                                                break;

                                            case 4:
                                                Utils.saliendo();
                                                salirModificar = true;
                                                break;
                                            default:
                                                System.out.println("Opción incorrecta");
                                                Utils.pulsaParaCuntinuar();
                                                break;
                                        }
                                    } while (!salirModificar);
                                    break;
                                case 3:
                                    System.out.println(administracion.pintaDatosUsuario(nombreTeclado));
                                    break;
                                case 4:
                                    //Se resetea la bandera que controla el menú por si se entra varias veces
                                    salirModificar = false;
                                    nuevoNombre = null;
                                    nuevaClave = null;
                                    do {
                                        System.out.print("""
                                                === Modificar datos de usuario ===
                                                1. Cambiar nombre de usuario
                                                2. Cambiar clave de usuario
                                                3. Salir
                                                Elige la opción deseada: """);
                                        op5 = Integer.parseInt(scanner.nextLine());
                                        switch (op5) {
                                            case 1:
                                                System.out.print("Introduce el nuevo nombre: ");
                                                nuevoNombre = scanner.nextLine();
                                                administracion.cambioNombreUsuario(nuevoNombre, nombreTeclado);
                                                nombreTeclado = nuevoNombre;
                                                break;
                                            case 2:
                                                System.out.print("Introduce la nueva clave: ");
                                                nuevaClave = scanner.nextLine();
                                                administracion.cambioClaveUsuario(nuevaClave, claveTeclado);
                                                claveTeclado = nuevaClave;
                                                break;
                                            case 3:
                                                Utils.saliendo();
                                                salirModificar = true;
                                                break;

                                            default:
                                                System.out.println("Opción incorrecta");
                                                Utils.pulsaParaCuntinuar();
                                                break;
                                        }
                                    } while (!salirModificar);
                                    break;
                                case 5:
                                    //Se pregunta si se está seguro de cerrar sesión por si le ha dado por error
                                    System.out.println("¿Está seguro de cerrar la sesión? (Y/N)");
                                    respuesta = scanner.nextLine().toUpperCase().charAt(0);
                                    if (respuesta == 'Y') {
                                        cierraSesion = true;
                                        Utils.cierraSesion();
                                    } else {
                                        cierraSesion = false;
                                        System.out.println("El programa sigue en ejecución");
                                        Utils.pulsaParaCuntinuar();
                                    }
                                    break;

                                default:
                                    break;
                            }
                        } while (!cierraSesion);

                    }
                    if (administracion.login(nombreTeclado, claveTeclado).equals("")) {
                        System.out.println("Nombre o clave incorrectos");
                        Utils.pulsaParaCuntinuar();
                    }
                    break;
                case 2:
                    //Aquí se resetean el nombre y la clave introducidos
                    nombreTeclado = "";
                    claveTeclado = "";
                    direccionTeclado = "";
                    localidadTeclado = "";
                    provinciaTeclado = "";
                    if (administracion.numeroUsuarios() < 2) {
                        System.out.print("Introduzca el nombre de usuario: ");
                        nombreTeclado = scanner.nextLine();
                        System.out.print("Introduzca la clave de usuario: ");
                        claveTeclado = scanner.nextLine();
                        System.out.print("Introduzca la dirección del usuario: ");
                        direccionTeclado = scanner.nextLine();
                        System.out.print("Introduzca la localidad del usuario: ");
                        localidadTeclado = scanner.nextLine();
                        System.out.print("Introduzca la provincia del usuario: ");
                        provinciaTeclado = scanner.nextLine();
                        System.out.print("Introduzca el teléfono del usuario: ");
                        tlfTeclado = Integer.parseInt(scanner.nextLine());

                        //Aquí se añade un nuevo usuario, con el método utilizado también se comprueba si ya hay 2 usuarios registrados
                        if (administracion.addUsuario(nombreTeclado, claveTeclado, direccionTeclado, localidadTeclado, provinciaTeclado, tlfTeclado))
                            System.out.println("Usuario registrado correctamente");
                    } else {
                        System.out.println("No quedan usuarios disponibles");
                    }

                    Utils.pulsaParaCuntinuar();
                    break;
                case 3:
                    //Se pregunta si se está seguro de cerrar sesión por si le ha dado por error
                    System.out.println("¿Está seguro de cerrar la sesión? (Y/N)");
                    respuesta = scanner.nextLine().toUpperCase().charAt(0);
                    if (respuesta == 'Y') {
                        apagado = true;
                        Utils.apagando();
                    } else {
                        apagado = false;
                        System.out.println("El programa sigue en ejecución");
                        Utils.pulsaParaCuntinuar();
                    }
                    break;

                default:
                    System.out.println("Opción introducida incorrecta");
                    Utils.pulsaParaCuntinuar();
                    break;
            }
        } while (!apagado);
    }
}
