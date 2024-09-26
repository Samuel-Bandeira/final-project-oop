package luthier.entities;

public abstract class UserAbstract {
	public Integer id;
	public String firstName;
	public String lastName;
	public String email;
	public String name;
	public String password;
	public String role;
	
	public UserAbstract(String firstName, String lastName, String email, String password, String luthierOrClientOption) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = luthierOrClientOption;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
}
