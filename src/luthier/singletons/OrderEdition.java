package luthier.singletons;

import luthier.entities.Order;

public class OrderEdition {
	 private static OrderEdition instance;
	    private Order orderEdition;

	    private OrderEdition() { }

	    public static OrderEdition getInstance() {
	        if (instance == null) {
	        	instance = new OrderEdition();
	        }
	        return instance;
	    }

	    public Order getOrderEdition() {
	        return orderEdition;
	    }

	    public void setOrderEdition(Order order) {
	        this.orderEdition = order;
	    }	 
	    
	    public void clear() {
	    	orderEdition = null;
	    }
}
