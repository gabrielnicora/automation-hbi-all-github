package listeners;

import io.qameta.allure.Attachment;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StepReporterEventListener implements StepLifecycleListener {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(StepReporterEventListener.class));

    public StepReporterEventListener() {
    }

    @Override
    public void beforeStepStart(StepResult result) {
        if (stepNoPerteneceACucumber(result.getName())) {
            LOGGER.info("Próximo Step: " + result.getName());
        } else {
            LOGGER.info("Próximo Step: " + result.getName());
        }
    }

    @Override
    public void afterStepUpdate(StepResult result) {
        if (stepNoPerteneceACucumber(result.getName())) {
            if (elStepPasoConExito(result.getStatus())) {
                LOGGER.info("Step realizado: " + result.getName());
            }
        } else {
            if (elStepPasoConExito(result.getStatus())) {
                LOGGER.info("Step realizado: " + result.getName());
            }
        }
    }

    public void afterStepStop(StepResult result) {
        LOGGER.log(Level.INFO, String.format("⇤: %s", result.getName()));
        if (result.getStatus() == Status.FAILED || result.getStatus() == Status.BROKEN) {
            LOGGER.log(Level.INFO, "FAIL or ERROR");
        }

    }

    @Attachment(
            value = "{name}",
            type = "text/plain"
    )
    public static String attachFile(String name, String data) {
        return data;
    }

    private boolean elStepPasoConExito(Status resultStatus) {
        return resultStatus != Status.FAILED && resultStatus != Status.BROKEN;
    }

    private boolean stepNoPerteneceACucumber(String result) {
        String[] keyWordsCucumber = {"Dado", "Cuando", "Y", "Entonces"};
        for (String palabra : keyWordsCucumber) {
            if (result.startsWith(palabra)) return false;
        }
        return true;
    }
}