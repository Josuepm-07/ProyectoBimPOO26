package trenturistico;

import java.util.ArrayList;
import java.util.List;

/**
 * CLASE Ruta
 * ============================================================
 * Representa un trayecto del tren turístico.
 * 
 * CONCEPTOS USADOS:
 *  - Encapsulamiento: todos los atributos son PRIVADOS (private).
 *    Solo se accede a ellos mediante getters/setters.
 *  - ArrayList<String>: lista dinámica para guardar las paradas
 *    intermedias. Se usa ArrayList porque no sabemos de antemano
 *    cuántas paradas tendrá cada ruta.
 *  - Constructor: método especial que inicializa el objeto al crearlo.
 *    Tiene el MISMO nombre que la clase y sin tipo de retorno.
 * ============================================================
 */
public class Ruta {

    // ── ATRIBUTOS (estado del objeto) ──────────────────────
    // private: solo esta clase puede leer/modificar el valor directamente.
    private String  idRuta;          // Código único, ej: "R001"
    private String  origen;          // Ciudad de salida, ej: "Riobamba"
    private String  destino;         // Ciudad de llegada, ej: "Nariz del Diablo"
    private List<String> paradas;    // Paradas intermedias (puede haber 0 o más)
    private String  horarioSalida;   // Hora de salida, ej: "08:00"
    private double  duracionHoras;   // Cuántas horas dura el viaje, ej: 3.5
    private double  precioBase;      // Precio base sin recargo ni descuento, ej: 25.00

    // ── CONSTRUCTOR ────────────────────────────────────────
    /**
     * Constructor CON parámetros.
     * Cuando escribimos: Ruta r = new Ruta("R001", "Riobamba", ...)
     * Java llama a este constructor y asigna los valores.
     * 
     * 'this.nombreAtributo' distingue el atributo del objeto
     * del parámetro del constructor que tiene el mismo nombre.
     */
    public Ruta(String idRuta, String origen, String destino,
                String horarioSalida, double duracionHoras, double precioBase) {
        this.idRuta         = idRuta;
        this.origen         = origen;
        this.destino        = destino;
        this.horarioSalida  = horarioSalida;
        this.duracionHoras  = duracionHoras;
        this.precioBase     = precioBase;
        // Inicializamos la lista vacía; se llenan con agregarParada()
        this.paradas        = new ArrayList<>();
    }

    // ── MÉTODOS DE NEGOCIO ─────────────────────────────────

    /**
     * Agrega una parada intermedia a la ruta.
     * Ej: ruta.agregarParada("Alausí");
     */
    public void agregarParada(String parada) {
        paradas.add(parada);  // .add() es el método de ArrayList para insertar
    }

    /**
     * Devuelve el nombre completo del trayecto.
     * Ej: "Riobamba → Alausí → Nariz del Diablo"
     * 
     * StringBuilder: más eficiente que concatenar Strings con '+' en un bucle.
     * append(): va pegando texto al final sin crear nuevos objetos String.
     */
    public String getNombreRuta() {
        StringBuilder sb = new StringBuilder();
        sb.append(origen);
        // Si hay paradas, las agrega en el medio
        for (String parada : paradas) {   // for-each: recorre cada elemento
            sb.append(" → ").append(parada);
        }
        sb.append(" → ").append(destino);
        return sb.toString();
    }

    // ── GETTERS (acceso de solo lectura) ───────────────────
    // Regla de encapsulamiento: nunca exponemos el atributo directamente.
    // Así controlamos quién lee y qué se puede modificar.
    public String getIdRuta()         { return idRuta; }
    public String getOrigen()         { return origen; }
    public String getDestino()        { return destino; }
    public List<String> getParadas()  { return paradas; }
    public String getHorarioSalida()  { return horarioSalida; }
    public double getDuracionHoras()  { return duracionHoras; }
    public double getPrecioBase()     { return precioBase; }

    /**
     * toString(): Método heredado de Object, aquí lo SOBREESCRIBIMOS
     * (Override). Cuando hacemos System.out.println(ruta), Java
     * llama automáticamente a este método para convertir el objeto
     * en texto legible.
     * 
     * String.format(): Formatea texto con marcadores de posición.
     * %s = String, %.2f = double con 2 decimales.
     */
    @Override
    public String toString() {
        return String.format(
            "Ruta [%s] %s | Salida: %s | Duración: %.1f h | Precio base: $%.2f",
            idRuta, getNombreRuta(), horarioSalida, duracionHoras, precioBase
        );
    }
}
