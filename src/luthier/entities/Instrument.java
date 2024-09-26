package luthier.entities;

public class Instrument {
	public Integer id;
	public String name;
	public User user;
	
	public Instrument(String name, User user) {
		this.name = name;
		this.user = user;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
}
