# Especialista Spring Rest - Algafoods API
 
### Resumo
Esta API gerencia o cadastro de clientes, restaurantes e cozinhas, bem como a realização de pedidos com emissão de relatórios, informação sobre status e envio de e-mails.
Cada restaurante pertence a uma única cozinha associada e vende produtos específicos.

## Tecnologias utilizadas
* Java
* Spring Boot
* JPA/Hibernate
* Mysql
* Flyway
* ModelMapper
* Rest Assured
* Squiggly
* Jasper Reports
* S3 (Amazon)
* Freemaker
* Hateoas
* Loggly

## Recursos disponíveis para acesso via API:
* [**Cozinhas**]
* [**Restaurantes**]
* [**Clientes**]
* [**Formas de Pagamento**]
* [**Pedidos**]


## Métodos
Requisições para a API devem seguir os padrões:
| Método | Descrição |
|---|---|
| GET | Retorna informações de um ou mais registros |
| POST | Utilizado para criar um novo registro |
| PUT | Atualiza dados de um registro ou altera sua situação |
| DEL | Remove um registro do sistema |
| PATCH | Utilizado para atualiação parcial de um registro |

## Respostas
| Código | Descrição |
|---|---|
| 200 | Requisição executada com sucesso |
| 201 | Incluído no Banco de Dados com sucesso |
| 400 | Erros de validação |
| 404 | Registro pesquisado não encontrado |
| 409 | O registro não pode ser deletado pois viola regra de negócio |
| 500 | Erro de Sistema. Contactar administrador do sistema |

## Requisições

### http://api.algafood.local:8080/swagger-ui.html

## Testes automatizados implementados

### Cozinha
* Retorno de status http 200 quando consultar cozinhas
* Retorno de status http 404 quando cozinha não encontrada
* Retorno de status http 201 quando cozinha cadastrada
* Verificação do número de cozinhas cadastradas

### Restaurante
* Retorno de status http 200 quando consultar restaurantes
* Retorno de status http 404 quando restaurante não encontrado
* Retorno de status http 201 quando restaurante cadastrado
* Retorno de status http 400 quando tentar cadastrar restaurante sem cozinha associada
* Retorno de status http 400 quando tentar cadastrar restaurante sem taxa frete associada

