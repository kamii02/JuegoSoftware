package org.kami.config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Implementación de {@link IConfigReader} que obtiene la configuración
 * desde un archivo de propiedades ubicado en el classpath.
 *
 * <p>
 * Esta clase se encarga de:
 * <ul>
 *     <li>Cargar el archivo de configuración al inicializarse.</li>
 *     <li>Proveer acceso a los valores definidos en dicho archivo.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Si el archivo no se encuentra o ocurre un error al leerlo,
 * se lanza una excepción en tiempo de ejecución.
 * </p>
 *
 * <p>
 * Implementa el patrón Singleton para asegurar una única instancia
 * en toda la aplicación.
 * </p>
 *
 * @author Manuel
 * @version 1.0
 * @since 1.0
 */
public class PropertiesManager implements IConfigReader {
    /**
     * Constante que define el archivo de propiedades por defecto.
     */
    private static final String DEFAULT_FILE = "application.properties";

    /**
     * Instancia única de {@code PropertiesManager} (patrón Singleton).
     */
    private static volatile PropertiesManager instance;

    /**
     * Objeto que almacena las propiedades cargadas desde el archivo.
     */
    private final Properties props =  new Properties();

    /**
     * Constructor privado que evita la creación directa de instancias.
     *
     * @param propFile nombre del archivo de propiedades (por ejemplo, {@code application.properties})
     * @throws RuntimeException si el archivo no se encuentra o no puede leerse
     */
    private PropertiesManager(String propFile) {
        try(InputStream input = getClass().getClassLoader().getResourceAsStream(propFile)){
            if(input == null){
                throw new RuntimeException("Archivo no encontrado: " + propFile);
            }
            props.load(input);
        }catch (IOException e){
            throw new RuntimeException("Error leyendo la configuracion: " + e.getMessage());
        }
    }

    /**
     * Obtiene la instancia única de {@code PropertiesManager}.
     *
     * @return la instancia global del gestor de propiedades
     */
    public static PropertiesManager getInstance(){
        if(instance == null){
            synchronized (PropertiesManager.class){
                if(instance == null){
                    instance = new PropertiesManager(DEFAULT_FILE);
                }
            }
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Convierte el valor asociado a la clave en un entero utilizando
     * {@link Integer#parseInt(String)}.
     * </p>
     *
     * @param key la clave de configuración
     * @return el valor convertido a entero
     * @throws NumberFormatException si el valor no es un entero válido
     * @throws NullPointerException si la clave no existe
     */
    @Override
    public int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Retorna el valor asociado a la clave sin transformación.
     * </p>
     *
     * @param key la clave de configuración
     * @return el valor en formato String o {@code null} si no existe
     */
    @Override
    public String getString(String key) {
        return  props.getProperty(key);
    }

}