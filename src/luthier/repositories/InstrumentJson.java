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
import luthier.entities.Instrument;
import luthier.entities.Order;
import luthier.repositories.interfaces.IInstrumentRepository;

public class InstrumentJson implements IInstrumentRepository {
	private Gson gson = new Gson();
	private String pathArquivo = "/Users/samue/eclipse-workspace/Luthier/src/luthier/json/instruments.json";

	private Vector<Instrument> getAll() {
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

	private void rewriteDb(Vector<Instrument> contas) {		
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
	
	public void add(Instrument instrument) {
		Vector<Instrument> instruments = getAll();
		instrument.setId(instruments.size());
		instruments.add(instrument);
		rewriteDb(instruments);
	}

	public Instrument find(String name) {
		Vector<Instrument> instruments = getAll();

		for (Instrument instrument : instruments) {
			if (instrument.name.equals(name)) {
				return instrument;
			}
		}

		return null;
	}
	
	public Instrument find(Integer id) {
		Vector<Instrument> instruments = getAll();

		for (Instrument instrument : instruments) {
			if (instrument.id.equals(id)) {
				return instrument;
			}
		}

		return null;
	}

	public void remove(Integer id) {
		Vector<Instrument> instruments = getAll();
		
		Instrument instrument = find(id);
		int index = -1;

		if (instrument != null) {
			for (int i = 0; i < instruments.size(); i++) {
				if (instruments.get(i).id.equals(id)) {
					index = i;
					break;
				}
			}
			
			if(index >= 0) {
				instruments.remove(index);
			}
			
			rewriteDb(instruments);
		}
	}
	
	public void edit(Integer id, Instrument instrument) {
		Vector<Instrument> instruments = getAll();
		Integer index = -1;

		for (int i = 0; i < instruments.size(); i++) {
			if (instruments.get(i).id.equals(id)) {
				index = i;
			}
		}

		instruments.set(index, instrument);
		rewriteDb(instruments);
	}

	public Instrument[] list() {
		Vector<Instrument> instruments = getAll();
		Instrument[] instrumentsArray = new Instrument[instruments.size()];
		
		for(int i = 0; i < instruments.size(); i++) {
			instrumentsArray[i] = instruments.get(i);
		}
		
		return instrumentsArray;
	}
	
	public Instrument[] list(Integer userId) {
		Vector<Instrument> instruments = getAll();
		List<Instrument> instrumentsFromClient = (List<Instrument>) instruments.stream().filter(instrument -> instrument.user.id.equals(userId))
				.collect(Collectors.toList());

		Instrument[] instrumentsArray = new Instrument[instrumentsFromClient.size()];

		for (int i = 0; i < instrumentsFromClient.size(); i++) {
			instrumentsArray[i] = instrumentsFromClient.get(i);
		}

		return instrumentsArray;
	}

	public int size() {
		Vector<Instrument> contas = getAll();
		return contas.size();
	}
}
