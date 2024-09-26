package luthier.repositories.interfaces;

import luthier.entities.Instrument;

public interface IInstrumentRepository {
	public void add(Instrument instrument);
	public void remove(Integer id);
	public Instrument find(String name);
	public Instrument find(Integer id);
	public Instrument[] list();
	public Instrument[] list(Integer userId);
	public void edit(Integer id, Instrument instrument);
	public int size();
}
