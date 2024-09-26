package luthier.entities;

public class User extends UserAbstract {
	public User(String firstName, String lastName, String email, String password, String luthierOrClientOption) {
		super(firstName, lastName, email, password, luthierOrClientOption);
	}
}
