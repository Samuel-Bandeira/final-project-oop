package luthier.repositories.interfaces;

import luthier.entities.Order;

public interface IOrderRepository {
	public void inserir(Order conta);
	public void remover(String numero);
	public Order procurar(String numero);
	public Order[] listar();
	public int tamanho();
}
