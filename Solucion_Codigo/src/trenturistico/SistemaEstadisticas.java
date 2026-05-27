package trenturistico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CLASE SistemaEstadisticas
 * ============================================================
 * Centraliza el registro de reservas y genera reportes gerenciales.
 * 
 * CONCEPTOS USADOS:
 *  - HashMap<K,V>: estructura de datos que asocia una CLAVE (K) con
 *    un VALOR (V). Como un diccionario: idRuta → lista de ocupaciones.
 *    Búsqueda en O(1): muy eficiente para reportes.
 *  - getOrDefault(): retorna el valor si existe la clave, o un valor
 *    predeterminado si no existe. Evita NullPointerException.
 *  - Cálculo de promedios: sumar y dividir acumulando resultados.
 * ============================================================
 */
public class SistemaEstadisticas {

    // ── ATRIBUTOS ──────────────────────────────────────────
    private List<Reserva>             reservas;
    private List<Ruta>                rutas;
    private List<Tren>                trenes;
    // Mapa: idRuta → Lista de porcentajes de ocupación registrados
    private Map<String, List<Double>> ocupacionPorRuta;

    // ── CONSTRUCTOR ────────────────────────────────────────
    public SistemaEstadisticas() {
        this.reservas         = new ArrayList<>();
        this.rutas            = new ArrayList<>();
        this.trenes           = new ArrayList<>();
        this.ocupacionPorRuta = new HashMap<>();
    }

    // ── REGISTRO ───────────────────────────────────────────

    /**
     * Registra una nueva reserva en el sistema.
     * Al registrar, también actualiza las estadísticas de ocupación
     * para cada vagón del tren en cada ruta usada.
     */
    public void registrarReserva(Reserva reserva) {
        reservas.add(reserva);

        // Actualizar ocupación por ruta para cada boleto
        for (Boleto b : reserva.getBoletos()) {
            String idRuta = b.getRuta().getIdRuta();

            // getOrDefault: si no existe la clave, crea una nueva lista vacía
            // Luego agrega el porcentaje de ocupación actual del vagón
            List<Double> ocupaciones = ocupacionPorRuta.getOrDefault(idRuta, new ArrayList<>());
            ocupaciones.add(b.getVagon().getPorcentajeOcupacion());
            // put: inserta o ACTUALIZA el valor en el mapa
            ocupacionPorRuta.put(idRuta, ocupaciones);
        }
    }

    /**
     * Registra rutas y trenes del sistema (para el reporte).
     */
    public void agregarRuta(Ruta ruta)  { rutas.add(ruta); }
    public void agregarTren(Tren tren)  { trenes.add(tren); }

    // ── ESTADÍSTICAS ───────────────────────────────────────

    /**
     * Suma todos los ingresos de todas las reservas del día.
     */
    public double getIngresosTotal() {
        double total = 0.0;
        for (Reserva r : reservas) {
            // Solo contamos reservas confirmadas
            if (r.getEstado().equals(Reserva.ESTADO_CONFIRMADA)) {
                total += r.getTotalReserva();
            }
        }
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Calcula el porcentaje de ocupación PROMEDIO para una ruta específica.
     * Promedio = suma de valores / cantidad de valores.
     */
    public double getOcupacionPromedioPorRuta(String idRuta) {
        List<Double> ocupaciones = ocupacionPorRuta.get(idRuta);
        if (ocupaciones == null || ocupaciones.isEmpty()) return 0.0;

        double suma = 0.0;
        for (double ocu : ocupaciones) {
            suma += ocu;
        }
        return Math.round((suma / ocupaciones.size()) * 100.0) / 100.0;
    }

    /**
     * Retorna el total de boletos vendidos en todas las reservas confirmadas.
     */
    public int getTotalBoletosVendidos() {
        int total = 0;
        for (Reserva r : reservas) {
            if (r.getEstado().equals(Reserva.ESTADO_CONFIRMADA)) {
                total += r.getBoletos().size();
            }
        }
        return total;
    }

    /**
     * Genera el reporte gerencial completo.
     * Imprime directamente en consola usando System.out.println.
     */
    public void generarReporte() {
        String linea    = "═".repeat(55);
        String lineaFin = "─".repeat(55);
        System.out.println("\n" + linea);
        System.out.println("       REPORTE GERENCIAL — TREN TURÍSTICO");
        System.out.println(linea);

        // ── Sección 1: Ingresos ─────────────────────────
        System.out.println("  INGRESOS DEL DÍA:");
        System.out.printf("    Total recaudado   : $%.2f%n", getIngresosTotal());
        System.out.printf("    Boletos vendidos  : %d%n", getTotalBoletosVendidos());
        System.out.printf("    Reservas totales  : %d%n", reservas.size());
        System.out.println(lineaFin);

        // ── Sección 2: Ocupación por ruta ───────────────
        System.out.println("  OCUPACIÓN PROMEDIO POR RUTA:");
        if (rutas.isEmpty()) {
            System.out.println("    (Sin rutas registradas)");
        } else {
            for (Ruta r : rutas) {
                double prom = getOcupacionPromedioPorRuta(r.getIdRuta());
                // Barra visual de porcentaje con '#' para visualizar nivel
                int barras = (int)(prom / 10);  // Cada '#' = 10%
                String barra = "#".repeat(barras) + ".".repeat(10 - barras);
                System.out.printf("    [%s] %-30s : [%s] %.1f%%%n",
                       r.getIdRuta(), r.getNombreRuta(), barra, prom);
            }
        }
        System.out.println(lineaFin);

        // ── Sección 3: Estado de vagones ─────────────────
        System.out.println("  ESTADO DE VAGONES POR TREN:");
        for (Tren t : trenes) {
            System.out.printf("    Tren: %s%n", t.getNombre());
            for (Vagon v : t.getVagones()) {
                System.out.printf("      └─ %s%n", v.toString());
            }
        }
        System.out.println(linea);
    }

    // ── GETTERS ────────────────────────────────────────────
    public List<Reserva> getReservas() { return reservas; }
    public int getCantidadReservas()   { return reservas.size(); }
}
