package trenturistico;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CLASE PRINCIPAL: SistemaTrenTuristico
 * ============================================================
 * Punto de entrada del programa. Contiene el método main()
 * y el menú interactivo por consola.
 * 
 * CONCEPTOS USADOS:
 *  - Scanner: clase de Java para leer entrada del teclado.
 *    System.in = entrada estándar (teclado).
 *  - switch-case: estructura de selección múltiple. Más legible
 *    que muchos if-else cuando se compara una sola variable.
 *  - Bucle do-while: ejecuta el cuerpo AL MENOS UNA VEZ, luego
 *    repite mientras la condición sea verdadera. Ideal para menús.
 *  - try-catch: manejo de excepciones. Si el usuario escribe texto
 *    donde se espera un número, capturamos el error sin que el
 *    programa explote.
 *  - Método main: punto de arranque de TODO programa Java.
 *    Debe ser: public static void main(String[] args)
 * ============================================================
 */
public class SistemaTrenTuristico {

    // ── ATRIBUTOS DEL SISTEMA ──────────────────────────────
    private List<Ruta>          rutas;
    private List<Tren>          trenes;
    private SistemaEstadisticas estadisticas;
    private Scanner             scanner;

    // ── CONSTRUCTOR ────────────────────────────────────────
    public SistemaTrenTuristico() {
        this.rutas         = new ArrayList<>();
        this.trenes        = new ArrayList<>();
        this.estadisticas  = new SistemaEstadisticas();
        // Creamos el Scanner una sola vez; lo cerramos al salir
        this.scanner       = new Scanner(System.in);
    }

    // ═══════════════════════════════════════════════════════
    //  MÉTODO MAIN — Punto de entrada del programa
    // ═══════════════════════════════════════════════════════
    public static void main(String[] args) {
        SistemaTrenTuristico sistema = new SistemaTrenTuristico();
        sistema.inicializarDatos();  // Carga datos de ejemplo
        sistema.ejecutarMenu();      // Lanza el menú interactivo
    }

    // ═══════════════════════════════════════════════════════
    //  INICIALIZACIÓN DE DATOS DE PRUEBA
    // ═══════════════════════════════════════════════════════
    /**
     * Crea rutas, trenes y vagones de ejemplo para demostrar el sistema.
     * En un sistema real, estos datos vendrían de una base de datos.
     */
    private void inicializarDatos() {
        System.out.println("Inicializando sistema...\n");

        // ── Crear Rutas ─────────────────────────────────
        Ruta ruta1 = new Ruta("R001", "Riobamba", "Nariz del Diablo",
                              "08:00", 3.5, 25.00);
        ruta1.agregarParada("Alausí");

        Ruta ruta2 = new Ruta("R002", "Quito", "Otavalo",
                              "09:00", 2.0, 18.00);
        ruta2.agregarParada("Calderón");

        Ruta ruta3 = new Ruta("R003", "Cuenca", "Huigra",
                              "07:30", 4.0, 30.00);
        ruta3.agregarParada("Azogues");
        ruta3.agregarParada("Biblián");

        rutas.add(ruta1);
        rutas.add(ruta2);
        rutas.add(ruta3);

        // ── Crear Trenes con Vagones ─────────────────────
        // Tren 1: "El Expreso del Chimborazo"
        Tren tren1 = new Tren("T001", "El Expreso del Chimborazo");
        tren1.agregarVagon(new Vagon("V001", Vagon.CATEGORIA_TURISTA,   20));
        tren1.agregarVagon(new Vagon("V002", Vagon.CATEGORIA_TURISTA,   20));
        tren1.agregarVagon(new Vagon("V003", Vagon.CATEGORIA_EJECUTIVA, 10));

        // Tren 2: "El Cóndor Andino"
        Tren tren2 = new Tren("T002", "El Cóndor Andino");
        tren2.agregarVagon(new Vagon("V004", Vagon.CATEGORIA_TURISTA,   25));
        tren2.agregarVagon(new Vagon("V005", Vagon.CATEGORIA_EJECUTIVA, 10));

        // Tren 3: "El Tren del Austro"
        Tren tren3 = new Tren("T003", "El Tren del Austro");
        tren3.agregarVagon(new Vagon("V006", Vagon.CATEGORIA_TURISTA,   30));
        tren3.agregarVagon(new Vagon("V007", Vagon.CATEGORIA_EJECUTIVA, 15));

        trenes.add(tren1);
        trenes.add(tren2);
        trenes.add(tren3);

        // Registrar en estadísticas para el reporte
        for (Ruta r : rutas)  estadisticas.agregarRuta(r);
        for (Tren t : trenes) estadisticas.agregarTren(t);

        System.out.println("✓ Sistema inicializado con " + rutas.size() +
                           " rutas y " + trenes.size() + " trenes.\n");
    }

    // ═══════════════════════════════════════════════════════
    //  MENÚ PRINCIPAL
    // ═══════════════════════════════════════════════════════
    private void ejecutarMenu() {
        int opcion = -1;

        // do-while: el menú se muestra al menos una vez
        // y se repite hasta que el usuario elija salir (opcion 0)
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Seleccione una opción: ");

            // switch-case: según la opción, ejecuta el caso correspondiente
            switch (opcion) {
                case 1: verRutas();            break;
                case 2: verTrenes();           break;
                case 3: realizarReserva();     break;
                case 4: estadisticas.generarReporte(); break;
                case 0:
                    System.out.println("\n¡Hasta pronto! Sistema cerrado.");
                    scanner.close();  // Liberar recurso del Scanner
                    break;
                default:
                    System.out.println("⚠ Opción no válida. Intente de nuevo.\n");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuPrincipal() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   SISTEMA TREN TURÍSTICO — MENÚ      ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. Ver rutas disponibles            ║");
        System.out.println("║  2. Ver trenes y vagones             ║");
        System.out.println("║  3. Realizar una reserva             ║");
        System.out.println("║  4. Ver reporte gerencial            ║");
        System.out.println("║  0. Salir                            ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    // ═══════════════════════════════════════════════════════
    //  OPCIÓN 1: VER RUTAS
    // ═══════════════════════════════════════════════════════
    private void verRutas() {
        System.out.println("\n──────── RUTAS DISPONIBLES ────────");
        for (int i = 0; i < rutas.size(); i++) {
            // i+1 para mostrar numeración desde 1 (no desde 0)
            System.out.printf("  %d. %s%n", i + 1, rutas.get(i));
        }
        System.out.println();
    }

    // ═══════════════════════════════════════════════════════
    //  OPCIÓN 2: VER TRENES
    // ═══════════════════════════════════════════════════════
    private void verTrenes() {
        System.out.println("\n──────── TRENES Y VAGONES ────────");
        for (Tren t : trenes) {
            System.out.println(t);  // Llama a toString() de Tren
        }
    }

    // ═══════════════════════════════════════════════════════
    //  OPCIÓN 3: REALIZAR RESERVA
    // ═══════════════════════════════════════════════════════
    private void realizarReserva() {
        System.out.println("\n──────── NUEVA RESERVA ────────");

        // Paso 1: Seleccionar Ruta
        verRutas();
        int indiceRuta = leerEntero("Seleccione número de ruta: ") - 1;
        if (indiceRuta < 0 || indiceRuta >= rutas.size()) {
            System.out.println("⚠ Ruta inválida.\n");
            return;
        }
        Ruta rutaSeleccionada = rutas.get(indiceRuta);

        // Paso 2: Seleccionar Tren
        verTrenes();
        int indiceTren = leerEntero("Seleccione número de tren: ") - 1;
        if (indiceTren < 0 || indiceTren >= trenes.size()) {
            System.out.println("⚠ Tren inválido.\n");
            return;
        }
        Tren trenSeleccionado = trenes.get(indiceTren);

        // Paso 3: Crear la Reserva
        System.out.print("Ingrese la fecha de viaje (ej: 2025-07-20): ");
        String fecha = scanner.nextLine().trim();
        Reserva reserva = new Reserva(fecha, trenSeleccionado);

        // Paso 4: Agregar Pasajeros y Boletos
        int cantPasajeros = leerEntero("¿Cuántos pasajeros? ");
        if (cantPasajeros <= 0) {
            System.out.println("⚠ Debe haber al menos 1 pasajero.\n");
            return;
        }

        for (int i = 1; i <= cantPasajeros; i++) {
            System.out.println("\n  --- Pasajero " + i + " ---");
            Pasajero p = ingresarPasajero();
            if (p == null) continue;

            // Seleccionar vagón
            System.out.println("  Vagones disponibles:");
            List<Vagon> vagones = trenSeleccionado.getVagones();
            for (int j = 0; j < vagones.size(); j++) {
                Vagon v = vagones.get(j);
                System.out.printf("    %d. %s%n", j + 1, v);
            }
            int indVagon = leerEntero("  Seleccione vagón: ") - 1;
            if (indVagon < 0 || indVagon >= vagones.size()) {
                System.out.println("  ⚠ Vagón inválido.");
                continue;
            }
            Vagon vagonSeleccionado = vagones.get(indVagon);

            // Verificar disponibilidad antes de crear el boleto
            if (!vagonSeleccionado.hayDisponibilidad()) {
                System.out.println("  ⚠ El vagón está lleno. Elija otro.");
                continue;
            }

            // Crear el boleto y agregarlo a la reserva
            Boleto boleto = new Boleto(p, rutaSeleccionada, vagonSeleccionado);
            reserva.agregarBoleto(boleto);

            // Imprimir el boleto generado
            System.out.println(boleto.emitirBoleto());
        }

        // Paso 5: Confirmar la reserva
        if (!reserva.getBoletos().isEmpty()) {
            reserva.confirmarReserva();
            estadisticas.registrarReserva(reserva);
            System.out.printf("%nTotal a pagar por la reserva: $%.2f%n%n",
                              reserva.getTotalReserva());
        } else {
            System.out.println("⚠ Reserva sin boletos. No se registró.\n");
        }
    }

    // ═══════════════════════════════════════════════════════
    //  MÉTODO AUXILIAR: ingresar datos de un pasajero
    // ═══════════════════════════════════════════════════════
    /**
     * Lee los datos de un pasajero desde consola y crea el objeto
     * del subtipo correcto según la edad.
     * Retorna null si hay algún error de entrada.
     */
    private Pasajero ingresarPasajero() {
        System.out.print("  Cédula       : ");
        String cedula = scanner.nextLine().trim();

        System.out.print("  Nombre       : ");
        String nombre = scanner.nextLine().trim();

        System.out.print("  Apellido     : ");
        String apellido = scanner.nextLine().trim();

        int edad = leerEntero("  Edad         : ");
        if (edad < 0 || edad > 120) {
            System.out.println("  ⚠ Edad inválida.");
            return null;
        }

        System.out.print("  ¿Discapacidad? (s/n): ");
        // .equalsIgnoreCase: acepta 's', 'S', 'si', 'SI', etc.
        boolean discapacidad = scanner.nextLine().trim().equalsIgnoreCase("s");

        // POLIMORFISMO EN ACCIÓN:
        // Según la edad, creamos el SUBTIPO correcto de Pasajero.
        // La variable 'p' es de tipo Pasajero pero apunta a una subclase.
        Pasajero p;
        if (edad < 18) {
            p = new PasajeroMenor(cedula, nombre, apellido, edad, discapacidad);
        } else if (edad >= 65) {
            p = new PasajeroTerceraEdad(cedula, nombre, apellido, edad, discapacidad);
        } else {
            p = new PasajeroAdulto(cedula, nombre, apellido, edad, discapacidad);
        }

        System.out.printf("  ✓ Registrado como: %s (Descuento: %.0f%%)%n",
                          p.getTipo(), p.calcularDescuento() * 100);
        return p;
    }

    // ═══════════════════════════════════════════════════════
    //  MÉTODO AUXILIAR: leer entero con manejo de excepciones
    // ═══════════════════════════════════════════════════════
    /**
     * Lee un número entero del teclado.
     * Si el usuario escribe texto, captura la excepción y pide de nuevo.
     * 
     * try { } catch (TipoExcepcion e) { }:
     *   - try: bloque que puede generar error.
     *   - catch: qué hacer si ocurre ese error específico.
     *   - NumberFormatException: error cuando Integer.parseInt() no puede
     *     convertir un String a número (ej: "hola" → excepción).
     */
    private int leerEntero(String mensaje) {
        while (true) {  // Bucle infinito: repite hasta obtener un número válido
            try {
                System.out.print(mensaje);
                String linea = scanner.nextLine().trim();
                return Integer.parseInt(linea);  // Puede lanzar NumberFormatException
            } catch (NumberFormatException e) {
                System.out.println("  ⚠ Por favor ingrese un número entero.");
            }
        }
    }
}
