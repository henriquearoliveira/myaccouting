package br.com.contability.teste.lambdaCalcula;

@FunctionalInterface
public interface Compute<T> {
	
	T calcula(T v1, T v2);
}
