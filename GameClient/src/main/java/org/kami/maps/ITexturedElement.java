package org.kami.maps;

/**
 * Define el contrato para elementos que utilizan una textura para su representación visual.
 * <p>
 * La textura generalmente se identifica mediante una ruta a un recurso
 * (por ejemplo, dentro del classpath del proyecto).
 * </p>
 */
public interface ITexturedElement {

    /**
     * Establece la ruta de la textura del elemento.
     *
     * @param path la ruta de la textura; puede ser relativa al classpath
     */
    void setTexturePath(String path);

    /**
     * Obtiene la ruta de la textura del elemento.
     *
     * @return la ruta de la textura, o {@code null} si no se ha definido
     */
    String getTexturePath();
}
