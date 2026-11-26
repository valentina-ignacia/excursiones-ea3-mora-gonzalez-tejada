package cl.duoc.excursiones.model;

/**
 * Excursión de trekking.
 * <p>
 * Valor por persona: base de $45.000; si incluye guía bilingüe, se incrementa en un 30%.
 */
public class Trekking extends Excursion implements Asegurable {

    public static final String TIPO = "Trekking";
    private static final double VALOR_BASE = 45000;
    private static final double RECARGO = 0.30;

    private double desnivelMetros;
    private boolean guiaBilingue;
    private boolean seguroIncluido;

    public Trekking(String codigo, int participantes, double distanciaKm,
                    double desnivelMetros, boolean guiaBilingue, boolean seguroIncluido) {
        super(codigo, participantes, distanciaKm);
        setDesnivelMetros(desnivelMetros);
        this.guiaBilingue = guiaBilingue;
        this.seguroIncluido = seguroIncluido;
    }

    public double getDesnivelMetros() {
        return desnivelMetros;
    }

    public void setDesnivelMetros(double desnivelMetros) {
        if (desnivelMetros <= 0) {
            throw new IllegalArgumentException("El desnivel debe ser mayor que cero.");
        }
        this.desnivelMetros = desnivelMetros;
    }

    public boolean isGuiaBilingue() {
        return guiaBilingue;
    }

    public void setGuiaBilingue(boolean guiaBilingue) {
        this.guiaBilingue = guiaBilingue;
    }

    @Override
    public boolean isSeguroIncluido() {
        return seguroIncluido;
    }

    @Override
    public void setSeguroIncluido(boolean seguroIncluido) {
        this.seguroIncluido = seguroIncluido;
    }

    @Override
    public String getTipo() {
        return TIPO;
    }

    @Override
    public double calcularValorPersona() {
        double valor = VALOR_BASE;
        if (guiaBilingue) {
            valor += VALOR_BASE * RECARGO;
        }
        return valor;
    }
}
