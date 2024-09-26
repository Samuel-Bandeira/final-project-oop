package luthier.repositories.interfaces;

import java.util.Vector;

import luthier.entities.Order;

public interface IOrderRepository {
	public void add(Order conta);
	public void delete(Integer id);
	public void edit(Integer id, Order orderEdition);
	public Order find(Integer id);
	public Order[] list();
	public Order[] list(Integer id);
	public int size();
}
