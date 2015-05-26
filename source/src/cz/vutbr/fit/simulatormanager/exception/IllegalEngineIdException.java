package cz.vutbr.fit.simulatormanager.exception;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;

public class IllegalEngineIdException extends CommitException {

	private static final long serialVersionUID = 1L;

	public IllegalEngineIdException(String string) {
		super(string);
	}

}
