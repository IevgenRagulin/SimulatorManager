package cm.example.testvaadin.simulatorcommunication;

import java.util.Collection;

import com.example.testvaadin.beans.SimulationBean;
import com.vaadin.data.util.BeanItem;

public class SimulationItem extends BeanItem<SimulationBean> {
	public SimulationItem(SimulationBean bean) {
		super(bean);
		Collection<?> itemPropIds = getItemPropertyIds();
		for (Object prop : itemPropIds) {
			String propeer = (String) prop;
			System.out.println(propeer);
		}
	}

	private static final long serialVersionUID = -7621069271194263988L;

}
