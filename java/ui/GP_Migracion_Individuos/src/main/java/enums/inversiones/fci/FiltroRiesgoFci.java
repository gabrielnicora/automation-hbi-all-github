package enums.inversiones.fci;

public enum FiltroRiesgoFci implements FiltroFci {

    CONSERVADOR("CONSERVADOR"),
    MODERADO("MODERADO"),
    DINAMICO("DINÁMICO");

    private static final String GRUPO_DOM = "RIESGO:";

    private final String textoDom;

    FiltroRiesgoFci(String textoDom) {
        this.textoDom = textoDom;
    }

    @Override
    public String getGrupoDom() {
        return GRUPO_DOM;
    }

    @Override
    public String getTextoDom() {
        return textoDom;
    }

    @Override
    public String toString() {
        return textoDom;
    }
}
