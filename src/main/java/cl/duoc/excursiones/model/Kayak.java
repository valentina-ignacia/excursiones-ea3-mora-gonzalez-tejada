package cl.duoc.excursiones.model;

/**
 * Excursión en kayak.
 * <p>
 * Valor por persona: base de $60.000; si la duración supera 3 horas, se incrementa en un 15%.
 */
public class Kayak extends Excursion {

    public static final String TIPO = "Kayak";
    private static final double VALOR_BASE = 60000;
    private static final double RECARGO = 0.15;

    private double duracionHoras;

    public Kayak(String codigo, int participantes, double distanciaKm, double duracionHoras) {
        super(codigo, participantes, distanciaKm);
        setDuracionHoras(duracionHoras);
    }

    public double getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(double duracionHoras) {
        if (duracionHoras <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor que cero.");
        }
        this.duracionHoras = duracionHoras;
    }

    @Override
    public String getTipo() {
        return TIPO;
    }

    @Override
    public double calcularValorPersona() {
        double valor = VALOR_BASE;
        if (duracionHoras > 3) {
            valor += VALOR_BASE * RECARGO;
        }
        return valor;
    }
}
