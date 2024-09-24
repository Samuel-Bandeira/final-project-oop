package luthier.entities;

public class Order {
	public Integer id;
	public User user;
	public Instrument instrument;
	
	public Order(User user, Instrument instrument) {
		this.user = user;
		this.instrument = instrument;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
}
