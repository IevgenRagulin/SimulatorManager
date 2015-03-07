package cz.vutbr.fit.simulatormanager.database;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class DatabaseUtil {
	
	public static Item getLatestItemFromContainer(SQLContainer container) {
		Item latestItem = null;
		if ((container != null) && (container.size() != 0)) {
			final RowId id = (RowId) container.getIdByIndex(0);
			latestItem = container.getItem(id);
		}
		return latestItem;
	}

}
