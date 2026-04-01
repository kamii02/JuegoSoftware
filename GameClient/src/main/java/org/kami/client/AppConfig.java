package org.kami.client;

import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private final Properties props = new Properties();

    public AppConfig() {
        try (InputStream in = getClass()
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (in == null) {
                throw new RuntimeException("No se encontró application.properties en resources/");
            }
            props.load(in);

        } catch (Exception e) {
            throw new RuntimeException("Error leyendo configuración: " + e.getMessage());
        }
    }

    public String getServerIp()   { return props.getProperty("ip"); }
    public int    getServerPort() { return Integer.parseInt(props.getProperty("port")); }
    public String getPlayerId()   { return props.getProperty("id"); }
}
