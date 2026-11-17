# CinemaUQ - Sistema de Gestión de Cine (Maven, JavaFX)

Proyecto final de Programación I (Universidad del Quindío). Equipo: **Alejandro Morales, Andrés Felipe
Alegría, Yoan Sebastián Bermúdez**.

## Cómo abrirlo en IntelliJ

1. `File → Open…` y seleccionar la carpeta `CinemaUQ` (la que tiene el `pom.xml`).
2. *Trust project*. IntelliJ descargará JUnit 5 y JavaFX (`javafx-controls`) desde Maven Central.
3. Verificar en `File → Project Structure → Project` que el SDK sea **JDK 21 o superior**.
4. Ejecutar la app: clic derecho sobre `pom.xml` → *Maven* → *javafx:run*, o abrir
   `src/main/java/co/edu/uniquindio/cinemauq/app/Main.java` → botón ▶ Run (si falla por módulos de JavaFX,
   usar la opción de Maven `javafx:run`, que ya trae el *module path* configurado).
5. Ejecutar las pruebas: clic derecho sobre `src/test/java` → *Run 'All Tests'*.

Desde la terminal (con Maven instalado):

```
mvn test          # corre las 36 pruebas JUnit 5
mvn javafx:run    # abre la interfaz grafica
```

> **Nota importante:** este código no se compiló en un entorno con JavaFX real (el sandbox donde se generó
> no tiene acceso a Maven Central ni a los jars de JavaFX). Las clases de `modelo` y `excepciones` **sí** se
> compilaron y verificaron con `javac` en ese entorno. La interfaz gráfica (`Main.java`, paquete `app`) se
> escribió con cuidado siguiendo la API estándar de JavaFX, pero debe compilarse y probarse por primera vez
> en IntelliJ (los dos PCs del equipo). Si aparece algún error de compilación puntual en `Main.java`, es casi
> siempre un detalle menor (un import, un tipo de columna) y se corrige fácilmente comparando con el mensaje
> de error de IntelliJ.

## Estructura

```
CinemaUQ/
├── pom.xml                                  (Java 21, JUnit 5, JavaFX 21)
├── Pensamiento_Computacional_y_Diseno.md
├── Diagrama_Clases_CinemaUQ.png
├── src/main/java/co/edu/uniquindio/cinemauq/
│   ├── modelo/        enums, entidades (Pelicula, Sala, Asiento, Cliente, Funcion, Venta)
│   │                  y la clase de servicio CinemaUQ
│   ├── excepciones/   9 excepciones propias del dominio
│   └── app/           Main (interfaz grafica JavaFX, por pestañas)
└── src/test/java/co/edu/uniquindio/cinemauq/modelo/   6 clases de prueba JUnit 5 (36 pruebas)
```

## Funcionalidades cubiertas

**Operador:** vender entrada (con todas sus validaciones), cancelar venta, consultar películas y funciones,
consultar disponibilidad de una función (asientos disponibles/ocupados).

**Administrador:** CRUD + deshabilitar de películas y salas (sin duplicados), programar funciones (bloqueando
salas fuera de servicio), tarifas por tipo de cliente, y los 5 reportes pedidos: total de entradas vendidas,
total de ingresos, película más vendida, porcentaje de ocupación de una función, funciones programadas por
fecha.

## Excepciones propias

`PeliculaDuplicadaException`, `PeliculaNoEncontradaException`, `SalaDuplicadaException`,
`SalaNoEncontradaException`, `SalaNoDisponibleException`, `FuncionNoEncontradaException`,
`AsientoOcupadoException`, `SinAsientosDisponiblesException`, `VentaNoEncontradaException`.
