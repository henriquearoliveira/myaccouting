package br.com.contability.business.services;

import br.com.contability.comum.BeanIdentificavel;
import br.com.contability.comum.IServices;
import br.com.contability.utilitario.CaixaDeFerramentas;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeitorPlanilhasLibreOffice<T extends BeanIdentificavel, E extends IServices<T>>
        implements LeitorPlanilhaStrategy<T, E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeitorPlanilhasLibreOffice.class);

    private E servicesAbstract;

    /**
     * @param services
     */
    public LeitorPlanilhasLibreOffice(E services) {
        this.servicesAbstract = services;
    }

    @Override
    public List<T> configuraFileToObject(File file) {

        final List<T> objetos = new ArrayList<>();

        try {
            final Sheet sheet = SpreadSheet.createFromFile(file).getSheet(0);
            servicesAbstract.preencheObjetoPlanilhaLibreOffice(objetos, sheet);
        } catch (IOException e) {
            LOGGER.error("Falha na configuração de objetos da planilha Office", e);
        }

        return objetos;
    }
}