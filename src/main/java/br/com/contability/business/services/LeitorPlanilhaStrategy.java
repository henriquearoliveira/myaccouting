package br.com.contability.business.services;

import br.com.contability.comum.BeanIdentificavel;
import br.com.contability.comum.IServices;

import java.io.File;
import java.util.List;

public interface LeitorPlanilhaStrategy<T extends BeanIdentificavel, E extends IServices<T>> {

    List<T> configuraFileToObject(File file);
}
