package luthier.entities;

public class Order {
	public Integer id;
	public User user;
	public Instrument instrument;
	public String instrumentParts;
	public String serviceType;
	public String status;
	
	public Order(UserAbstract user, Instrument instrument, String instrumentParts, String serviceType, String status) {
		this.user = (User) user;	
		this.instrument = instrument;
		this.instrumentParts = instrumentParts;
		this.serviceType = serviceType;
		this.status = status;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
}
