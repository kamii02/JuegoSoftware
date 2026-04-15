package org.kami.config;
/**
 * Define el contrato para acceder a la configuración de layout del juego.
 *
 * <p>
 * Esta interfaz proporciona los métodos necesarios para obtener rutas de recursos
 * y dimensiones utilizadas en la interfaz gráfica y el entorno del juego.
 * </p>
 *
 * <p>
 * Permite desacoplar la fuente de configuración (archivos, constantes, etc.)
 * de su uso dentro del sistema.
 * </p>
 *
 * @author  manuel
 * @version 1.0
 * @since   1.0
 */
public interface ILayoutConfig {

    /**
     * Obtiene la ruta de la carpeta donde se almacenan los mapas del juego.
     */
    String getMapFolder();

    /**
     * Obtiene la altura de la ventana o área de juego.
     */
    int getHeight();

    /**
     * Obtiene el ancho de la ventana o área de juego.
     */
    int getWidth();
}
