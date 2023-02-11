package com.algaworks.algafoods.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoods.api.openapi.controller.EstatisticaControlerOpenApi;
import com.algaworks.algafoods.domain.model.dto.VendaDiaria;
import com.algaworks.algafoods.domain.model.filter.VendaDiariaFilter;
import com.algaworks.algafoods.domain.service.VendaQueryService;
import com.algaworks.algafoods.domain.service.VendaReportService;



@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController implements EstatisticaControlerOpenApi{
	
	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@GetMapping(value="/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
			@RequestParam(required=false, defaultValue = "+00:00") String timeOffSet){
		return vendaQueryService.consultarVendasDiarias(filtro, timeOffSet);
		
		/*
		 * Required = False - Não é obrigatório passar 
		 * defaultValue = +00:00h - Se o consumidor não passar nada, retornamos no UTC
		 */
		
	}
	
	//Chamando relatório no endpoint
	@GetMapping(value="/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
			@RequestParam(required=false, defaultValue = "+00:00") String timeOffSet){
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffSet);
		
				var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diárias.pdf"); // Significa que esse conteúdo deverá ser baixado
		//pelo consumidor "attachment"
		//Filename = sugestão de nome no download do arquivo
				
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
		/*
		 * Se o consumidor da API chamar o MediaType Json_Value - vem os dados
		 * Se chamar o PDF Value - Vem o relatório
		 */
		
	}
	
	

}
