package enums.inversiones.fci;

public enum FiltroMonedaFci implements FiltroFci {

    PESOS("PESOS"),
    DOLARES("DÓLARES");

    private static final String GRUPO_DOM = "MONEDA:";

    private final String textoDom;

    FiltroMonedaFci(String textoDom) {
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
