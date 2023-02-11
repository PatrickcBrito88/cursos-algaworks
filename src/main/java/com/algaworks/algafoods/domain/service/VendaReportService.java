package com.algaworks.algafoods.domain.service;

import org.springframework.stereotype.Repository;

import com.algaworks.algafoods.domain.model.filter.VendaDiariaFilter;

import net.sf.jasperreports.engine.JasperFillManager;


public interface VendaReportService  {

	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffSet);
	// Os mesmos parâmetros do Json, mas o retorno é diferente
	
}
