/**
 * Proporciona las clases e interfaces relacionadas con la creación de elementos de mapa.
 * <p>
 * Este paquete implementa el patrón de diseño Factory para desacoplar la
 * creación de objetos concretos de su uso dentro del sistema.
 * </p>
 *
 * <h2>Componentes principales</h2>
 * <ul>
 *     <li>{@link org.kami.maps.mapelementsfactory.IMapElement}: Contrato base para elementos del mapa</li>
 *     <li>{@link org.kami.maps.mapelementsfactory.IMapElementFactory}: Interfaz de fábrica</li>
 *     <li>{@link org.kami.maps.mapelementsfactory.MapElementFactory}: Implementación concreta de la fábrica</li>
 *     <li>{@link org.kami.maps.mapelementsfactory.MapElementType}: Enum que define los tipos disponibles</li>
 * </ul>
 *
 * <h2>Diseño</h2>
 * <p>
 * Se utiliza un enfoque basado en registro mediante {@link java.util.Map}
 * y {@link java.util.function.Supplier} para asociar tipos de elementos
 * con sus respectivos constructores.
 * </p>
 *
 * <h2>Ventajas</h2>
 * <ul>
 *     <li>Extensibilidad: fácil agregar nuevos tipos de elementos</li>
 *     <li>Bajo acoplamiento entre creación y uso</li>
 *     <li>Evita estructuras condicionales complejas</li>
 * </ul>
 *
 * <h2>Notas</h2>
 * <ul>
 *     <li>Los tipos definidos en {@link org.kami.maps.mapelementsfactory.MapElementType}
 *     deben estar registrados en la fábrica</li>
 * </ul>
 */
package org.kami.maps.mapelementsfactory;