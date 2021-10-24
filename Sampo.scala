/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF
package novakorp.nifi.processors

import java.io._
import java.util.UUID
import scala.util.Try
import org.apache.nifi.flowfile.FlowFile
import org.apache.nifi.components.PropertyDescriptor
import org.apache.nifi.processor.{ AbstractProcessor, Relationship }
import org.apache.nifi.processor.{ ProcessContext, ProcessSession }
import org.apache.nifi.annotation.documentation.{ Tags, CapabilityDescription }
import org.apache.nifi.processor.exception.ProcessException
import org.apache.nifi.processor.util.StandardValidators
import sun.misc.IOUtils._
import org.apache.nifi.processor.io.{ InputStreamCallback, OutputStreamCallback }
import org.apache.nifi.expression.ExpressionLanguageScope
import scala.io.Source
@Tags(Array("Novakorp", "tag de tarea del procesador"))
@CapabilityDescription("Este procesador genera un pokemon para un entrenador y tipo de pokemon especificado")
class RealProcessorPokeball extends AbstractProcessor with TemplateProperties with TemplateRelationships with ProcesadorPokeball {

  import scala.collection.JavaConverters._

  override def getSupportedPropertyDescriptors: java.util.List[PropertyDescriptor] = {
    properties.asJava
  }

  private val processorId = "ProcessorSystem-" + UUID.randomUUID().toString

  override def getRelationships(): java.util.Set[Relationship] = {
    this.relationships.asJava
  }

  override def onTrigger(context: ProcessContext, session: ProcessSession): Unit = {

    val inFlowFile = session.get

    Option(inFlowFile) match {
      case Some(_) =>
        val in = session.read(inFlowFile)

        val propiedad_entrenador = context.getProperty(Entrenador_seleccionado) //asi tomo el valor de la propiedad que paso como variable
        val propiedad_entrenador_value = propiedad_entrenador.evaluateAttributeExpressions(inFlowFile).getValue

        val propiedad_tipo_pokemon = context.getProperty(Tipo_seleccionado)
        val propiedad_tipo_pokemon_value = propiedad_tipo_pokemon.evaluateAttributeExpressions(inFlowFile).getValue // asi tomo el valor de la propiedad con el nifiexpression language

        val rutaFF = context.getProperty(ruta_del_ff)
        val propiedad_rutaFF_value = rutaFF.evaluateAttributeExpressions(inFlowFile).getValue // asi tomo el valor de la propiedad con el nifiexpression language

        //var cont = scala.io.Source.fromInputStream(in).mkString("") // asi leo el contenido del FF
        var cont = propiedad_rutaFF_value

        try {
          var pokeball = new Pokeball()
          var valor_final = pokeball.buscar_pokemon_para_ema_capo(propiedad_entrenador_value, cont, propiedad_tipo_pokemon_value)

          var outFlowFile = session.create()
          session.putAttribute(outFlowFile, "Nombre Entrenador", propiedad_entrenador_value)
          session.putAttribute(outFlowFile, "Nombre Ruta", propiedad_rutaFF_value)
          session.putAttribute(outFlowFile, "Tipo Pokemon", propiedad_tipo_pokemon_value)

          outFlowFile = session.write(outFlowFile, new OutputStreamCallback {
            @throws(classOf[IOException])
            override def process(outputStream: OutputStream): Unit =
              outputStream.write(valor_final.getBytes())
          })
          in.close()

          session.transfer(outFlowFile, RelSuccess) // asi transfiero un FF a la relacion success
          session.remove(inFlowFile)
        } catch {

          case e: Exception =>
            throw new ProcessException(e.getStackTrace.mkString("\n")) // asi obtengo el error cuando falla el procesador

            var outFlowFile = session.create(inFlowFile)
            val value = "La operacion no puedo ser realizada correctamente"
            outFlowFile = session.write(outFlowFile, new OutputStreamCallback {
              @throws(classOf[IOException])
              override def process(outputStream: OutputStream): Unit =
                outputStream.write(value.getBytes())
            })

            session.transfer(outFlowFile, RelFailure) // asi transfiero un FF a la relacion failure
            session.remove(inFlowFile)
        }
        session.commit() // asi transfiero un FF al siguiente procesador

      case None =>
    }
  }
}
