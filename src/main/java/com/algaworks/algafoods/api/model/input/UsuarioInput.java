package com.algaworks.algafoods.api.model.input;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {
	
	@ApiModelProperty(value="Nome de um usuário", example="Pedro", required=true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(value="E-mail de um usuário", example="fulano@gmail.com", required=true)
	@NotBlank
	@Email
	private String email;
	
}
