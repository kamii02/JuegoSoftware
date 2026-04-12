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
 * Si el archivo no se encuentra, se lanza una excepción en tiempo de ejecución.
 * Si ocurre un error al leer el archivo, se registra en consola.
 * </p>
 *
 * @author Manuel
 * @version 1.0
 * @since 1.0
 */
public class PropertiesManager implements IConfigReader {
    /**
     * Objeto que almacena las propiedades cargadas desde el archivo.
     */
    Properties props =  new Properties();

    /**
     * Constructor que carga el archivo de propiedades desde el classpath.
     *
     * @param propFile nombre del archivo de propiedades (por ejemplo, {@code application.properties}).
     * @throws RuntimeException si el archivo no se encuentra en el classpath.
     */
    public PropertiesManager(String propFile) {
        try(InputStream input = getClass().getClassLoader().getResourceAsStream(propFile)) {
            if(input == null){
                throw new RuntimeException("Error: no se encontro el archivo");
            }
            props.load(input);
        }catch (IOException e){
            System.out.printf("Error critico al leer las propiedades:  "+ e.getMessage());
        }
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