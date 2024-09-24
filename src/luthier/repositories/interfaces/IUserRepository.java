package luthier.repositories.interfaces;

import luthier.entities.User;

public interface IUserRepository {
	public void inserir(User conta);
	public void remover(Integer id);
	public User procurar(String email);
	public User procurar(Integer id);
	public User[] listar();
	public int tamanho();
}
