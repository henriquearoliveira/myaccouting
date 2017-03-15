package br.com.contability.comum;

public interface IServices<T extends BeanIdentificavel> {
	
	default T atualiza(T objeto, Boolean webOrApi){return null;};

	default T get(Long id, Boolean webOrApi){return null;}
	
	default T insere(T objeto, Boolean webOrApi){return null;}
	
	default void remove(Long id, Boolean webOrApi){}
	
	default void verificaExistencia(T objeto, Boolean webOrApi){
		get(objeto.getId(), webOrApi);
	}
	
}
