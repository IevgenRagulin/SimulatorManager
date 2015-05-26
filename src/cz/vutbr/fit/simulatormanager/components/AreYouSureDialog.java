package cz.vutbr.fit.simulatormanager.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;

/**
 * Dialog which is used for asking the user if he really wants to do something
 * 
 * @author zhenia
 *
 */
@SuppressWarnings("serial")
public class AreYouSureDialog extends Window {

    /**
     * 
     * @param yesListener
     *            - this listener is used when user clicks "yes"
     */
    public AreYouSureDialog(Button.ClickListener yesListener) {
	super("Really delete?");
	this.setModal(true);
	HorizontalLayout layout = new HorizontalLayout();
	Button yesButton = new Button("Yes");
	Button noButton = new Button("No");
	layout.addComponent(yesButton);
	layout.addComponent(noButton);
	layout.setComponentAlignment(yesButton, Alignment.MIDDLE_LEFT);
	layout.setComponentAlignment(noButton, Alignment.MIDDLE_RIGHT);
	layout.setMargin(true);
	layout.setWidth("200px");
	layout.setHeight("100px");
	this.setContent(layout);
	this.setResizable(false);
	yesButton.addClickListener(yesListener);
	yesButton.addClickListener(getCloseListener());
	noButton.addClickListener(getCloseListener());
    }

    private Button.ClickListener getCloseListener() {
	return new Button.ClickListener() {
	    @Override
	    public void buttonClick(ClickEvent event) {
		AreYouSureDialog.this.close();
	    }
	};
    }
}
