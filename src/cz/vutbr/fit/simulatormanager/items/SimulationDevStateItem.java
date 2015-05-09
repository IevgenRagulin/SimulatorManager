package cz.vutbr.fit.simulatormanager.items;

import com.vaadin.data.util.BeanItem;

import cz.vutbr.fit.simulatormanager.beans.SimulationDevStateBean;

/**
 * Wrapper for SimulationDevStateBean providing Item interface to the Bean.
 * Probably, used for persisting data
 * 
 * @author zhenia
 *
 */
public class SimulationDevStateItem extends BeanItem<SimulationDevStateBean> {
    private static final long serialVersionUID = -5775678049419522714L;

    public SimulationDevStateItem(SimulationDevStateBean bean) {
	super(bean);
    }

}
