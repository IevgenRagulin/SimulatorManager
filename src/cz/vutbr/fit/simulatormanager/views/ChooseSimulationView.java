package cz.vutbr.fit.simulatormanager.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import cz.vutbr.fit.simulatormanager.SimulatormanagerUI;
import cz.vutbr.fit.simulatormanager.components.MainMenuBar;
import cz.vutbr.fit.simulatormanager.components.SimulationList;
import cz.vutbr.fit.simulatormanager.components.SimulatorListChooseSimulationView;
import cz.vutbr.fit.simulatormanager.data.DatabaseHelper;
import cz.vutbr.fit.simulatormanager.data.SimulationCols;
import cz.vutbr.fit.simulatormanager.types.PageType;
import cz.vutbr.fit.simulatormanager.util.ResourceUtil;

/**
 * Top layout contains 2 elements: menu and VerticalLayout which contains
 * everything else
 * 
 * @author ievgen
 *
 */
public class ChooseSimulationView extends VerticalLayout implements View {

	private static final String CSS_SM_CHOOSE_SIM_BUTTON = "sm-choose-sim-button";

	final static Logger logger = LoggerFactory
			.getLogger(ChooseSimulationView.class);

	private VerticalLayout mainLayout = new VerticalLayout();
	public static final String SIMULATIONS_ON_SELECTED_SIMULATOR = "<p style='text-align: center;'><b>Simulation sessions on the selected simulator</b></p>";
	public static final String NO_SIMULATIONS_ON_SELECTED_SIMULATOR = "<p style='text-align: center;'><b>There are no simulations on the selected simulator</b></p>";
	private static final long serialVersionUID = 1279140216863014337L;
	private Navigator navigator;
	private SimulationList simulationList;
	private SimulatorListChooseSimulationView simulatorList;
	private HorizontalSplitPanel bottomVerticalSplitPanel = new HorizontalSplitPanel();
	private VerticalLayout actionButtons = new VerticalLayout();
	private Button deleteButton = new Button("Delete chosen simulation");
	private Button viewSimButton = new Button("View chosen simulation");
	private Button finishSimButton = new Button("Finish chosen simulation");
	protected DatabaseHelper dbHelp = new DatabaseHelper();
	MainMenuBar mainMenu;
	Label simulationSessionsLabel;
	private Image bottomImage;

	@Override
	public void enter(ViewChangeEvent event) {
		logger.info("Entering ChooseSimulationView");
		setSizeFull();
		initMenu();
		// in case we added/removed simulators on simulator management page, the
		// simulators list on this page needs to be updated
		simulatorList.updateSimulatorsList();
	}

	public DatabaseHelper getDBHelp() {
		return dbHelp;
	}

	public ChooseSimulationView(Navigator navigator) {
		super();
		logger.info("new ChooseSimulationView()");
		this.navigator = navigator;
		mainLayout.setMargin(new MarginInfo(false, true, false, true));
		initMenu();
		initSimulatorList();
		initSimulationList();
		initLayout();
		addClickListeners();
		setMargin(new MarginInfo(false, false, false, false));
	}

	private void initMenu() {
		mainMenu = MainMenuBar.getInstance(navigator,
				PageType.CHOOSE_SIMULATION);
		mainMenu.setHeight("35px");
	}

	private void addClickListeners() {
		deleteButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 2511131902775324917L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					simulationList.removeSimulation();
				} catch (Exception e) {
					throw new RuntimeException("Could not remove simulation", e);
				}
			}
		});

		finishSimButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				simulationList.finishSimulation();
			}
		});

		viewSimButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 2273135571079398635L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (isSelectedSimulationRunning()) {
					navigator.navigateTo(SimulatormanagerUI.RUNNINGSIMULATIONS
							+ "/" + getSimulatorList().getValue());
				} else {
					navigator.navigateTo(SimulatormanagerUI.PASTSIMULATIONS
							+ "/" + getSimulationList().getValue());
				}
			}
		});

	}

	public boolean isSelectedSimulationRunning() {
		Item item = getSimulationList().getItem(getSimulationList().getValue());
		return (Boolean) item.getItemProperty(
				SimulationCols.issimulationon.toString()).getValue();
	}

	private void initLayout() {
		addComponent(mainMenu);
		addComponent(mainLayout);
		mainLayout
				.addComponent(new Label(
						"<p style='text-align: center; margin-left: 20px'><b>Select a simulator</b></p>",
						ContentMode.HTML));
		mainLayout.addComponent(simulatorList);
		bottomImage = new Image(
				"After choosing a simulator on top, a list of running/finished simulation sessions will be displayed here",
				ResourceUtil.getBoeing737Img());
		bottomImage.setSizeFull();
		mainLayout.addComponent(bottomImage);
		simulationSessionsLabel = new Label(SIMULATIONS_ON_SELECTED_SIMULATOR,
				ContentMode.HTML);
		simulationSessionsLabel.setVisible(false);
		mainLayout.addComponent(simulationSessionsLabel);
		mainLayout.addComponent(bottomVerticalSplitPanel);
		bottomVerticalSplitPanel.setSizeFull();
		VerticalLayout bottomLeftLayout = new VerticalLayout();
		bottomVerticalSplitPanel.addComponent(bottomLeftLayout);
		bottomLeftLayout.addComponent(simulationList);
		bottomVerticalSplitPanel.addComponent(actionButtons);
		bottomVerticalSplitPanel.setSplitPosition(70);
		actionButtons.setVisible(false);
		deleteButton
				.setDescription("Deletes data about chosen simulation from the database");
		actionButtons.addComponent(deleteButton);
		viewSimButton
				.setDescription("Look at the flight instruments of finsihed or currently running simulation");
		actionButtons.addComponent(viewSimButton);
		finishSimButton
				.setDescription("Can be applied to a running simulation. "
						+ "Mark this simulation as finished in the database. "
						+ "Information about currently running simulation will be saved to a new simulation "
						+ "session (new row in this table)");
		actionButtons.addComponent(finishSimButton);

		actionButtons.setComponentAlignment(deleteButton, Alignment.TOP_CENTER);
		actionButtons.setComponentAlignment(viewSimButton,
				Alignment.MIDDLE_CENTER);
		actionButtons.setComponentAlignment(finishSimButton,
				Alignment.BOTTOM_CENTER);
		actionButtons.setSizeFull();

		setExpandRatio(mainMenu, 1);
		setExpandRatio(mainLayout, 20);
		simulatorList.setHeight("350px");
		bottomImage.setSizeFull();
		bottomVerticalSplitPanel.setSizeFull();
		bottomVerticalSplitPanel.setVisible(false);
		setStyleNames();
	}

	private void setStyleNames() {
		deleteButton.setStyleName(CSS_SM_CHOOSE_SIM_BUTTON);
		viewSimButton.setStyleName(CSS_SM_CHOOSE_SIM_BUTTON);
		finishSimButton.setStyleName(CSS_SM_CHOOSE_SIM_BUTTON);
	}

	private void initSimulationList() {
		logger.info("Going to init simulation list");
		simulationList = new SimulationList(this);
	}

	private void initSimulatorList() {
		logger.info("Going to init simulator list");
		simulatorList = new SimulatorListChooseSimulationView(this);
	}

	public SimulatorListChooseSimulationView getSimulatorList() {
		return simulatorList;
	}

	public VerticalLayout getActionButtons() {
		return actionButtons;
	}

	public Button getFinishSimulationButton() {
		return finishSimButton;
	}

	public SimulationList getSimulationList() {
		return simulationList;
	}

	public Label getSimulationSessionsLabel() {
		return simulationSessionsLabel;
	}

	public Image getBottomImage() {
		return bottomImage;
	}

	public HorizontalSplitPanel getBottomVerticalSplitPanel() {
		return bottomVerticalSplitPanel;
	}

}
