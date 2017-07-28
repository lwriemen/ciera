package microwaveovenciera.components.microwaveoven.microwaveoven.turntable.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class Spin extends Event {
    
    private static final int eventId = 0;
    private static final int eventNumber = 1;
    private static final int classNumber = 6;
    private static final String eventName = "Spin";

    public Spin() {
    }

    public Spin( EventTarget t, boolean ts ) {
        super( t, ts );
    }

    @Override
    public int getEventId() {
        return eventId;
    }

    @Override
    public int getEventNumber() {
        return eventNumber;
    }

    @Override
    public int getClassNumber() {
        return classNumber;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

}
