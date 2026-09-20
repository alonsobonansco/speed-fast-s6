# 🚚 SpeedFast App

Actividad formativa 4 (Semana 6)

---

## 📖 Descripción

Este repositorio contiene la infraestructura modular de **SpeedFast**, una aplicación de escritorio diseñada bajo el
patrón de arquitectura **MVC (Modelo-Vista-Controlador)** que integra programación concurrente asíncrona mediante hilos
independientes.

---

## 🚀 Características Principales

### 1. Arquitectura de Interfaz y Control (Flujo de Ventanas)

La aplicación desacopla por completo la interfaz gráfica (Swing) de la lógica de orquestación utilizando un flujo de
dependencias unidireccional y el patrón *Observer*:

#### Orquestación Principal (`ControladorPrincipal`)

* Actúa como el despachador central de la suite.
* Escucha los eventos de los botones de la `VentanaPrincipal` mediante listeners semánticos explicitos (
  `addRegistrarPedidoListener`, etc.).
* Al gatillar una acción, valida la existencia y visibilidad de las ventanas secundarias (`isDisplayable()`) para
  asegurar el control de instancias únicas en la memoria RAM.

#### Ciclo de Registro y Listado

* **`ControladorRegistro` ⬄ `VentanaRegistroPedido`:** La vista implementa un formulario dinámico que muta sus campos en
  tiempo de ejecución según el tipo seleccionado. Al presionar "Guardar", el controlador extrae los datos mediante
  getters, ejecuta validaciones de formato (`NumberFormatException`) y bloquea de raíz los folios duplicados (
  `existePedido()`) antes de impactar el historial permanente.
* **`ControladorLista` ⬄ `VentanaListaPedidos`:** Al instanciarse, refresca la tabla gráfica de forma simétrica. La
  `VentanaListaPedidos` lee los pedidos de forma ciega y polimórfica a través de la firma `getDetalleEspecifico()`,
  abstrayéndose de la naturaleza interna de cada subclase.
* **Flujo de Retorno:** Ambas ventanas secundarias delegan su destrucción física al controlador correspondiente mediante
  el método `this.dispose()`, liberando recursos de forma limpia.

---

### 2. Motor Concurrente Logístico (Pedidos, Repartidores y Central)

El núcleo operativo de simulación asíncrona implementa el patrón de diseño **Productor-Consumidor** para procesar los
envíos en tiempo real:

#### El Archivador Central vs. El Andén de Despacho

* **`List<Pedido>` (El Archivador):** Es la colección permanente que vive en el controlador de ventanas. Mantiene los
  objetos fijos e inmutables en su identidad, sirviendo de registro histórico.
* **`BlockingQueue<Pedido>` (El Andén):** Ubicada dentro de `ControladorPedidos`. Al iniciar la simulación, un bucle
  filtra los pedidos del archivador que califican en estado `PENDIENTE` y ejecuta las validaciones de negocio del
  modelo (límites de peso o distancia). Si aprueban, se inyecta una referencia de memoria en esta cola segura; si
  fallan, se muta su estado internamente a `CANCELADO` de forma reactiva.

#### El Ciclo de Vida del `Repartidor` (*Worker Threads*)

* Los repartidores (`Juan`, `María` y `Carlos`) son clases situadas en la capa de servicios que implementan la interfaz
  nativa `Runnable`.
* Cada uno corre un bucle infinito independiente consumiendo el método destructivo `.poll()` de la `BlockingQueue`. Al
  retirar un pedido, este se elimina de la cola de despacho pero modifica el objeto original en la memoria compartida.
* Los hilos ejecutan la ruta logística simulando tiempos reales con la API moderna `TimeUnit.MILLISECONDS.sleep()`,
  mutando el estado del pedido a través de Enums fuertemente tipados (`PENDIENTE` ➔ `ENTREGADO`).

#### Sincronización y Cierre de Jornada

* La comunicación hacia la interfaz visual se realiza a través de un puente asíncrono aislado (`LogListener`),
  utilizando un método de escritura `synchronized` para evitar condiciones de carrera o corrupción de fuentes gráficas
  en el `JTextArea`.
* Para evitar el spam visual o la duplicación de carteles finales, el sistema maneja un contador primitivo de hilos
  vivos. Cada repartidor, justo antes de destruir su hilo en la RAM al quedarse sin tareas, ejecuta de forma segura
  `finalizarSimulacion(nombre)`. El último hilo operativo en reducir el contador a cero tiene la responsabilidad
  exclusiva de estampar el aviso de cierre logístico en la bitácora.

---

## 📁 Estructura del Proyecto

```text
speed-fast-s6/
└── src/
    └── main/
        └── java/
            └── cl/
                └── duoc/
                    └── speedfast/
                        │
                        ├── Main.java                       # Inicializador global y orquestador del ControladorPrincipal
                        │
                        ├── model/
                        │   ├── Pedido.java                 # Clase abstracta con ID (int) inmutable y firma getDetalleEspecifico()
                        │   ├── PedidoComida.java
                        │   ├── PedidoEncomienda.java
                        │   ├── PedidoExpress.java
                        │   ├── TipoPedido.java             # Enum de tipado fuerte (COMIDA, ENCOMIENDA, EXPRESS)
                        │   ├── EstadoPedido.java           # Enum de ciclo de vida (PENDIENTE, ENTREGADO, CANCELADO)
                        │   └── Cancelable.java             # Interfaz Funcional con el contrato de aborto logístico
                        │
                        ├── view/
                        │   ├── VentanaPrincipal.java       # Panel de control con bitácora JTextArea integrada
                        │   ├── VentanaRegistroPedido.java  # Formulario dinámico para los subtipos de pedido
                        │   └── VentanaListaPedidos.java    # JTable adaptada con 5 columnas
                        │
                        ├── controller/
                        │   ├── ControladorPrincipal.java   # Despachador central y gestor de instancias de ventanas
                        │   ├── ControladorRegistro.java    # Capturador de inputs con escudo para NumberFormatException e ID duplicado
                        │   ├── ControladorLista.java       # Refrescador simétrico de la grilla gráfica de datos
                        │   └── ControladorPedidos.java     # Central de hilos asíncronos con contador seguro de repartidores
                        ├── event/
                        │   └── LogListener.java            # Interfaz funcional para el transporte seguro de logs hacia Swing            
                        └── service/
                            └── Repartidor.java             # Hilo Runnable independiente con TimeUnit y control de fin de jornada
```

---

## 🛠️ Instrucciones para clonar y ejecutar

Requisitos del sistema:

* **JDK:** Java 25 (LTS) o superior

1. Clonar el repositorio desde la terminal de la computadora o IDE:  
   git clone https://github.com/alonsobonansco/speed-fast-s6.git
2. Ir a File →️ Open y seleccionar la carpeta raíz del proyecto (la carpeta que contiene el archivo pom.xml).
3. Ejecutar el `Main` desde su clase en el paquete raíz `cl.duoc.speedfast`

---

## 👤 Autor

Alonso Bonansco Vergara  
Desarrollo Orientado a Objetos II - 004A  
Analista Programador Computacional
