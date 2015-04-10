package cz.vutbr.fit.simulatormanager.beans;

import com.vaadin.data.Item;

/**
 * Bean corresponding to simulatormodel table in database
 * 
 * @author zhenia
 *
 */
public class SimulatorModelBean {
    private int enginemodelorder;
    private int minspeed;
    private int maxpeed;
    private int maxspeedonflaps;
    private int minspeedonflaps;
    private boolean hasgears;
    private int numberoflandinggears;
    private boolean lfu;
    private float minlfu;
    private float maxlfu;
    private boolean rfu;
    private float minrfu;
    private float maxrfu;
    private boolean cfu;
    private float mincfu;
    private float maxcfu;

    public SimulatorModelBean(Item simulatorModelItem) {

    }
}
