/**
 * Proporciona interfaces y clases para la gestión de configuraciones del sistema.
 *
 * <p>
 * Este paquete centraliza el acceso a distintos tipos de configuración del juego,
 * como layout, red y parámetros generales, siguiendo el principio de separación
 * de responsabilidades y programación contra interfaces.
 * </p>
 *
 * <p>
 * Incluye interfaces como:
 * <ul>
 *   <li>{@link org.kami.config.IConfigReader}: contrato general para la lectura de configuraciones.</li>
 *   <li>{@link org.kami.config.ILayoutConfig}: define parámetros relacionados con la interfaz gráfica.</li>
 *   <li>{@link org.kami.config.IUDPConfig}: define parámetros para la comunicación de red mediante UDP.</li>
 * </ul>
 * </p>
 *
 * <p>
 * También incluye implementaciones concretas como:
 * <ul>
 *   <li>{@link org.kami.config.LayoutConfig}: obtiene configuraciones de layout a partir de un {@code IConfigReader}.</li>
 *   <li>{@link org.kami.config.UDPConfig}: gestiona configuraciones de red y el identificador del jugador.</li>
 * </ul>
 * </p>
 *
 * <p>
 * El diseño permite cambiar la fuente de configuración (por ejemplo, archivos
 * .properties, JSON o base de datos) sin afectar al resto del sistema.
 * </p>
 *
 * @since 1.0
 */
package org.kami.config;