package luthier.entities;

public class Notification {
	public Integer id;
	public String message;
	
	public Notification(String message) {
		this.message = message;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
}
