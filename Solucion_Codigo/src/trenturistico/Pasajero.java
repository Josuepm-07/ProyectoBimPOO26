package trenturistico;

/**
 * CLASE ABSTRACTA Pasajero
 * ============================================================
 * Clase BASE de la jerarquía de pasajeros.
 * 
 * CONCEPTOS CLAVE:
 *  - abstract class: clase que NO se puede instanciar directamente.
 *    No puedes hacer: new Pasajero(...) — da error de compilación.
 *    Solo sirve como PLANTILLA para las subclases.
 *
 *  - abstract method: método SIN cuerpo (sin llaves { }).
 *    Obliga a todas las subclases a implementarlo.
 *    Así garantizamos que CUALQUIER tipo de pasajero sabrá
 *    calcular su descuento.
 *
 *  - HERENCIA (extends): las subclases heredan TODOS los atributos
 *    y métodos de esta clase. No hay que redefinirlos.
 *
 *  - POLIMORFISMO: una variable de tipo Pasajero puede guardar
 *    un PasajeroAdulto, PasajeroTerceraEdad o PasajeroMenor.
 *    Cuando llamamos a calcularDescuento(), Java ejecuta la versión
 *    CORRECTA según el tipo real del objeto (binding dinámico).
 * ============================================================
 */
public abstract class Pasajero {

    // ── ATRIBUTOS PROTEGIDOS ───────────────────────────────
    // protected: las subclases SÍ pueden acceder directamente.
    // (private no funcionaría desde subclases)
    protected String  cedula;
    protected String  nombre;
    protected String  apellido;
    protected int     edad;
    protected boolean tieneDiscapacidad;

    // Descuento adicional para personas con discapacidad (por ley Ecuador: 25%)
    protected static final double DESCUENTO_DISCAPACIDAD = 0.25;

    // ── CONSTRUCTOR ────────────────────────────────────────
    // Este constructor es llamado por los constructores de las subclases
    // mediante super(...)
    public Pasajero(String cedula, String nombre, String apellido,
                    int edad, boolean tieneDiscapacidad) {
        this.cedula            = cedula;
        this.nombre            = nombre;
        this.apellido          = apellido;
        this.edad              = edad;
        this.tieneDiscapacidad = tieneDiscapacidad;
    }

    // ── MÉTODOS ABSTRACTOS ─────────────────────────────────
    /**
     * Cada tipo de pasajero DEBE implementar su cálculo de descuento.
     * Retorna un valor entre 0.0 (sin descuento) y 1.0 (100% descuento).
     */
    public abstract double calcularDescuento();

    /**
     * Retorna una etiqueta del tipo de pasajero.
     * Ej: "Adulto", "Tercera Edad", "Menor de edad"
     */
    public abstract String getTipo();

    // ── MÉTODOS CONCRETOS (heredados por todas las subclases) ─────
    /**
     * Nombre completo del pasajero.
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    /**
     * Resumen de los datos del pasajero.
     */
    public String getDatos() {
        return String.format("C.I.: %s | %s | Edad: %d | Tipo: %s | Discapacidad: %s",
               cedula, getNombreCompleto(), edad, getTipo(),
               tieneDiscapacidad ? "Sí" : "No");
    }

    // ── GETTERS ────────────────────────────────────────────
    public String  getCedula()            { return cedula; }
    public String  getNombre()            { return nombre; }
    public String  getApellido()          { return apellido; }
    public int     getEdad()              { return edad; }
    public boolean isTieneDiscapacidad()  { return tieneDiscapacidad; }

    @Override
    public String toString() {
        return getDatos();
    }
}


// ═══════════════════════════════════════════════════════════
//  SUBCLASE 1: PasajeroAdulto
//  Pasajeros entre 18 y 64 años sin descuento por edad.
//  Solo aplica descuento si tiene discapacidad.
// ═══════════════════════════════════════════════════════════
class PasajeroAdulto extends Pasajero {

    // Constructor: llama al padre con super(...)
    // super() es obligatorio y debe ser la PRIMERA línea del constructor.
    public PasajeroAdulto(String cedula, String nombre, String apellido,
                          int edad, boolean tieneDiscapacidad) {
        super(cedula, nombre, apellido, edad, tieneDiscapacidad);
    }

    /**
     * @Override indica que estamos SOBREESCRIBIENDO el método abstracto.
     * Si hay discapacidad: 25% descuento.
     * Si no: sin descuento (0%).
     */
    @Override
    public double calcularDescuento() {
        return tieneDiscapacidad ? DESCUENTO_DISCAPACIDAD : 0.0;
    }

    @Override
    public String getTipo() {
        return "Adulto";
    }
}


// ═══════════════════════════════════════════════════════════
//  SUBCLASE 2: PasajeroTerceraEdad
//  Pasajeros de 65 años o más.
//  Descuento del 50% por ley + 25% adicional si tiene discapacidad.
//  (En la práctica se aplica el mayor, pero aquí los sumamos
//   como ejemplo pedagógico; ajustar según normativa vigente.)
// ═══════════════════════════════════════════════════════════
class PasajeroTerceraEdad extends Pasajero {

    // Descuento específico de tercera edad (50% por ley ecuatoriana)
    private static final double DESCUENTO_TERCERA_EDAD = 0.50;

    public PasajeroTerceraEdad(String cedula, String nombre, String apellido,
                               int edad, boolean tieneDiscapacidad) {
        super(cedula, nombre, apellido, edad, tieneDiscapacidad);
    }

    @Override
    public double calcularDescuento() {
        double descuento = DESCUENTO_TERCERA_EDAD;
        if (tieneDiscapacidad) {
            // Se suman los descuentos, con tope de 100%
            descuento = Math.min(1.0, descuento + DESCUENTO_DISCAPACIDAD);
        }
        return descuento;
    }

    @Override
    public String getTipo() {
        return "Tercera Edad";
    }
}


// ═══════════════════════════════════════════════════════════
//  SUBCLASE 3: PasajeroMenor
//  Menores de 18 años.
//  Descuento del 50% por ley + 25% si tiene discapacidad.
// ═══════════════════════════════════════════════════════════
class PasajeroMenor extends Pasajero {

    private static final double DESCUENTO_MENOR = 0.50;

    public PasajeroMenor(String cedula, String nombre, String apellido,
                         int edad, boolean tieneDiscapacidad) {
        super(cedula, nombre, apellido, edad, tieneDiscapacidad);
    }

    @Override
    public double calcularDescuento() {
        double descuento = DESCUENTO_MENOR;
        if (tieneDiscapacidad) {
            descuento = Math.min(1.0, descuento + DESCUENTO_DISCAPACIDAD);
        }
        return descuento;
    }

    @Override
    public String getTipo() {
        return "Menor de edad";
    }
}
