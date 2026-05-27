package trenturistico;

import java.util.ArrayList;
import java.util.List;

/**
 * CLASE Reserva
 * ============================================================
 * Agrupa uno o más boletos bajo una misma transacción de reserva.
 * 
 * CONCEPTOS USADOS:
 *  - Composición fuerte: los Boletos PERTENECEN a esta Reserva.
 *  - Enum simulado con constantes: ESTADOS posibles de la reserva.
 *  - Iteración y acumulación: sumar precios de una lista.
 *  - Contador estático: para generar IDs únicos de reserva.
 * ============================================================
 */
public class Reserva {

    // ── CONSTANTES DE ESTADO ───────────────────────────────
    public static final String ESTADO_PENDIENTE  = "Pendiente";
    public static final String ESTADO_CONFIRMADA = "Confirmada";
    public static final String ESTADO_CANCELADA  = "Cancelada";

    // ── CONTADOR ESTÁTICO ──────────────────────────────────
    private static int contadorReservas = 0;

    // ── ATRIBUTOS ──────────────────────────────────────────
    private String       idReserva;  // Código único, ej: "RES-0001"
    private String       fecha;      // Fecha de la reserva, ej: "2025-07-15"
    private List<Boleto> boletos;    // Lista de boletos incluidos
    private String       estado;     // Estado actual de la reserva
    private Tren         tren;       // Tren asignado a esta reserva

    // ── CONSTRUCTOR ────────────────────────────────────────
    public Reserva(String fecha, Tren tren) {
        contadorReservas++;
        this.idReserva = String.format("RES-%04d", contadorReservas);
        this.fecha     = fecha;
        this.tren      = tren;
        this.boletos   = new ArrayList<>();
        this.estado    = ESTADO_PENDIENTE;  // Siempre empieza como pendiente
    }

    // ── MÉTODOS DE NEGOCIO ─────────────────────────────────

    /**
     * Agrega un boleto ya creado a esta reserva.
     */
    public void agregarBoleto(Boleto boleto) {
        boletos.add(boleto);
    }

    /**
     * Suma el precio de todos los boletos de la reserva.
     * Es el costo total que el cliente debe pagar.
     */
    public double getTotalReserva() {
        double total = 0.0;
        for (Boleto b : boletos) {
            total += b.getPrecioFinal();  // Acumulamos precio de cada boleto
        }
        return Math.round(total * 100.0) / 100.0;  // Redondear a 2 decimales
    }

    /**
     * Cambia el estado a "Confirmada".
     * Solo se puede confirmar si tiene al menos un boleto.
     */
    public void confirmarReserva() {
        if (boletos.isEmpty()) {
            System.out.println("⚠ No se puede confirmar: la reserva no tiene boletos.");
            return;
        }
        this.estado = ESTADO_CONFIRMADA;
        System.out.println("✓ Reserva " + idReserva + " confirmada exitosamente.");
    }

    /**
     * Cancela la reserva.
     */
    public void cancelarReserva() {
        this.estado = ESTADO_CANCELADA;
    }

    // ── GETTERS ────────────────────────────────────────────
    public String       getIdReserva() { return idReserva; }
    public String       getFecha()     { return fecha; }
    public List<Boleto> getBoletos()   { return boletos; }
    public String       getEstado()    { return estado; }
    public Tren         getTren()      { return tren; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Reserva [%s] | Fecha: %s | Tren: %s | Estado: %s | Total: $%.2f\n",
               idReserva, fecha, tren.getNombre(), estado, getTotalReserva()));
        for (Boleto b : boletos) {
            sb.append("  └─ ").append(b.toString()).append("\n");
        }
        return sb.toString();
    }
}
