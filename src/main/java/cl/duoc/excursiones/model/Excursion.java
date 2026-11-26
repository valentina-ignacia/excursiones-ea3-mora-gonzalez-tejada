package cl.duoc.excursiones.model;

/**
 * Datos comunes de todas las excursiones.
 * <p>
 * El identificador (id) vale 0 mientras la excursión no ha sido almacenada.
 * Los métodos set rechazan los datos inválidos con una IllegalArgumentException
 * cuyo mensaje explica el motivo.
 */
public abstract class Excursion {

    private int id;
    private String codigo;
    private int participantes;
    private double distanciaKm;

    protected Excursion(String codigo, int participantes, double distanciaKm) {
        setCodigo(codigo);
        setParticipantes(participantes);
        setDistanciaKm(distanciaKm);
    }

    /** @return identificador (0 si aún no ha sido almacenada). */
    public int getId() {
        return id;
    }

    /** @param id identificador asignado al almacenar la excursión (no negativo). */
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El identificador no puede ser negativo.");
        }
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }
        this.codigo = codigo.trim();
    }

    public int getParticipantes() {
        return participantes;
    }

    public void setParticipantes(int participantes) {
        if (participantes < 1 || participantes > 30) {
            throw new IllegalArgumentException("Los participantes deben estar entre 1 y 30.");
        }
        this.participantes = participantes;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        if (distanciaKm <= 0) {
            throw new IllegalArgumentException("La distancia debe ser mayor que cero.");
        }
        this.distanciaKm = distanciaKm;
    }

    /** @return tipo de la excursión: "Trekking" o "Kayak". */
    public abstract String getTipo();

    /** @return valor por persona, en pesos. */
    public abstract double calcularValorPersona();

    /** @return valor total de la excursión (valor por persona por participantes), en pesos. */
    public double calcularValorTotal() {
        return calcularValorPersona() * participantes;
    }
}
