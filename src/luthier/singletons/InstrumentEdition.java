package luthier.singletons;

import luthier.entities.Instrument;

public class InstrumentEdition {
	 private static InstrumentEdition instance;
	    private Instrument instrumentEdition;

	    private InstrumentEdition() { }

	    public static InstrumentEdition getInstance() {
	        if (instance == null) {
	        	instance = new InstrumentEdition();
	        }
	        return instance;
	    }

	    public Instrument getInstrumentEdition() {
	        return instrumentEdition;
	    }

	    public void setInstrumentEdition(Instrument order) {
	        this.instrumentEdition = order;
	    }	 
	    
	    public void clear() {
	    	instrumentEdition = null;
	    }
}
