package luthier.repositories.interfaces;

import luthier.entities.Instrument;

public interface IInstrumentRepository {
	public void inserir(Instrument conta);
	public void remover(String numero);
	public Instrument procurar(String numero);
	public Instrument[] listar();
	public int tamanho();
}
