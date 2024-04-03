package br.com.contability.business.services;

import br.com.contability.comum.BeanIdentificavel;
import br.com.contability.comum.IServices;
import br.com.contability.comum.ShaPasswordEncoder;
import br.com.contability.exceptions.ObjetoInexistenteException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LeitorPlanilhasExcel<T extends BeanIdentificavel, E extends IServices<T>>
        implements LeitorPlanilhaStrategy<T, E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeitorPlanilhasExcel.class);

    private E servicesAbstract;

    public LeitorPlanilhasExcel(E services) {
        this.servicesAbstract = services;
    }

    @Override
    public List<T> configuraFileToObject(File file) {

        final List<T> objetos = new ArrayList<>();

        final FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new ObjetoInexistenteException();
        }

        final Sheet firstSheet = criaFileSheetConformeExtensao(file, fileInputStream);
        final Iterator<Row> iteratorRow = firstSheet.iterator();

        servicesAbstract.preencheObjetoPlanilhaExcel(objetos, iteratorRow);
        return objetos;

    }

    private Sheet criaFileSheetConformeExtensao(File file, FileInputStream fileInputStream) {

        final String nameFile = file.getName();
        final String extensao = nameFile.substring(nameFile.lastIndexOf(".") + 1);

        Workbook workbook = null;

        if (extensao.equals("xls")) {

            workbook = criaWorkBookXLS(fileInputStream, workbook);

        } else if (extensao.equals("xlsx")) {

            workbook = criaWorkBookXLSX(fileInputStream, workbook);

        } else {
            throw new ObjetoInexistenteException();
        }

        return workbook.getSheetAt(0);
    }

    private Workbook criaWorkBookXLSX(FileInputStream fileInputStream, Workbook workbook) {
        try {
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            LOGGER.error("Erro no criar book xlsx", e);
        }
        return workbook;
    }

    private Workbook criaWorkBookXLS(FileInputStream fileInputStream, Workbook workbook) {
        try {
            workbook = new HSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            LOGGER.error("Erro no criar book xls", e);
        }
        return workbook;
    }

}
