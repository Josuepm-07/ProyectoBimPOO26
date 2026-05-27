package trenturistico;

/**
 * CLASE Boleto
 * ============================================================
 * Representa un boleto emitido para un pasajero en una ruta y vagón.
 * 
 * CONCEPTOS USADOS:
 *  - Asociación: Boleto TIENE UN Pasajero, UNA Ruta y UN Vagón.
 *    Estos objetos existen independientemente del Boleto.
 *  - Cálculo en cadena: precio = base + recargo - descuento
 *  - Static counter: un contador estático compartido por TODOS los
 *    objetos para generar IDs únicos automáticamente.
 * ============================================================
 */
public class Boleto {

    // ── CONTADOR ESTÁTICO ──────────────────────────────────
    // static: pertenece a la CLASE, no a cada objeto.
    // Hay UN SOLO contador para TODOS los boletos.
    // Se incrementa cada vez que se crea un boleto nuevo.
    private static int contadorBoletos = 0;

    // ── ATRIBUTOS ──────────────────────────────────────────
    private String   numeroBoleto;  // Identificador único, ej: "BOL-0001"
    private Pasajero pasajero;      // Referencia al objeto Pasajero
    private Ruta     ruta;          // Referencia al objeto Ruta
    private Vagon    vagon;         // Referencia al objeto Vagon
    private int      numeroAsiento; // Número de asiento asignado
    private double   precioFinal;   // Precio calculado (con descuentos y recargos)

    // ── CONSTRUCTOR ────────────────────────────────────────
    /**
     * Al crear el boleto:
     *  1. Se incrementa el contador para generar un ID único.
     *  2. Se registra el asiento en el vagón.
     *  3. Se calcula el precio final automáticamente.
     */
    public Boleto(Pasajero pasajero, Ruta ruta, Vagon vagon) {
        // Incrementar y formatear el número de boleto
        contadorBoletos++;
        // String.format con "%04d": formatea el número con 4 dígitos, ej: 0001, 0002
        this.numeroBoleto  = String.format("BOL-%04d", contadorBoletos);

        this.pasajero      = pasajero;
        this.ruta          = ruta;
        this.vagon         = vagon;

        // Registrar el asiento en el vagón (el método del vagón incrementa ocupados)
        this.numeroAsiento = vagon.registrarAsiento();

        // Calcular el precio al momento de emitir el boleto
        this.precioFinal   = calcularPrecio();
    }

    // ── MÉTODOS DE NEGOCIO ─────────────────────────────────

    /**
     * FÓRMULA DEL PRECIO:
     * 1. Precio base de la ruta (ej: $25.00)
     * 2. + Recargo del vagón si es ejecutivo (30% del precio base)
     * 3. - Descuento del pasajero (% aplicado al precio con recargo)
     *
     * Ejemplo con vagón ejecutivo y tercera edad:
     *   base        = 25.00
     *   + recargo   = 25.00 * 0.30 = 7.50  → subtotal = 32.50
     *   - descuento = 32.50 * 0.50 = 16.25 → FINAL = 16.25
     */
    public double calcularPrecio() {
        double precioBase    = ruta.getPrecioBase();
        double conRecargo    = precioBase * (1 + vagon.getRecargo());
        double descuento     = pasajero.calcularDescuento();
        double precioFinalC  = conRecargo * (1 - descuento);
        // Math.round(...*100)/100.0: redondea a 2 decimales
        return Math.round(precioFinalC * 100.0) / 100.0;
    }

    /**
     * Genera el texto del boleto como si fuera impreso.
     * Usa una línea de guiones para separar secciones visualmente.
     * \n = salto de línea, %-20s = texto alineado en 20 caracteres.
     */
    public String emitirBoleto() {
        String linea = "─".repeat(50);
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(linea).append("\n");
        sb.append("          BOLETO - TREN TURÍSTICO\n");
        sb.append(linea).append("\n");
        sb.append(String.format("  N° Boleto  : %s\n",   numeroBoleto));
        sb.append(String.format("  Pasajero   : %s\n",   pasajero.getNombreCompleto()));
        sb.append(String.format("  C.I.       : %s\n",   pasajero.getCedula()));
        sb.append(String.format("  Tipo       : %s\n",   pasajero.getTipo()));
        sb.append(String.format("  Ruta       : %s\n",   ruta.getNombreRuta()));
        sb.append(String.format("  Salida     : %s\n",   ruta.getHorarioSalida()));
        sb.append(String.format("  Vagón      : %s (%s)\n", vagon.getIdVagon(), vagon.getCategoria()));
        sb.append(String.format("  Asiento    : %d\n",   numeroAsiento));
        sb.append(linea).append("\n");
        sb.append(String.format("  Precio base: $%.2f\n", ruta.getPrecioBase()));
        if (vagon.getRecargo() > 0) {
            sb.append(String.format("  Recargo VIP: +%.0f%% (snacks + guía bilingüe)\n",
                      vagon.getRecargo() * 100));
        }
        if (pasajero.calcularDescuento() > 0) {
            sb.append(String.format("  Descuento  : -%.0f%% (%s)\n",
                      pasajero.calcularDescuento() * 100, pasajero.getTipo()));
        }
        sb.append(String.format("  TOTAL      : $%.2f\n", precioFinal));
        sb.append(linea).append("\n");
        if (vagon.esEjecutivo()) {
            sb.append("  Incluye: snacks y guía bilingüe durante el recorrido.\n");
        }
        return sb.toString();
    }

    // ── GETTERS ────────────────────────────────────────────
    public String   getNumeroBoleto() { return numeroBoleto; }
    public Pasajero getPasajero()     { return pasajero; }
    public Ruta     getRuta()         { return ruta; }
    public Vagon    getVagon()        { return vagon; }
    public int      getNumeroAsiento(){ return numeroAsiento; }
    public double   getPrecioFinal()  { return precioFinal; }

    @Override
    public String toString() {
        return String.format("Boleto %s | Pasajero: %-20s | Ruta: %-10s | Vagón: %s | Asiento: %2d | $%.2f",
               numeroBoleto, pasajero.getNombreCompleto(), ruta.getIdRuta(),
               vagon.getCategoria(), numeroAsiento, precioFinal);
    }
}
