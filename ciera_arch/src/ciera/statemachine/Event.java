package ciera.statemachine;

import ciera.classes.exceptions.EmptyInstanceException;
import ciera.statemachine.exceptions.SameDataException;

public abstract class Event {
    
    private EventTarget target;
    private boolean toSelf;
    
    public EventTarget getTarget() {
        return target;
    }
    
    public void setTarget( EventTarget t ) {
        target = t;
    }

    public boolean toSelf() {
        return toSelf;
    }
    
    public void setToSelf( boolean ts ) {
        toSelf = ts;
    }
    
    public Object getData( String id ) throws SameDataException {
        return null;
    }
    
    public void generate() throws EmptyInstanceException {
        if ( toSelf() ) target.generateToSelf( this );
        else target.generateTo( this );
    }

    public abstract int getEventNumber();
    public abstract int getClassNumber();

}
