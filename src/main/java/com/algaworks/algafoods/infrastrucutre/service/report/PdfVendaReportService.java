package com.algaworks.algafoods.infrastrucutre.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.algaworks.algafoods.domain.model.filter.VendaDiariaFilter;
import com.algaworks.algafoods.domain.service.VendaQueryService;
import com.algaworks.algafoods.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Repository
public class PdfVendaReportService implements VendaReportService{

	@Autowired
	private VendaQueryService vendaQueryService;
	
	
	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffSet) {
		try {
			var inputStream = this.getClass().getResourceAsStream(
					"/relatorios/vendas-diarias.jasper");//Origem do arquivo compilado
			
			var parametros = new HashMap<String, Object>();
			parametros.put("REPORT_LOCALE", new Locale("pt", "BR")); // Parametros que irão para dentro do relatório
			
			var vendasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffSet); //Geração da fonte de dados
			var dataSource = new JRBeanCollectionDataSource(vendasDiarias); // Fonte de dados
			
			var	jasperPrint = JasperFillManager.fillReport(inputStream, parametros,dataSource);
			
			
			return JasperExportManager.exportReportToPdf(jasperPrint);
			
		} catch (Exception e) {
			throw new ReportException("Não foi possível emitir o relatório de vendas diárias.",e);
		}
	}

}
