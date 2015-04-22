package cz.vutbr.fit.simulatormanager.components;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

/**
 * Class for adding a horizontal line
 * 
 * @author zhenia
 *
 */
public class Hr extends Label {
    private static final long serialVersionUID = 1L;

    Hr() {
	super("<hr/>", ContentMode.HTML);
    }
}
