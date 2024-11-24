# Hito 3

## Justificación Framework Elegido

Actualmente, kotlin cuenta con una serie de frameworks de pruebas bastante útiles y sencillos de utilizar. Entre ellos y
debido a que estoy utilizando un proyecto con un lenguaje que estoy aprendiendo, he decidido decantarme por los dos más
populares y sencillos de utilizar en su integración con Kotlin. Estos son:

### Springboot

Springboot es un framework de pruebas bastante útil y sencillo de utilizar. Cuenta con una documentación bastante
extensa
y una comunidad bastante activa que puede ayudar en caso de problemas. La integración con Kotlin es bastante buena y
sencilla
de utilizar. Asimismo, cuenta con una serie de herramientas bastante útiles para el desarrollo de pruebas.

### Ktor

Ktor es un framework web de Kotlin creado por JetBrains que se utiliza para crear aplicaciones web y servicios web.
Además, es un marco web asíncrono y basado en eventos que se ejecuta en la JVM. Asimismo se encuentran ya en su versión
3.0.0, lo que hace que sea bastante estable y sencillo de utilizar. La documentación del mismo es bastante sencilla y
se encuentra detallada en la página oficial del mismo. Por último, ktor es ligero y flexible.

### Elección

Aunque Springboot es una herramienta bastante útil y sencilla de utilizar, he decidido decantarme por Ktor, ya que es
una nueva herramienta que me suscita bastante curiosidad y me gustaría aprender a utilizarla. Asimismo, la documentación
del mismo es bastante extensa como se ha comentado previamente y la integración con Kotlin es total debido a que son los
mismos desarrolladores. En definitiva, la decisión ha sido tomada por la curiosidad y las ganas de aprender algo nuevo.

## Diseño de la API, test y documentación

## Elección de librería de logs y registro de estos

Ktor como venimos comentando es relativamente nuevo, por ello la elección de la librería de logs consiste en dos
opciones:

## Klogging

Se encuentran actualmente en la version 0.8.0 y es capaz de poder funcionar de manera asíncrona teniendo una
configuración sencilla a través de DSL o un json. Su principal tarea es poder crear una buena experiencia a los
desarrolladores de C# y Java con unos logs estructurados. Sin embrgo, aún está a unos comienzos y no es una librería que
pueda contar con una comunidad activa debido a su reciente creación y esto puede ser un problema de cara a enfrentarnos
a problemas futuros.

## SLF4J API

Es la versión nativa de Ktor que actua como fachada de varios frameworks de logs como **Logback** o **Log4j**. Es una
librería bastante estable donde en el archivo de configuración xml se elige, por ejemplo, el destino de los logs y
permite
una fácil integración dentro de nuestro framework. Dentro de esta versión debemos decidir los dos frameworks comentados:

### Logback

Es el sucesor directo de Log4j creado por Ceki Gülcü y está diseñado para ser más rápido,
flexible y eficiente.
Su integración es bastante sencilla y cuenta con bastante documentación y comunidad entre los desarrolladores java.
Para añadirlo hay que:
`implementation("ch.qos.logback:logback-classic:$logback_version")`

### Log4j

Log4j es una biblioteca de registros más antigua que sigue desarrollándose y con, quizás más documentación pero
una comunidad menos activa. Su integración es bastante sencilla y su integración requiere unos pocos más de cambios
en el archivo de configuración. Para añadirlo hay que "o use Log4j, you need to add the org.apache.logging.log4j:
log4j-core
and org.apache.logging.log4j:log4j-slf4j-impl artifacts."

## Elección

La elección por comodidad, comunidad y documentación en un cómputo global es de **SLF4J API** con **Logback**. Aunque
Klogging es una librería bastante interesante y con un futuro prometedor. Por otro lado log4j es una librería
interesante
pero me gusta probar una implementación más nueva y donde se haya apostado por la flexibilidad y rapidez.

## Correcto funcionamiento de logs

