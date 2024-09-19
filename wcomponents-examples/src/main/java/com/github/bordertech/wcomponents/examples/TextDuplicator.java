package com.github.bordertech.wcomponents.examples;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WTextField;

/**
 * <p>
 * This component demonstrates how to add some simple control logic, by using Actions.</p>
 *
 * <p>
 * Buttons can be given actions. If the button is pressed, then the action supplied will be executed.</p>
 *
 * @author Martin Shevchenko
 * @since 1.0.0
 */
public class TextDuplicator extends WPanel {

	/**
	 * The text field which the actions modify the state of.
	 */
	private final WTextField textFld = new WTextField();

	/**
	 * Creates a TextDuplicator with the default label text.
	 */
	public TextDuplicator() {
		this("Text Duplicator");
	}

	/**
	 * Creates a TextDuplicator with the given label text.
	 *
	 * @param name the name label text
	 */
	public TextDuplicator(final String name) {
		setupUI(name);
	}

	/**
	 * Add the controls to the UI.
	 *
	 * @param labelText the text to show in the duplicator field's label.
	 */
	private void setupUI(final String labelText) {
		WButton dupBtn = new WButton("Duplicate");
		dupBtn.setAction(new DuplicateAction());

		WButton clrBtn = new WButton("Clear");
		clrBtn.setAction(new ClearAction());

		add(new WLabel(labelText, textFld));
		add(textFld);
		add(dupBtn);
		add(clrBtn);
		add(new WAjaxControl(dupBtn, this));
		add(new WAjaxControl(clrBtn, this));
	}

	/**
	 * An action implementation which duplicates the text in the text field.
	 */
	private final class DuplicateAction implements Action {

		/**
		 * Executes the action which duplicates the text.
		 *
		 * @param event details about the event that occured.
		 */
		@Override
		public void execute(final ActionEvent event) {
			// Get the text entered by the user.
			String text = textFld.getText();

			// Now duplicate it.
			textFld.setText(text + text);
		}
	}

	/**
	 * An action implementation which clears the text in the text field.
	 */
	private final class ClearAction implements Action {

		/**
		 * Executes the action which clears the text.
		 *
		 * @param event details about the event that occured.
		 */
		@Override
		public void execute(final ActionEvent event) {
			textFld.setText("");
		}
	}
}
