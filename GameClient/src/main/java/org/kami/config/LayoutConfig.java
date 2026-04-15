package org.kami.config;

/**
 * Implementación de {@link ILayoutConfig} que obtiene los valores de
 * configuración a partir de un {@link IConfigReader}.
 *
 * <p>
 * Esta clase actúa como intermediaria entre el sistema de configuración
 * genérico y las necesidades específicas del layout del juego, delegando
 * la obtención de los valores al lector de configuración.
 * </p>
 *
 * <p>
 * Permite centralizar las claves utilizadas y facilita cambios en la
 * fuente de configuración sin afectar al resto del sistema.
 * </p>
 *
 * @author  manuel
 * @version 1.0
 * @since   1.0
 */
public class LayoutConfig implements ILayoutConfig {
    /**
     * Lector de configuración utilizado para obtener los valores.
     */
    private final IConfigReader configReader;

    /**
     * Construye una instancia de {@code LayoutConfig} con el lector de configuración especificado.
     *
     * @param configReader la fuente de datos de configuración
     */
    public LayoutConfig(IConfigReader configReader) {
        this.configReader = configReader;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMapFolder() {
        return configReader.getString("map_folder");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return configReader.getInt("height");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return configReader.getInt("width");
    }

}
