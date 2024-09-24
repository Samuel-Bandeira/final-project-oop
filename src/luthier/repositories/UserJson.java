package luthier.repositories;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import luthier.entities.User;
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

			Type users = new TypeToken<Vector<User>>() {
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

	private void reescreverDb(Vector<User> contas) {		
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
	
	public void inserir(User instrument) {
		Vector<User> users = getAll();
		instrument.setId(users.size());
		users.add(instrument);
		reescreverDb(users);
	}

	public User procurar(Integer id) {
		Vector<User> users = getAll();

		for (User user : users) {
			if (user.id.equals(id)) {
				return user;
			}
		}

		return null;
	}
	
	public User procurar(String email) {
		Vector<User> users = getAll();
		for (User user : users) {
			System.out.println(user.email);
			if (user.email.equals(email)) {
				return user;
			}
		}

		return null;
	}

	public void remover(Integer id) {
		Vector<User> users = getAll();
		
		User conta = procurar(id);
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
			
			reescreverDb(users);
		}
	}

	public User[] listar() {
		Vector<User> users = getAll();
		User[] instrumentsArray = new User[users.size()];
		
		for(int i = 0; i < users.size(); i++) {
			instrumentsArray[i] = users.get(i);
		}
		
		return instrumentsArray;
	}

	public int tamanho() {
		Vector<User> users = getAll();
		return users.size();
	}
}
