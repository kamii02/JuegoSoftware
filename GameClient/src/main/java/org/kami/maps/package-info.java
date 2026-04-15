/**
 * Proporciona las clases e interfaces encargadas de la gestión de mapas del juego.
 * <p>
 * Este paquete incluye funcionalidades para:
 * <ul>
 *     <li>Lectura y carga de mapas desde archivos (por ejemplo, CSV)</li>
 *     <li>Manejo de fondos y renderizado</li>
 *     <li>Animación de elementos del mapa (como monedas)</li>
 *     <li>Abstracciones para el acceso a mapas mediante interfaces</li>
 * </ul>
 *
 * <h2>Componentes principales</h2>
 * <ul>
 *     <li>{@link org.kami.maps.MapReader}: Implementación principal para leer mapas</li>
 *     <li>{@link org.kami.maps.IMapsHandler}: Contrato para la gestión de mapas</li>
 *     <li>{@link org.kami.maps.BackgroundLoader}: Maneja la carga y renderizado del fondo</li>
 *     <li>{@link org.kami.maps.CoinAnimator}: Gestiona animaciones periódicas</li>
 * </ul>
 *
 * <h2>Diseño</h2>
 * <p>
 * Este paquete sigue principios de bajo acoplamiento mediante el uso de interfaces,
 * e incluye patrones como:
 * </p>
 * <ul>
 *     <li>Singleton (en {@link org.kami.maps.MapReader})</li>
 *     <li>Separación de responsabilidades (lectura, renderizado, animación)</li>
 * </ul>
 *
 * <h2>Notas</h2>
 * <ul>
 *     <li>Los mapas suelen cargarse desde recursos del classpath</li>
 *     <li>Se recomienda validar rutas y recursos para evitar errores en tiempo de ejecución</li>
 * </ul>
 */
package org.kami.maps;