package br.com.contability.comum;

public interface IServices<T extends BeanIdentificavel> {
	
	default T atualiza(T objeto){return null;};

	default T get(Long id){return null;}
	
	default T insere(T objeto){return null;}
	
	default void remove(Long id){}
	
	default void verificaExistencia(T objeto){
		get(objeto.getId());
	}
	
}
