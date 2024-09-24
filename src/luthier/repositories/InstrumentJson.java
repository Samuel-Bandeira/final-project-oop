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
import luthier.entities.Instrument;
import luthier.repositories.interfaces.IInstrumentRepository;

public class InstrumentJson implements IInstrumentRepository {
	private Gson gson = new Gson();
	private String pathArquivo = "/Users/samue/eclipse-workspace/Luthier/src/luthier/json/instruments.json";

	private Vector<Instrument> getInstruments() {
		try {
			FileReader arquivo = new FileReader(pathArquivo);
			BufferedReader leitor = new BufferedReader(arquivo);
			String conteudo = "";
			String line;

			while ((line = leitor.readLine()) != null) {
				conteudo += line;
			}
			
			leitor.close();

			Type contasVector = new TypeToken<Vector<Instrument>>() {
			}.getType();

			Vector<Instrument> contas = gson.fromJson(conteudo, contasVector);
			if(contas == null) {
				return new Vector<Instrument>();
			}
			
			return contas;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Vector<Instrument> arrayVazio = new Vector<Instrument>();
		return arrayVazio;
	}

	private void reescreverDb(Vector<Instrument> contas) {		
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
	
	public void inserir(Instrument instrument) {
		Vector<Instrument> contas = getInstruments();
		instrument.setId(contas.size());
		contas.add(instrument);
		reescreverDb(contas);
	}

	public Instrument procurar(String name) {
		Vector<Instrument> instruments = getInstruments();

		for (Instrument instrument : instruments) {
			if (instrument.name.equals(name)) {
				return instrument;
			}
		}

		return null;
	}

	public void remover(String id) {
		Vector<Instrument> contas = getInstruments();
		
		Instrument conta = procurar(id);
		int index = -1;

		if (conta != null) {
			for (int i = 0; i < contas.size(); i++) {
				if (contas.get(i).id.equals(id)) {
					index = i;
					break;
				}
			}
			
			if(index >= 0) {
				contas.remove(index);
			}
			
			reescreverDb(contas);
		}
	}

	public Instrument[] listar() {
		Vector<Instrument> instruments = getInstruments();
		Instrument[] instrumentsArray = new Instrument[instruments.size()];
		
		for(int i = 0; i < instruments.size(); i++) {
			instrumentsArray[i] = instruments.get(i);
		}
		
		return instrumentsArray;
	}

	public int tamanho() {
		Vector<Instrument> contas = getInstruments();
		return contas.size();
	}
}
