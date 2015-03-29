package cz.vutbr.fit.simulatormanager.views;

import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import cz.vutbr.fit.simulatormanager.SimulatormanagerUI;
import cz.vutbr.fit.simulatormanager.util.ResourceUtil;

public class StartView extends VerticalLayout implements View {

    private static final String BUTTON_MAINPAGE = "button-main-page";
    private static final long serialVersionUID = 7495049073810117367L;
    private HorizontalLayout bottomHorizontalLayout = new HorizontalLayout();
    private VerticalLayout bottomVerticalLayout1 = new VerticalLayout();
    private VerticalLayout bottomVerticalLayout2 = new VerticalLayout();
    private VerticalLayout bottomVerticalLayout3 = new VerticalLayout();
    private VerticalLayout bottomVerticalLayout4 = new VerticalLayout();
    private Button buttonToModelManager = new Button("Simulator model manager");
    private Button buttonToManager = new Button("Simulator manager");
    private Button buttonToViewingSimulations = new Button("View simulations");
    private Button buttonToConfigurations = new Button("Configuration");
    private Navigator navigator;
    private Image imgToModelManager;
    private Image imgToSimManager;
    private Image imgToViewSimulations;
    private Image imgToConfigurations;
    private Label topLabel;
    private Image topImage;

    public StartView(final Navigator navigator) {
	this.navigator = navigator;
	setSizeFull();
	setMargin(new MarginInfo(false, true, true, true));
	topLabel = new Label("<h1 style='text-align: right;'>X-Plane Simulator Manager</h1>", ContentMode.HTML);
	addComponent(topLabel);
	setComponentAlignment(topLabel, Alignment.TOP_RIGHT);
	topImage = new Image("", ResourceUtil.getEv97MainImg());
	topImage.setSizeFull();
	addComponent(topImage);
	bottomHorizontalLayout.setSizeFull();
	addComponent(bottomHorizontalLayout);
	bottomHorizontalLayout.addComponent(bottomVerticalLayout1);
	bottomHorizontalLayout.addComponent(bottomVerticalLayout2);
	bottomHorizontalLayout.addComponent(bottomVerticalLayout3);
	bottomHorizontalLayout.addComponent(bottomVerticalLayout4);

	imgToModelManager = new Image("", ResourceUtil.getSimulatorManagerImg());
	imgToModelManager.setPrimaryStyleName("circular");

	imgToSimManager = new Image("", ResourceUtil.getSimulatorManagerImg());
	imgToSimManager.setPrimaryStyleName("circular");

	imgToViewSimulations = new Image("", ResourceUtil.getBoeing737Img());
	imgToViewSimulations.setPrimaryStyleName("circular");

	imgToConfigurations = new Image("", ResourceUtil.getConfigurationMainImg());
	imgToConfigurations.setPrimaryStyleName("circular");

	buttonToModelManager.setPrimaryStyleName(BUTTON_MAINPAGE);
	buttonToManager.setPrimaryStyleName(BUTTON_MAINPAGE);
	buttonToViewingSimulations.setPrimaryStyleName(BUTTON_MAINPAGE);
	buttonToConfigurations.setPrimaryStyleName(BUTTON_MAINPAGE);

	bottomVerticalLayout1.addComponent(imgToModelManager);
	bottomVerticalLayout1.addComponent(buttonToModelManager);
	bottomVerticalLayout2.addComponent(imgToSimManager);
	bottomVerticalLayout2.addComponent(buttonToManager);
	bottomVerticalLayout3.addComponent(imgToViewSimulations);
	bottomVerticalLayout3.addComponent(buttonToViewingSimulations);
	bottomVerticalLayout4.addComponent(imgToConfigurations);
	bottomVerticalLayout4.addComponent(buttonToConfigurations);

	setExpandRatios();
	addClickListeners();
	setAllignments();
    }

    private void setExpandRatios() {
	setExpandRatio(topLabel, 1);
	setExpandRatio(topImage, 12);
	setExpandRatio(bottomHorizontalLayout, 8);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

    private void addClickListeners() {
	addButtonToManagerListener();
	addButtonToViewingSimulationsListener();
	addButtonToModelManagerListener();
	addButtonToConfigurationsListner();

    }

    private void addButtonToViewingSimulationsListener() {
	buttonToViewingSimulations.addClickListener(new ClickListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		navigator.navigateTo(SimulatormanagerUI.VIEWINGSIMULATIONS);
	    }
	});
	imgToViewSimulations.addClickListener(new MouseEvents.ClickListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
		navigator.navigateTo(SimulatormanagerUI.VIEWINGSIMULATIONS);
	    }
	});
    }

    private void addButtonToModelManagerListener() {
	ClickListener toManagerListener = new ClickListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		navigator.navigateTo(SimulatormanagerUI.MANAGESIMULATORMODELS);
	    }
	};
	buttonToModelManager.addClickListener(toManagerListener);
	imgToModelManager.addClickListener(new MouseEvents.ClickListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
		navigator.navigateTo(SimulatormanagerUI.MANAGESIMULATORMODELS);
	    }
	});
    }

    private void addButtonToManagerListener() {
	ClickListener toManagerListener = new ClickListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		navigator.navigateTo(SimulatormanagerUI.MANAGESIMULATORS);
	    }
	};
	buttonToManager.addClickListener(toManagerListener);
	imgToSimManager.addClickListener(new MouseEvents.ClickListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
		navigator.navigateTo(SimulatormanagerUI.MANAGESIMULATORS);
	    }
	});
    }

    private void addButtonToConfigurationsListner() {
	buttonToConfigurations.addClickListener(new ClickListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		navigator.navigateTo(SimulatormanagerUI.CONFIGURATION);
	    }
	});
	imgToConfigurations.addClickListener(new MouseEvents.ClickListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
		navigator.navigateTo(SimulatormanagerUI.CONFIGURATION);
	    }
	});
    }

    private void setAllignments() {
	bottomHorizontalLayout.setComponentAlignment(bottomVerticalLayout1, Alignment.MIDDLE_LEFT);
	bottomHorizontalLayout.setComponentAlignment(bottomVerticalLayout2, Alignment.MIDDLE_CENTER);
	bottomHorizontalLayout.setComponentAlignment(bottomVerticalLayout3, Alignment.MIDDLE_CENTER);
	bottomHorizontalLayout.setComponentAlignment(bottomVerticalLayout4, Alignment.MIDDLE_RIGHT);

	bottomVerticalLayout1.setComponentAlignment(imgToModelManager, Alignment.MIDDLE_LEFT);
	bottomVerticalLayout1.setComponentAlignment(buttonToModelManager, Alignment.MIDDLE_LEFT);

	bottomVerticalLayout2.setComponentAlignment(imgToSimManager, Alignment.MIDDLE_LEFT);
	bottomVerticalLayout2.setComponentAlignment(buttonToManager, Alignment.MIDDLE_LEFT);

	bottomVerticalLayout3.setComponentAlignment(imgToViewSimulations, Alignment.MIDDLE_CENTER);
	bottomVerticalLayout3.setComponentAlignment(buttonToViewingSimulations, Alignment.MIDDLE_CENTER);

	bottomVerticalLayout4.setComponentAlignment(imgToConfigurations, Alignment.MIDDLE_RIGHT);
	bottomVerticalLayout4.setComponentAlignment(buttonToConfigurations, Alignment.MIDDLE_RIGHT);
    }

}
