package cz.vutbr.fit.simulatormanager.jscomponents.flightcontrols;

import com.vaadin.shared.ui.JavaScriptComponentState;

/**
 * Class for javascript-java communication of FlightControls component
 * 
 * @author zhenia
 *
 */
@SuppressWarnings("serial")
public class FlightControlsState extends JavaScriptComponentState {
    // the variable names are so short so that we pass less data through the
    // network
    // simulator configuration
    public int maxonflaps; // max speed on flaps
    public int numoflandg; // number of landing gears

    public float ail; // aileron
    public float el; // elevator
    public float rd; // rudder
    public float sb; // speed brakes
    public boolean b; // brakes
    public float fl; // flaps
    public float ailt; // aileron trim
    public float elt; // elevator trim
    public float rdt; // rudder trim
    public boolean p;// paused
    public int landg_1;// landing gear 1
    public int landg_2;// landing gear 2
    public int landg_3;// landing gear 3
    public int[] test;// landing gear 3

}
