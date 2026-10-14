package GP.SantaCruz.inversiones;

import org.junit.After;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;

public class CedipTest {



    @After
    public void teardown() throws IOException, InterruptedException {
        global.practia.jemeinsamstark.web.core.WebDriver.closeDriver();
        cerrarConexion();
    }
}
