fun main() {
    var personaje: Personaje? = null
    var monstruo: Monstruo? = null

    while (true) {
        println("Menú:")
        println("1. Crear Personaje")
        println("2. Crear Artículo")
        println("3. Equipar Artículo")
        println("4. Usar Artículo")
        println("5. Comunicación")
        println("6. Salir")
        print("Selecciona una opción: ")

        val opcion = readLine()

        when (opcion) {
            "1" -> {
                println("Crear Personaje")
                var nombre: String
                do {
                    print("Nombre del Personaje: ")
                    nombre = readLine() ?: ""
                    if (nombre.isBlank()) {
                        println("El nombre no puede estar vacío. Inténtalo de nuevo.")
                    }
                } while (nombre.isBlank())
                var raza: Personaje.Raza? = null
                var clase: Personaje.Clase? = null
                var estadoVital: Personaje.EstadoVital? = null

                while (raza == null) {
                    println("Elige una raza:")
                    for (razaEnum in Personaje.Raza.values()) {
                        println("${razaEnum.ordinal + 1}. ${razaEnum.name}")
                    }
                    print("Selecciona una raza: ")
                    val razaSeleccionada = readLine()?.toIntOrNull()

                    if (razaSeleccionada != null && razaSeleccionada in 1..Personaje.Raza.values().size) {
                        raza = Personaje.Raza.values()[razaSeleccionada - 1]
                    } else {
                        println("Selección no válida. Inténtalo de nuevo.")
                    }
                }

                while (clase == null) {
                    println("Elige una clase:")
                    for (claseEnum in Personaje.Clase.values()) {
                        println("${claseEnum.ordinal + 1}. ${claseEnum.name}")
                    }
                    print("Selecciona una clase: ")
                    val claseSeleccionada = readLine()?.toIntOrNull()

                    if (claseSeleccionada != null && claseSeleccionada in 1..Personaje.Clase.values().size) {
                        clase = Personaje.Clase.values()[claseSeleccionada - 1]
                    } else {
                        println("Selección no válida. Inténtalo de nuevo.")
                    }
                }
                while (estadoVital == null) {
                    println("Elige un estado vital:")
                    for (estadoVitalEnum in Personaje.EstadoVital.values()) {
                        println("${estadoVitalEnum.ordinal + 1}. ${estadoVitalEnum.name}")
                    }
                    print("Selecciona una clase: ")
                    val estadoVitalSeleccionado = readLine()?.toIntOrNull()

                    if (estadoVitalSeleccionado != null && estadoVitalSeleccionado in 1..Personaje.EstadoVital.values().size) {
                        estadoVital = Personaje.EstadoVital.values()[estadoVitalSeleccionado - 1]
                    } else {
                        println("Selección no válida. Inténtalo de nuevo.")
                    }
                }

                personaje = Personaje(nombre, raza, clase, estadoVital)
                println("Personaje creado con éxito.")
                println(personaje.toString())
            }
            "2" -> {
                println("Crear Articulo")
                if (personaje != null) {

                    print("Ingresa el tipo de artículo (ARMA, OBJETO, PROTECCION): ")
                    val tipoArticuloLeido = readlnOrNull()
                    print("Ingresa el nombre del artículo: ARMA (BASTON, ESPADA, DAGA, MARTILLO, GARRAS), OBJETO (POCION, IRA), PROTECCION (ESCUDO; ARMADURA)")
                    val nombreArticuloLeido = readlnOrNull()
                    print("Ingresa el peso del artículo: ")
                    val pesoArticulo = readlnOrNull()?.toIntOrNull()
                    val tipoArticulo = enumValues<Articulo.TipoArticulo>().find { it.name == tipoArticuloLeido }
                    val nombreArticulo = enumValues<Articulo.Nombre>().find { it.name == nombreArticuloLeido }

                    if (tipoArticulo != null && nombreArticulo != null && pesoArticulo != null) {
                        personaje.getMochila().addArticulo(Articulo(tipoArticulo, nombreArticulo, pesoArticulo, 12))
                        println(personaje.toString())

                    } else
                        println("Error en el tipo o nombre del Artículo")
                }else{
                    println("Debes crear un Personaje primero.")
                }
            }
            "3" -> {
                println("Equipar Articulo")
                if (personaje != null) {
                    if (personaje.getMochila().toString() != "Mochila vacía"){
                        println(personaje.getMochila().toString())
                        print("Ingresa el nombre del artículo que quieres equipar: ARMA (BASTON, ESPADA, DAGA, MARTILLO, GARRAS), PROTECCION (ESCUDO; ARMADURA)")
                        val nombreArticuloLeido = readlnOrNull()
                        val nombreArticulo = enumValues<Articulo.Nombre>().find { it.name == nombreArticuloLeido }
                        if(nombreArticulo!=null) {
                            if (personaje.getMochila().findObjeto(nombreArticulo) != -1) {
                                val articulo = Articulo(personaje.getMochila().getContenido()[personaje.getMochila()
                                    .findObjeto(nombreArticulo)].getTipoArticulo(), nombreArticulo, personaje.getMochila().getContenido()[personaje.getMochila()
                                    .findObjeto(nombreArticulo)].getPeso(),12)
                                personaje.equipar(articulo)
                                personaje.getMochila().getContenido().remove(articulo)
                            } else {
                                println("No existe ese Articulo")
                            }
                        }
                    }
                    else{
                        println(personaje.getMochila().toString())
                    }

                }
                else {
                    println("Debes crear un Personaje primero.")
                }
            }
            "4" -> {
                println("Usar Articulo")
                if (personaje != null) {
                    if (personaje.getMochila().toString() != "Mochila vacía"){
                        println(personaje.getMochila().toString())
                        print("Ingresa el nombre del artículo que quieres usar: OBJETO (POCION, IRA)")
                        val nombreArticuloLeido = readlnOrNull()
                        val nombreArticulo = enumValues<Articulo.Nombre>().find { it.name == nombreArticuloLeido }
                        if(nombreArticulo!=null) {
                            if (personaje.getMochila().findObjeto(nombreArticulo) != -1) {
                                val articulo = Articulo(personaje.getMochila().getContenido()[personaje.getMochila()
                                    .findObjeto(nombreArticulo)].getTipoArticulo(), nombreArticulo, personaje.getMochila().getContenido()[personaje.getMochila()
                                    .findObjeto(nombreArticulo)].getPeso(),12)
                                personaje.usarObjeto(articulo)
                                personaje.getMochila().getContenido().remove(articulo)
                            } else {
                                println("No existe ese Articulo")
                            }
                        }
                    }
                    else{
                        println(personaje.getMochila().toString())
                    }

                }
                else {
                    println("Debes crear un Personaje primero.")
                }
            }
            "5" -> {
                println("Comunicación")
                if(personaje!=null){
                    print("Ingresa el mensaje")
                    val mensaje = readlnOrNull()
                    if (mensaje != null) {
                        personaje.comunicacion(mensaje)
                    }else{
                        println("Introduce un mensaje válido")
                    }
                }else{
                    println("Debes crear un Personaje primero.")
                }
            }
            "6" -> {
                println("Saliendo del programa.")
                return
            }
            else -> {
                println("Opción no válida. Inténtalo de nuevo.")
            }
        }
    }
}
