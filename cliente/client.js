function consultarRestaurantes() {
    $.ajax({
      url: "http://api.algafood.local:8080/cozinhas",
      type: "get",
  
      success: function(response) {
        $("#conteudo").text(JSON.stringify(response));
      }
    });
  }
  
  $("#botao").click(consultarRestaurantes);

function fecharRestaurante() {
    $.ajax({
      url: "http://api.algafood.local:8080/restaurantes/1/fechamento",
      type: "put",
  
      success: function(response) {
        alert("Restaurante foi fechado");
      }
    });
  }
  
  //$("#botao").click(fecharRestaurantes);