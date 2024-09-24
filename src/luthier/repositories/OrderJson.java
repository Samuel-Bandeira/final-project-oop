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

import luthier.entities.Order;

public class OrderJson {
	private Gson gson = new Gson();
	private String pathArquivo = "/Users/samue/eclipse-workspace/Luthier/src/luthier/repositories/instruments.json";

	private Vector<Order> getInstruments() {
		try {
			FileReader arquivo = new FileReader(pathArquivo);
			BufferedReader leitor = new BufferedReader(arquivo);
			String conteudo = "";
			String line;

			while ((line = leitor.readLine()) != null) {
				conteudo += line;
			}
			
			leitor.close();

			Type contasVector = new TypeToken<Vector<Order>>() {
			}.getType();

			Vector<Order> contas = gson.fromJson(conteudo, contasVector);
			if(contas == null) {
				return new Vector<Order>();
			}
			
			return contas;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Vector<Order> arrayVazio = new Vector<Order>();
		return arrayVazio;
	}

	private void reescreverDb(Vector<Order> contas) {		
		String contasJson = gson.toJson(contas);

		try {
			FileWriter arquivo = new FileWriter(pathArquivo);
			PrintWriter gravador = new PrintWriter(arquivo);
			gravador.println(contasJson);
			gravador.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void inserir(Order instrument) {
		Vector<Order> contas = getInstruments();
		instrument.setId(contas.size());
		contas.add(instrument);
		reescreverDb(contas);
	}

//	public Order procurar(String name) {
//		Vector<Order> instruments = getInstruments();
//
//		for (Order instrument : instruments) {
//			if (instrument.name.equals(name)) {
//				return instrument;
//			}
//		}
//
//		return null;
//	}

//	public void remover(String id) {
//		Vector<Order> contas = getInstruments();
//		
//		Order conta = procurar(id);
//		int index = -1;
//
//		if (conta != null) {
//			for (int i = 0; i < contas.size(); i++) {
//				if (contas.get(i).id.equals(id)) {
//					index = i;
//					break;
//				}
//			}
//			
//			if(index >= 0) {
//				contas.remove(index);
//			}
//			
//			reescreverDb(contas);
//		}
//	}

	public Order[] listar() {
		Vector<Order> instruments = getInstruments();
		Order[] instrumentsArray = new Order[instruments.size()];
		
		for(int i = 0; i < instruments.size(); i++) {
			instrumentsArray[i] = instruments.get(i);
		}
		
		return instrumentsArray;
	}

	public int tamanho() {
		Vector<Order> contas = getInstruments();
		return contas.size();
	}
}
