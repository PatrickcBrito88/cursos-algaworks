package com.algaworks.algafoods.core.jackson;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>>{//Serializar para qual tipo ? (Page)

	@Override
	public void serialize(Page<?> page, JsonGenerator gen, 
			SerializerProvider serializers) throws IOException {
		//Serializador Page 		
		
		gen.writeStartObject();//Começa aí pra mim um objeto
		
		gen.writeObjectField("content", page.getContent());//Escreve aí pra mim uma propriedade de objeto. O nome da propriedade é content e o conteúdo é page.getContent()
		gen.writeNumberField("size", page.getSize());//Número de páginas
		gen.writeNumberField("totalElements", page.getTotalElements());
		gen.writeNumberField("totalPages", page.getTotalPages());
		gen.writeNumberField("number", page.getNumber());
				
		gen.writeEndObject();//Encerra o objeto
		
		
	}
	
	

}
