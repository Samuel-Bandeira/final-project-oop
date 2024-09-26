package luthier.repositories.interfaces;
import luthier.entities.User;

public interface IUserRepository {
	public void add(User conta);
	public void remove(Integer id);
	public User find(String email) throws Exception;
	public User find(Integer id);
	public User[] list();
	public User[] listClients();
	public int size();
}
