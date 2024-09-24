package luthier.entities;

public class Instrument {
	public Integer id;
	public String name;
	
	public Instrument(String name) {
		this.name = name;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
}
