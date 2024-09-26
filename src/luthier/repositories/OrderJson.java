package luthier.repositories;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import luthier.entities.Order;
import luthier.repositories.interfaces.IOrderRepository;

public class OrderJson implements IOrderRepository {
	private Gson gson = new Gson();
	private String pathArquivo = "/Users/samue/eclipse-workspace/Luthier/src/luthier/json/orders.json";

	private Vector<Order> getAll() {
		try {
			FileReader arquivo = new FileReader(pathArquivo);
			BufferedReader leitor = new BufferedReader(arquivo);
			String conteudo = "";
			String line;

			while ((line = leitor.readLine()) != null) {
				conteudo += line;
			}

			leitor.close();

			Type ordersVector = new TypeToken<Vector<Order>>() {
			}.getType();

			Vector<Order> orders = gson.fromJson(conteudo, ordersVector);
			if (orders == null) {
				return new Vector<Order>();
			}

			return orders;
		} catch (IOException e) {
			e.printStackTrace();
		}

		Vector<Order> arrayVazio = new Vector<Order>();
		return arrayVazio;
	}

	private void rewriteDb(Vector<Order> contas) {
		String ordersJson = gson.toJson(contas);

		try {
			FileWriter arquivo = new FileWriter(pathArquivo);
			PrintWriter gravador = new PrintWriter(arquivo);
			gravador.println(ordersJson);
			gravador.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void add(Order instrument) {
		Vector<Order> orders = getAll();
		instrument.setId(orders.size());
		orders.add(instrument);
		rewriteDb(orders);
	}

	public Order find(Integer id) {
		Vector<Order> orders = getAll();

		for (Order order : orders) {
			if (order.id.equals(id)) {
				return order;
			}
		}

		return null;
	}

	public void edit(Integer id, Order orderEdition) {
		Vector<Order> orders = getAll();
		Integer index = -1;

		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).id.equals(id)) {
				index = i;
			}
		}

		orders.set(index, orderEdition);
		rewriteDb(orders);
	}

	public Order[] list(Integer id) {
		Vector<Order> orders = getAll();
		List<Order> ordersFromClient = (List<Order>) orders.stream().filter(order -> order.user.id.equals(id))
				.collect(Collectors.toList());

		Order[] orderArray = new Order[ordersFromClient.size()];

		for (int i = 0; i < ordersFromClient.size(); i++) {
			orderArray[i] = ordersFromClient.get(i);
		}

		return orderArray;
	}

	public Order[] list() {
		Vector<Order> orders = getAll();
		
		if(orders.size() == 0) return new Order[0];
		
		Order[] ordersArray = new Order[orders.size()];

		for (int i = 0; i < orders.size(); i++) {
			ordersArray[i] = orders.get(i);
		}

		return ordersArray;
	}

	public int size() {
		Vector<Order> orders = getAll();
		return orders.size();
	}

	@Override
	public void delete(Integer id) {
		Vector<Order> orders = getAll();
		int index = -1;

		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).id.equals(id)) {
				index = i;
			}
		}

		orders.remove(index);
		rewriteDb(orders);
	}
}
