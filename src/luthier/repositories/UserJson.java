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
import luthier.entities.User;
import luthier.entities.UserLuthier;
import luthier.repositories.interfaces.IUserRepository;

public class UserJson implements IUserRepository {
	private Gson gson = new Gson();
	private String pathArquivo = "/Users/samue/eclipse-workspace/Luthier/src/luthier/json/users.json";

	private Vector<User> getAll() {
		try {
			FileReader arquivo = new FileReader(pathArquivo);
			BufferedReader leitor = new BufferedReader(arquivo);
			String conteudo = "";
			String line;

			while ((line = leitor.readLine()) != null) {
				conteudo += line;
			}
			
			leitor.close();
			
			Type users = new TypeToken<Vector<UserLuthier>>() {
			}.getType();

			Vector<User> contas = gson.fromJson(conteudo, users);
			if(contas == null) {
				return new Vector<User>();
			}
			
			return contas;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Vector<User> arrayVazio = new Vector<User>();
		return arrayVazio;
	}

	private void rewriteDb(Vector<User> contas) {		
		String usersJson = gson.toJson(contas);

		try {
			FileWriter arquivo = new FileWriter(pathArquivo);
			PrintWriter gravador = new PrintWriter(arquivo);
			gravador.println(usersJson);
			gravador.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void add(User user) {
		Vector<User> users = getAll();
		user.setId(users.size());
		users.add(user);
		System.out.println("Users " + users.get(0).name);
		rewriteDb(users);
	}

	public User find(Integer id) {
		Vector<User> users = getAll();

		for (User user : users) {
			if (user.id.equals(id)) {
				return user;
			}
		}

		return null;
	}
	
	public User find(String email) throws Exception {
		Vector<User> users = getAll();
		
		for (User user : users) {
			if (user.email.equals(email)) {
				return user;
			}
		}
		
		throw new Exception("Nenhum usuário com esse email encontrado");
	}

	public void remove(Integer id) {
		Vector<User> users = getAll();
		
		User conta = find(id);
		int index = -1;

		if (conta != null) {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).id.equals(id)) {
					index = i;
					break;
				}
			}
			
			if(index >= 0) {
				users.remove(index);
			}
			
			rewriteDb(users);
		}
	}

	public User[] list() {
		Vector<User> users = getAll();
		User[] instrumentsArray = new User[users.size()];
		
		for(int i = 0; i < users.size(); i++) {
			instrumentsArray[i] = users.get(i);
		}
		
		return instrumentsArray;
	}
	
	public User[] listClients() {
		Vector<User> users = getAll();
		
		List<User> clientUsers = (List<User>) users.stream().filter(user -> user.role.equals("cliente"))
				.collect(Collectors.toList());
		User[] clientsArray = new User[clientUsers.size()];
		
		for(int i = 0; i < clientUsers.size(); i++) {
			clientsArray[i] = clientUsers.get(i);
		}
		
		return clientsArray;
	}

	public int size() {
		Vector<User> users = getAll();
		return users.size();
	}
}
