package trenturistico;

import java.util.ArrayList;
import java.util.List;

/**
 * CLASE Tren
 * ============================================================
 * Agrupa los vagones de un tren turístico.
 * 
 * CONCEPTOS USADOS:
 *  - Composición: el Tren TIENE UNA LISTA de Vagones.
 *    Si el Tren se elimina, sus Vagones también desaparecen.
 *    Esto es diferente a Asociación (donde los objetos viven
 *    independientemente).
 *  - List<Vagon>: lista genérica. El <Vagon> entre <> indica que
 *    SOLO acepta objetos de tipo Vagon (type-safety de Java).
 *  - Búsqueda lineal: recorrer la lista hasta encontrar el elemento.
 * ============================================================
 */
public class Tren {

    // ── ATRIBUTOS ──────────────────────────────────────────
    private String       idTren;   // Código único, ej: "T001"
    private String       nombre;   // Nombre del tren, ej: "El Expreso del Chimborazo"
    private List<Vagon>  vagones;  // Lista de vagones que componen el tren

    // ── CONSTRUCTOR ────────────────────────────────────────
    public Tren(String idTren, String nombre) {
        this.idTren  = idTren;
        this.nombre  = nombre;
        this.vagones = new ArrayList<>();  // Lista vacía al inicio
    }

    // ── MÉTODOS DE NEGOCIO ─────────────────────────────────

    /**
     * Agrega un vagón al tren.
     * Ej: tren.agregarVagon(new Vagon("V001", "Turista", 20));
     */
    public void agregarVagon(Vagon vagon) {
        vagones.add(vagon);
    }

    /**
     * Busca un vagón por su ID.
     * Recorre la lista con un for-each y compara IDs.
     * Retorna el Vagon si lo encuentra, o NULL si no existe.
     * 
     * IMPORTANTE: siempre verificar que el resultado no sea null
     * antes de usarlo, para evitar NullPointerException.
     */
    public Vagon getVagonPorId(String idVagon) {
        for (Vagon v : vagones) {
            // equals(): compara contenido de String (no dirección de memoria)
            if (v.getIdVagon().equals(idVagon)) {
                return v;  // Encontrado: retornamos inmediatamente
            }
        }
        return null;  // No encontrado
    }

    /**
     * Suma la capacidad total de TODOS los vagones del tren.
     */
    public int getCapacidadTotal() {
        int total = 0;
        for (Vagon v : vagones) {
            total += v.getCapacidad();  // += suma acumulada
        }
        return total;
    }

    /**
     * Suma todos los asientos ocupados en todos los vagones.
     */
    public int getTotalAsientosOcupados() {
        int total = 0;
        for (Vagon v : vagones) {
            total += v.getAsientosOcupados();
        }
        return total;
    }

    /**
     * Porcentaje de ocupación general del tren completo.
     */
    public double getPorcentajeOcupacionTotal() {
        int cap = getCapacidadTotal();
        if (cap == 0) return 0.0;
        return ((double) getTotalAsientosOcupados() / cap) * 100;
    }

    // ── GETTERS ────────────────────────────────────────────
    public String       getIdTren()  { return idTren; }
    public String       getNombre()  { return nombre; }
    public List<Vagon>  getVagones() { return vagones; }

    /**
     * toString() mejorado: muestra el tren Y todos sus vagones.
     * Se usa un StringBuilder para construir el texto con saltos de línea.
     * \n = salto de línea (newline)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Tren [%s] %s | Capacidad total: %d asientos\n",
                  idTren, nombre, getCapacidadTotal()));
        for (Vagon v : vagones) {
            sb.append("  └─ ").append(v.toString()).append("\n");
        }
        return sb.toString();
    }
}
