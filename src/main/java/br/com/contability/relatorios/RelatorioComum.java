package br.com.contability.relatorios;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.util.Map;

@Component
public class RelatorioComum implements Relatorio {

    private final DataSource dataSource;

    public RelatorioComum(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ModelAndView criaRelatorio(Map<String, Object> parametros, String nomeRelatorio) {
        parametros.put("datasource", dataSource);
        parametros.put("format", "pdf");
        return new ModelAndView(nomeRelatorio, parametros);
    }

}
