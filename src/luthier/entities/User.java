package luthier.entities;

public class User {
	public Integer id;
	public String firstName;
	public String lastName;
	public String email;
	public String name;
	public String password;
	public String role;
	
	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = "client";
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
}
