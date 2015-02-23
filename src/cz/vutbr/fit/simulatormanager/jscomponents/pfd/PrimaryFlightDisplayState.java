package cz.vutbr.fit.simulatormanager.jscomponents.pfd;

import com.vaadin.shared.ui.JavaScriptComponentState;

@SuppressWarnings("serial")
public class PrimaryFlightDisplayState extends JavaScriptComponentState {
	// the variable names are so short so that we pass less data through the
	// network
	public int a;// altitude
	public int s;// speed
	public int r;// roll
	public int p;// pitch
	public int h;// heading
	public int tc;// truecourse
	public int rpfd;// resetpfd
	public float vs;// verticalspeed
}
