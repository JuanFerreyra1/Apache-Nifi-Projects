package novakorp.nifi.processors
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.Random
import scala.io.Source

trait ProcesadorPokeball {

  case class Pokemon(nombre: String, pd_num: Int, var hp: Int, atk: Int, df: Int, sp_atk: Int, sp_df: Int, var speed: Int, gen: Int, isleg: Int, tipo: String) {

    //println(s"$nombre, $pd_num, $hp, $atk, $df, $sp_atk, $sp_df, $speed, $gen, $isleg")
  }

  class Pokeball() {

    def buscar_pokemon_para_ema_capo(Entrenador: String, ruta_atributo_procesador_lore: String, TipoP: String): String = {
      val pokemon_encontrado = atrapar_para_ema_capo(Entrenador, ruta_atributo_procesador_lore, TipoP)
      pokemon_encontrado
      //unEntrenador.agregar_pokemon(pokemon_encontrado)
    }

    def atrapar_para_ema_capo(Entrenador_rec: String, Ruta_normal: String, tipo: String): String = {
      var entr = Entrenador_rec
      var rut = Ruta_normal
      var tip = tipo
      def apartado_tipo(ruta: String, typpe: String): Array[Int] = {
        var lineas_tipo: Array[Int] = Array()
        var c = 0
        for (line <- Source.fromFile(ruta).getLines) {
          c = c + 1
          var linea_sep = line.split(",").reverse
          if (linea_sep(0) == tip) {
            lineas_tipo = lineas_tipo :+ c
          }
        }
        lineas_tipo
      } //DEVUELVE UN ARRAY CON LOS NUMEROS DE LAS FILAS
      // DE LOS POKEMONES PARA ESE TIPO EN EL ARCHIVO CSV PASADO

      def generar_ale(ruta_normal: String, tipo: String): Int = {
        /*nextInt(upperbound) generates random numbers in the range 0 to upperbound-1.*/
        val ss = new Random()
        var numero = 0
        var numero_real = 0
        while (numero == 0) {
          numero = ss.nextInt(apartado_tipo(ruta_normal, tipo).size + 1)
          //numero = 1
          //numero_real = 40
          numero_real = apartado_tipo(ruta_normal, tipo)(numero)
        }
        numero_real
      }

      val n_a = generar_ale(rut, tip) //con esto hago que cuando llamo a atrapar me llame al metodo generar_ale y me lo guarde en la variable n_a

      var c = 0
      var linea_encontrada = ""
      var car = new Array[String](500)
      for (line <- Source.fromFile(rut).getLines) {
        c = c + 1
        if (c == n_a) {
          linea_encontrada = line
          car = linea_encontrada.split(",")
        }
      }

      val pokemon_atrapado = new Pokemon(car(0), car(1).toInt, car(2).toInt, car(3).toInt, car(4).toInt, car(5).toInt, car(6).toInt, car(7).toInt, car(8).toInt, car(9).toInt, car(10))
      val pokemon_hallado = s"${Entrenador_rec},${pokemon_atrapado.nombre},${pokemon_atrapado.pd_num},${pokemon_atrapado.hp},${pokemon_atrapado.atk},${pokemon_atrapado.df},${pokemon_atrapado.sp_atk},${pokemon_atrapado.sp_df},${pokemon_atrapado.speed},${pokemon_atrapado.gen},${pokemon_atrapado.isleg},${pokemon_atrapado.tipo}"
      pokemon_hallado //retorno de función
    } //CAMBIO

  }
}

object ProcesadorPokeball extends ProcesadorPokeball
