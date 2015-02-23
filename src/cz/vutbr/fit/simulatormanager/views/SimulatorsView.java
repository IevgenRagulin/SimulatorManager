package cz.vutbr.fit.simulatormanager.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import cz.vutbr.fit.simulatormanager.components.MainMenuBar;
import cz.vutbr.fit.simulatormanager.components.SimulatorForm;
import cz.vutbr.fit.simulatormanager.components.SimulatorListSimulatorsView;
import cz.vutbr.fit.simulatormanager.data.DatabaseHelper;
import cz.vutbr.fit.simulatormanager.data.SimulatorCols;
import cz.vutbr.fit.simulatormanager.simulatorcommunication.SimulationStatusProviderSimpleImpl;
import cz.vutbr.fit.simulatormanager.types.PageType;
import cz.vutbr.fit.simulatormanager.util.ResourceUtil;

/**
 * Page on which the user can add/remove/modify simulators information
 * 
 * @author ievgen
 *
 */
@SuppressWarnings("serial")
public class SimulatorsView extends VerticalLayout implements View {
	final static Logger logger = LoggerFactory.getLogger(SimulatorsView.class);

	final static String PING_SUCCESS_MESSAGE = "Success. The selected simulator is up and running";
	final static String PING_FAIL_MESSAGE = "Connection error. The selected simulator is not responding";

	private HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
	private VerticalLayout leftLayout = new VerticalLayout();
	private VerticalLayout rightLayout = new VerticalLayout();
	private SimulatorListSimulatorsView simulatorList;
	private DatabaseHelper dbHelp = new DatabaseHelper();

	private FormLayout editorLayout = new FormLayout();
	private SimulatorForm simulatorForm;
	private Button removeSimulatorButton = new Button("Remove simulator");
	private Button addSimulatorButton = new Button("Add simulator");
	private Navigator navigator;
	private Label selectedSimulatorName = new Label("", ContentMode.HTML);
	private Image ev97Img;
	private MainMenuBar mainMenu;
	private Button pingSimulatorButton = new Button("Ping simulator");

	public Label getSelectedSimulatorName() {
		return selectedSimulatorName;
	}

	public Button getPingSimulatorButton() {
		return pingSimulatorButton;
	}

	public FormLayout getEditorLayout() {
		return editorLayout;
	}

	public SimulatorForm getSimulatorForm() {
		return simulatorForm;
	}

	public SimulatorListSimulatorsView getSimulatorList() {
		return simulatorList;
	}

	public DatabaseHelper getDBHelp() {
		return dbHelp;
	}

	public SimulatorsView(Navigator navigator) {
		logger.info("new SimulatorsView()");
		this.navigator = navigator;
		initSimulatorList();
		initLayout();
		addClickListeners();
	}

	private void initHorizontalSplitPanel() {
		horizontalSplitPanel.addComponent(leftLayout);
		horizontalSplitPanel.addComponent(rightLayout);
	}

	private void initSimulatorList() {
		simulatorList = new SimulatorListSimulatorsView(this);
	}

	private void initLayout() {
		setSizeFull();
		initMenu();
		addComponent(mainMenu);
		addComponent(horizontalSplitPanel);
		setExpandRatio(horizontalSplitPanel, 20);
		initHorizontalSplitPanel();
		initLeftLayout();
		initRightLayout();
	}

	private void initMenu() {
		mainMenu = MainMenuBar.getInstance(navigator,
				PageType.MANAGE_SIMULATORS);
	}

	private void initLeftLayout() {
		initAddSimulatorButton();
		leftLayout.setSizeFull();
		leftLayout.setMargin(new MarginInfo(false, false, true, true));
		leftLayout.addComponent(new Label("<b>Managed simulators</b>",
				ContentMode.HTML));
		leftLayout.addComponent(simulatorList);
		leftLayout.addComponent(addSimulatorButton);
		leftLayout.setExpandRatio(simulatorList, 20);

	}

	private void initAddSimulatorButton() {
		addSimulatorButton.setStyleName("simulatorsAddSimulator");
		addSimulatorButton.setIcon(ResourceUtil.getPlusImgResource());
	}

	private void initRemoveSimulatorButton() {
		removeSimulatorButton.setIcon(ResourceUtil.getMinusImgResource());
	}

	private void initRightLayout() {
		rightLayout.setMargin(new MarginInfo(true, true, true, true));
		rightLayout.addComponent(selectedSimulatorName);
		pingSimulatorButton
				.setDescription("Check if selected simulator is up and running");
		pingSimulatorButton.setVisible(false);
		rightLayout.addComponent(pingSimulatorButton);
		ev97Img = new Image(
				"After selecting simulator on the left, you will be able to configure it here",
				ResourceUtil.getEv97Img());
		ev97Img.setSizeFull();
		rightLayout.addComponent(ev97Img);
		editorLayout.setVisible(false);
		rightLayout.addComponent(editorLayout);
		initSimulatorForm();
		initRemoveSimulatorButton();
		editorLayout.addComponent(removeSimulatorButton);
	}

	private void initSimulatorForm() {
		simulatorForm = new SimulatorForm(this);
	}

	private void addClickListeners() {
		addAddClickListener();
		addRemoveClickListener();
		addPingClickListener();
	}

	private void addPingClickListener() {
		pingSimulatorButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Object simulatorId = simulatorList.getValue();
				String host = simulatorList.getItem(simulatorId)
						.getItemProperty(SimulatorCols.hostname.toString())
						.getValue().toString();
				int port = Integer.valueOf(simulatorList.getItem(simulatorId)
						.getItemProperty(SimulatorCols.port.toString())
						.getValue().toString());

				boolean isRespoding = SimulationStatusProviderSimpleImpl
						.isSimulatorResponding(host, port);
				if (isRespoding) {
					Notification.show(PING_SUCCESS_MESSAGE, "",
							Notification.TYPE_HUMANIZED_MESSAGE);
				} else {
					Notification.show(PING_FAIL_MESSAGE, "",
							Notification.TYPE_WARNING_MESSAGE);
				}

			}
		});
	}

	private void addAddClickListener() {
		addSimulatorButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				simulatorForm.addSimulator();
			}
		});
	}

	private void addRemoveClickListener() {
		removeSimulatorButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				selectedSimulatorName.setVisible(false);
				simulatorForm.removeSimulator();
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

	public Image getRightPanelImage() {
		return ev97Img;
	}

}