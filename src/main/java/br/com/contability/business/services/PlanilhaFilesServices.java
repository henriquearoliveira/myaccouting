package br.com.contability.business.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import br.com.contability.business.Lancamento;
import br.com.contability.exceptions.ObjetoInexistenteException;
import br.com.contability.utilitario.CaixaDeFerramentas;

@Component
public class PlanilhaFilesServices {

	public List<Lancamento> configuraFileToObject(File file) {
		
		List<Lancamento> lancamentos = new ArrayList<>();
		
		FileInputStream fileInputStream = null;
		
		fileInputStream = criaInputStream(file, fileInputStream);
		
		Sheet firstSheet = criaFileSheetConformeExtensao(file, fileInputStream);
		
		Iterator<Row> iteratorRow = firstSheet.iterator();
		
		preencheObjetoLancamento(lancamentos, iteratorRow);
		
		return lancamentos;
	}

	private Sheet criaFileSheetConformeExtensao(File file, FileInputStream fileInputStream) {
		
		String nameFile = file.getName();
		
		String extensao = nameFile.substring(nameFile.lastIndexOf(".") + 1, nameFile.length());
		
		Workbook workbook = null;
		
		if (extensao.equals("xls")) {
			
			workbook = criaWorkBookXLS(fileInputStream, workbook);
			
		} else if (extensao.equals("xlsx")) {
			
			workbook = criaWorkBookXLSX(fileInputStream, workbook);
			
		} else {
			throw new ObjetoInexistenteException();
		}
		
		
		Sheet firstSheet = workbook.getSheetAt(0);
		
		return firstSheet;
	}

	private void preencheObjetoLancamento(List<Lancamento> lancamentos, Iterator<Row> iteratorRow) {
		while (iteratorRow.hasNext()) {
			
			Row nextRow = (Row) iteratorRow.next();
			
			if (pulaLinhaSeNecessario(nextRow)) {
				continue;
			}
			
			Iterator<Cell> cellIterator = nextRow.iterator();
			
			Lancamento lancamento = new Lancamento();
			
			while (cellIterator.hasNext()) {
			
				Cell nextCell = (Cell) cellIterator.next();
				
				int columnIndex = nextCell.getColumnIndex();
				
				switch (columnIndex) {

				case 0:
					
					Calendar date = DateUtil.getJavaCalendar((double) getCellValue(nextCell));
					lancamento.setDataHoraLancamento(CaixaDeFerramentas.converteDateToLocalDate(date.getTime()));
					break;
					
				case 1:
					lancamento.setDescricao((String) getCellValue(nextCell));
					break;
					
				case 2:
					
					BigDecimal valorLancamento = configuraValorLancamento(nextCell);
					lancamento.setValorLancamento(valorLancamento);
					break;
					
				default:
					break;
				}
			}
			
			lancamentos.add(lancamento);
			
		}
	}

	/**
	 * @param nextCell
	 * @return
	 */
	private BigDecimal configuraValorLancamento(Cell nextCell) {
		
		Double valorExcel = (Double) getCellValue(nextCell);
		
		BigDecimal valorLancamento = BigDecimal.valueOf(valorExcel);
		return valorLancamento;
	}

	/**
	 * @param nextRow
	 * @return
	 */
	private boolean pulaLinhaSeNecessario(Row nextRow) {
		return nextRow.getRowNum() == 0;
	}

	private Workbook criaWorkBookXLSX(FileInputStream fileInputStream, Workbook workbook) {
		try {
			workbook = new XSSFWorkbook(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}
	
	private Workbook criaWorkBookXLS(FileInputStream fileInputStream, Workbook workbook) {
		try {
			workbook = new HSSFWorkbook(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	private FileInputStream criaInputStream(File file, FileInputStream fileInputStream) {
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileInputStream;
	}
	
	/**
	 * @param cell
	 * @return
	 */
	private Object getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();
	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	    }
	 
	    return null;
	}
	
	

}
