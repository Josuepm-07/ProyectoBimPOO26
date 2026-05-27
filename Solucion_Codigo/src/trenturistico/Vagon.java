package trenturistico;

/**
 * CLASE Vagon
 * ============================================================
 * Representa un vagón del tren con su categoría y ocupación.
 * 
 * CONCEPTOS USADOS:
 *  - Constantes (static final): valores que NUNCA cambian.
 *    Se definen con 'static final' en MAYÚSCULAS por convención.
 *    'static' significa que pertenecen a la CLASE, no a un objeto.
 *  - Validación de datos: antes de aceptar un valor, verificamos
 *    que sea correcto (ej: categoría válida).
 *  - Cálculo de porcentaje: aritmética básica dentro del objeto.
 * ============================================================
 */
public class Vagon {

    // ── CONSTANTES DE CATEGORÍA ────────────────────────────
    // static final: constante de clase. Todos los vagones comparten
    // estos valores; no tiene sentido que cada objeto tenga su copia.
    public static final String CATEGORIA_TURISTA   = "Turista";
    public static final String CATEGORIA_EJECUTIVA = "Ejecutiva";

    // Recargo por categoría ejecutiva (30% adicional al precio base)
    public static final double RECARGO_EJECUTIVA = 0.30;

    // ── ATRIBUTOS ──────────────────────────────────────────
    private String idVagon;           // Código único del vagón, ej: "V001"
    private String categoria;         // "Turista" o "Ejecutiva"
    private int    capacidad;         // Total de asientos del vagón
    private int    asientosOcupados;  // Cuántos asientos ya están vendidos
    private double recargo;           // Factor de recargo (0.0 o 0.30)

    // ── CONSTRUCTOR ────────────────────────────────────────
    public Vagon(String idVagon, String categoria, int capacidad) {
        this.idVagon          = idVagon;
        this.capacidad        = capacidad;
        this.asientosOcupados = 0;  // Empieza en 0; aumenta con cada venta

        // Validamos la categoría y asignamos el recargo correspondiente
        // equalsIgnoreCase: compara ignorando mayúsculas/minúsculas
        if (CATEGORIA_EJECUTIVA.equalsIgnoreCase(categoria)) {
            this.categoria = CATEGORIA_EJECUTIVA;
            this.recargo   = RECARGO_EJECUTIVA;  // 30% extra
        } else {
            // Si no es ejecutiva, por defecto es Turista sin recargo
            this.categoria = CATEGORIA_TURISTA;
            this.recargo   = 0.0;
        }
    }

    // ── MÉTODOS DE NEGOCIO ─────────────────────────────────

    /**
     * Verifica si hay asientos disponibles.
     * Retorna TRUE si asientosOcupados < capacidad total.
     */
    public boolean hayDisponibilidad() {
        return asientosOcupados < capacidad;
    }

    /**
     * Registra un asiento como ocupado (cuando se vende un boleto).
     * Solo lo hace si hay disponibilidad para evitar sobrepasar la capacidad.
     * 
     * Retorna el número de asiento asignado (1, 2, 3...) o -1 si está lleno.
     */
    public int registrarAsiento() {
        if (!hayDisponibilidad()) {
            return -1;  // -1 es señal de error/sin disponibilidad
        }
        asientosOcupados++;           // Incremento: asientosOcupados = asientosOcupados + 1
        return asientosOcupados;      // El número de asiento es la posición ocupada
    }

    /**
     * Calcula el porcentaje de ocupación del vagón.
     * Fórmula: (ocupados / capacidad) * 100
     * 
     * (double): cast (conversión de tipo). 
     * Si no lo hacemos, Java haría DIVISIÓN ENTERA: 3/10 = 0 (no 0.3).
     * Con el cast: (double)3/10 = 0.3
     */
    public double getPorcentajeOcupacion() {
        if (capacidad == 0) return 0.0;  // Evitar división entre cero
        return ((double) asientosOcupados / capacidad) * 100;
    }

    /**
     * Devuelve el factor de recargo para aplicar al precio del boleto.
     * Turista → 0.0 (sin recargo)
     * Ejecutiva → 0.30 (30% extra)
     */
    public double getRecargo() {
        return recargo;
    }

    /**
     * Indica si el vagón es ejecutivo (para mostrar beneficios).
     */
    public boolean esEjecutivo() {
        return CATEGORIA_EJECUTIVA.equals(categoria);
    }

    // ── GETTERS ────────────────────────────────────────────
    public String getIdVagon()          { return idVagon; }
    public String getCategoria()        { return categoria; }
    public int    getCapacidad()        { return capacidad; }
    public int    getAsientosOcupados() { return asientosOcupados; }
    public int    getAsientosLibres()   { return capacidad - asientosOcupados; }

    @Override
    public String toString() {
        return String.format(
            "Vagón [%s] Categoría: %-10s | Capacidad: %2d | Ocupados: %2d | " +
            "Libres: %2d | Ocupación: %.1f%%",
            idVagon, categoria, capacidad, asientosOcupados,
            getAsientosLibres(), getPorcentajeOcupacion()
        );
    }
}
