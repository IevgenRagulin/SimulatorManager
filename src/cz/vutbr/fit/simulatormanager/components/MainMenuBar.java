package cz.vutbr.fit.simulatormanager.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.MenuBar;

import cz.vutbr.fit.simulatormanager.SimulatormanagerUI;
import cz.vutbr.fit.simulatormanager.types.PageType;

/**
 * This menu bar is quite tricky (the tricky part is about marking the menu as
 * checked/unchecked). Each page has an instance of this menu bar. Every time
 * the user enters a page, a new menu bar is instantiated. Every time someone
 * selects the menu item, we mark the menu item on the current page as selected,
 * and we deselect all others.
 *
 * 
 * @author ievgen
 *
 */
public class MainMenuBar extends MenuBar {
	private static final long serialVersionUID = 1L;
	final static Logger LOG = LoggerFactory.getLogger(MainMenuBar.class);
	private static final String MENU_HOME_TEXT = "Home";
	private static final String MENU_MANAGE_SIMULATOR_MODELS_TEXT = "Manage simulator models";
	private static final String MENU_MANAGE_SIMULATORS_TEXT = "Manage simulators";
	private static final String MENU_VIEW_SIMULATIONS_TEXT = "View simulations";
	private static final String MENU_CONFIGURATION_TEXT = "Configuration";
	private static final String MENU_HOME_DESCRIPTION = "Go to home page";
	private static final String MENU_MANAGE_SIMULATOR_MODELS_DESCRIPTION = "Add/edit/remove simulators' models";
	private static final String MENU_MANAGE_SIMULATORS_DESCRIPTION = "Add/edit/remove simulators' info";
	private static final String MENU_VIEW_SIMULATIONS_DESCRIPTION = "View previous/current simulations";
	private static final String MENU_CONFIGURATION_DESCRIPTION = "Go to Configurations page";

	private final PageType pageType;

	public static MainMenuBar getInstance(final Navigator navigator, PageType currentPage) {
		return new MainMenuBar(navigator, currentPage);
	}

	private MainMenuBar(final Navigator navigator, PageType currentPage) {
		super();
		setStyleName("sm-menu-bar");
		setWidth("100%");
		this.pageType = currentPage;
		LOG.info("Creating menu bar on page " + currentPage);
		MenuBar.Command menuClickHandler = getClickHandler(navigator);
		addMenuItem(MENU_HOME_TEXT, MENU_HOME_DESCRIPTION, menuClickHandler, currentPage);
		addMenuItem(MENU_MANAGE_SIMULATOR_MODELS_TEXT, MENU_MANAGE_SIMULATOR_MODELS_DESCRIPTION, menuClickHandler,
				currentPage);
		addMenuItem(MENU_MANAGE_SIMULATORS_TEXT, MENU_MANAGE_SIMULATORS_DESCRIPTION, menuClickHandler, currentPage);
		addMenuItem(MENU_VIEW_SIMULATIONS_TEXT, MENU_VIEW_SIMULATIONS_DESCRIPTION, menuClickHandler, currentPage);
		addMenuItem(MENU_CONFIGURATION_TEXT, MENU_CONFIGURATION_DESCRIPTION, menuClickHandler, currentPage);

		checkTheCorrectMenuItem(currentPage);
	}

	private MenuBar.Command getClickHandler(final Navigator navigator) {
		return new MenuBar.Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem itemSelected) {
				LOG.info("Menu selected {}", itemSelected.getText());
				if (itemSelected.getText().equals(MENU_HOME_TEXT)) {
					navigator.navigateTo("");
				} else if (itemSelected.getText().equals(MENU_MANAGE_SIMULATOR_MODELS_TEXT)) {
					navigator.navigateTo(SimulatormanagerUI.MANAGESIMULATORMODELS);
				} else if (itemSelected.getText().equals(MENU_MANAGE_SIMULATORS_TEXT)) {
					navigator.navigateTo(SimulatormanagerUI.MANAGESIMULATORS);
				} else if (itemSelected.getText().equals(MENU_VIEW_SIMULATIONS_TEXT)) {
					navigator.navigateTo(SimulatormanagerUI.VIEWINGSIMULATIONS);
				} else if (itemSelected.getText().equals(MENU_CONFIGURATION_TEXT)) {
					navigator.navigateTo(SimulatormanagerUI.CONFIGURATION);
				}
				checkTheCorrectMenuItem(pageType);
			}
		};
	}

	/**
	 * Adds menu item to the current page
	 * 
	 * @param text
	 * @param command
	 * @param currentPage
	 * @return
	 */
	private MenuItem addMenuItem(String text, String description, MenuBar.Command command, PageType currentPage) {
		MenuItem item = addItem(text, null, command);
		item.setStyleName("sm-menu-item");
		item.setCheckable(true);
		item.setDescription(description);
		return item;
	}

	/**
	 * Based on currentPage, select one of the menu items, deselect the rest
	 * 
	 * @param currentPage
	 */
	public void checkTheCorrectMenuItem(PageType currentPage) {
		for (MenuItem item : getItems()) {
			item.setChecked(false);
			if (item.equals(MENU_HOME_TEXT) && currentPage.equals(PageType.HOME)) {
				item.setChecked(true);
			} else if (item.getText().equals(MENU_CONFIGURATION_TEXT) && currentPage.equals(PageType.CONFIGURATIONS)) {
				item.setChecked(true);
			} else if (item.getText().equals(MENU_MANAGE_SIMULATOR_MODELS_TEXT)
					&& currentPage.equals(PageType.MANAGE_SIMULATOR_MODELS)) {
				item.setChecked(true);
			} else if (item.getText().equals(MENU_MANAGE_SIMULATORS_TEXT)
					&& currentPage.equals(PageType.MANAGE_SIMULATORS)) {
				item.setChecked(true);
			} else if (item.getText().equals(MENU_VIEW_SIMULATIONS_TEXT)
					&& currentPage.equals(PageType.CHOOSE_SIMULATION)) {
				item.setChecked(true);
			}
		}
	}
}