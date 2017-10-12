/**
 * Copyright (C) 2016, Antony Holmes
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. Neither the name of copyright holder nor the names of its contributors 
 *     may be used to endorse or promote products derived from this software 
 *     without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jebtk.math.ui.matrix;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.Box;

import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.Io;
import org.jebtk.core.text.TextUtils;
import org.jebtk.math.matrix.DataFrame;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.UI;
import org.jebtk.modern.UIService;
import org.jebtk.modern.button.ButtonsBox;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.button.ModernButtonWidget;
import org.jebtk.modern.button.ModernCheckBox;
import org.jebtk.modern.combobox.ModernComboBox;
import org.jebtk.modern.dialog.ModernDialogButton;
import org.jebtk.modern.dialog.ModernDialogFlatButton;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernDialogWindow;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.icons.OpenFolderVectorIcon;
import org.jebtk.modern.graphics.icons.PlusVectorIcon;
import org.jebtk.modern.list.ModernList;
import org.jebtk.modern.list.ModernListModel;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.panel.MatrixPanel;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.text.ModernAutoSizeLabel;
import org.jebtk.modern.text.ModernTextBorderPanel;
import org.jebtk.modern.text.ModernTextField;
import org.jebtk.modern.widget.ModernWidget;
import org.jebtk.modern.widget.tooltip.ModernToolTip;
import org.jebtk.modern.window.ModernWindow;



// TODO: Auto-generated Javadoc
/**
 * The class IdFilterDialog.
 */
public class IdFilterDialog extends ModernDialogWindow implements ModernClickListener, KeyListener {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * The row list.
	 */
	private ModernList<String> rowList = new ModernList<String>();
	
	/**
	 * The model.
	 */
	private ModernListModel<String> model = 
			new ModernListModel<String>();

	/**
	 * The check exact.
	 */
	private ModernCheckBox checkExact = 
			new ModernCheckBox("Full match", true);
	
	/**
	 * The check in list.
	 */
	private ModernCheckBox checkInList = 
			new ModernCheckBox("In list", true);
	
	/**
	 * The add button.
	 */
	private ModernButton addButton = 
			new ModernDialogFlatButton(UIService.getInstance().loadIcon(PlusVectorIcon.class, 16));

	/**
	 * The remove button.
	 */
	private ModernButton removeButton = new ModernDialogFlatButton(UI.MENU_REMOVE,
			UIService.getInstance().loadIcon("trash_bw", 16));
	
	/**
	 * The clear button.
	 */
	private ModernButton clearButton = new ModernDialogFlatButton(UI.MENU_CLEAR,
			UIService.getInstance().loadIcon("clear", UIService.ICON_SIZE_16));

	/**
	 * The import button.
	 */
	private ModernButton importButton = 
			new ModernDialogFlatButton(UI.BUTTON_IMPORT, UIService.getInstance().loadIcon(OpenFolderVectorIcon.class, 16));

	

	/**
	 * The new row text field.
	 */
	private ModernTextField newRowTextField = new ModernTextField();

	/**
	 * The working directory.
	 */
	private Path mPWd;
	
	/**
	 * The type combo.
	 */
	private ModernComboBox typeCombo = new ModernComboBox();


	/**
	 * The ids.
	 */
	private List<List<String>> ids;


	/**
	 * The types.
	 */
	private List<String> types;
	
	/**
	 * The class TypeChangeEvents.
	 */
	private class TypeChangeEvents implements ModernClickListener {

		/* (non-Javadoc)
		 * @see org.abh.common.ui.ui.event.ModernClickListener#clicked(org.abh.common.ui.ui.event.ModernClickEvent)
		 */
		@Override
		public void clicked(ModernClickEvent e) {
			changeIds();
		}
		
	}
	
	/**
	 * Instantiates a new id filter dialog.
	 *
	 * @param parent the parent
	 * @param title the title
	 * @param workingDirectory the working directory
	 */
	public IdFilterDialog(ModernWindow parent, 
			String title, 
			Path workingDirectory) {
		super(parent);

		mPWd = workingDirectory;
		
		setup(title);
	}
	
	/**
	 * Instantiates a new id filter dialog.
	 *
	 * @param parent the parent
	 * @param title the title
	 * @param workingDirectory the working directory
	 * @param types the types
	 * @param ids the ids
	 */
	public IdFilterDialog(ModernWindow parent,
			String title,
			Path workingDirectory,
			List<String> types,
			List<List<String>> ids) {
		super(parent);
		
		mPWd = workingDirectory;
		
		setup(title);
		
		this.ids = ids;
		
		this.types = types;
		
		for (String type : types) {
			if (type.equals(DataFrame.ROW_NAMES)) {
				typeCombo.addMenuItem("Row Names");
			} else if (type.equals(DataFrame.COLUMN_NAMES)) {
				typeCombo.addMenuItem("Column Names");
			} else {
				typeCombo.addMenuItem(type);
			}
		}
		
		typeCombo.setSelectedIndex(0);
		
		typeCombo.addClickListener(new TypeChangeEvents());
		
		changeIds();
	}
	
	

	/**
	 * Sets the up.
	 *
	 * @param title the new up
	 */
	private void setup(String title) {
		setTitle(title);
		
		rowList.setModel(model);

		

		//TabbedPane tabbedPane = new TabbedPane();

		// samples

		ModernComponent content = new ModernComponent();
		
		int[] rows = {ModernWidget.WIDGET_HEIGHT};
		int[] columns = {100, 400, ModernWidget.WIDGET_HEIGHT};
		
		MatrixPanel panel = new MatrixPanel(rows, columns, ModernWidget.PADDING, ModernWidget.PADDING);

		panel.add(new ModernAutoSizeLabel("New row id"));
		panel.add(new ModernTextBorderPanel(newRowTextField));
		addButton.setToolTip(new ModernToolTip("Add Row Id", 
				"Add a new row identifier to search for. The row identifier applys only to the first column in a table."));
		addButton.addClickListener(this);
		panel.add(addButton);
		
		panel.add(new ModernAutoSizeLabel("Type"));
		panel.add(typeCombo);
		
		
		content.add(panel, BorderLayout.PAGE_START);
		
		ModernScrollPane scrollPane = new ModernScrollPane(rowList);
		scrollPane.setBorder(ModernPanel.TOP_BOTTOM_BORDER);
		content.add(scrollPane, BorderLayout.CENTER);
		
		Box box = HBox.create();
	
		box.add(importButton);
		box.add(ModernPanel.createHGap());
		box.add(removeButton);
		box.add(ModernPanel.createHGap());
		box.add(clearButton);
		content.add(box, BorderLayout.PAGE_END);
		
	
		setContent(content);

		ButtonsBox buttonPanel = new ButtonsBox();
		
		buttonPanel.addLeft(checkExact);
		buttonPanel.addLeft(ModernPanel.createHGap());
		buttonPanel.addLeft(checkInList);

		ModernButtonWidget button = new ModernDialogButton(UI.BUTTON_OK);
		button.addClickListener(this);
		buttonPanel.add(button);

		buttonPanel.add(ModernPanel.createHGap());

		button = new ModernDialogButton(UI.BUTTON_CANCEL);
		button.addClickListener(this);
		buttonPanel.add(button);

		
		setButtons(buttonPanel);

		setSize(640, 480);
		
		UI.centerWindowToScreen(this);
		
		newRowTextField.addKeyListener(this);
		
		importButton.setToolTip(new ModernToolTip("Import Row Ids", 
				"Import multiple row identifiers from a text file. There should be one row id per line in the file."));
		
		importButton.addClickListener(this);
		
		removeButton.setToolTip(new ModernToolTip("Remove Row Ids", 
				"Remove selected row identifiers from the list."));
		
		removeButton.addClickListener(this);
		
		clearButton.setToolTip(new ModernToolTip("Clear All Ids", 
				"Remove all row identifiers from the list."));
		
		clearButton.addClickListener(this);
	}

	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.event.ModernClickListener#clicked(org.abh.common.ui.ui.event.ModernClickEvent)
	 */
	public final void clicked(ModernClickEvent e) {
		if (e.getSource().equals(addButton)) {
			addId();
		} else if (e.getSource().equals(importButton)) {
			try {
				importIds();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(removeButton)) {
			removeId();
		} else if (e.getSource().equals(clearButton)) {
			clearIds();
		} else if (e.getMessage().equals(UI.BUTTON_OK)) {
			setStatus(ModernDialogStatus.OK);
			
			close();
		} else if (e.getMessage().equals(UI.BUTTON_CANCEL)) {
			close();
		} else {
			// do nothing
		}
	}

	/**
	 * Gets the ids.
	 *
	 * @return the ids
	 */
	public List<String> getIds() {

		List<String> ids = new ArrayList<String>();

		for (int i = 0; i < model.getItemCount(); ++i) {
			ids.add(model.getValueAt(i));
		}

		return ids;
	}

	/**
	 * Adds the id.
	 */
	private void addId() {

		if (this.newRowTextField.getText() != null && !this.newRowTextField.getText().equals("")) {
			model.addValue(this.newRowTextField.getText());
		}
		
		//rowList.adjustSize();
		//scrollPane.adjustDisplay();
	}

	/**
	 * Removes the id.
	 */
	private void removeId() {

		Stack<Integer> ids = new Stack<Integer>();

		for (int i : rowList.getSelectionModel()) {
			ids.push(i);
		}

		while (!ids.empty()) {
			model.removeValueAt(ids.pop());
		}
		
		//rowList.adjustSize();
		//scrollPane.adjustDisplay();
	}
	
	/**
	 * Clear ids.
	 */
	private void clearIds() {

		model.clear();
		
		//rowList.adjustSize();
		//scrollPane.adjustDisplay();
	}

	/**
	 * Import ids.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void importIds() throws IOException {

		Path file = UI.selectFile(this, mPWd);
		
		if (file == null) {
			return;
		}

        importIds(file);
	}

	/**
	 * Import ids.
	 *
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void importIds(Path file) throws IOException {

        List<String> ids = new ArrayList<String>();

		BufferedReader reader = FileUtils.newBufferedReader(file);

		String line;

		try {
			// Skip header
			reader.readLine();
			
    		while ((line = reader.readLine()) != null) {
    			if (Io.isEmptyLine(line)) {
    				continue;
    			}

    			List<String> tokens = TextUtils.fastSplit(line, TextUtils.TAB_DELIMITER);

    			ids.add(tokens.get(0));
    		}
		} finally {
			reader.close();
		}

		loadIds(ids);
	}
	
	/**
	 * Change ids.
	 */
	private void changeIds() {
		clearIds();
		
		loadIds(ids.get(this.typeCombo.getSelectedIndex()));
	}
	
	/**
	 * Load ids.
	 *
	 * @param ids the ids
	 */
	private void loadIds(List<String> ids) {
		for (String id : ids) {
			model.addValue(id);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public final void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public final void keyTyped(KeyEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			addId();
		}
	}

	/**
	 * Gets the exact match.
	 *
	 * @return the exact match
	 */
	public boolean getExactMatch() {
		return checkExact.isSelected();
	}
	
	/**
	 * Returns true if we are finding items in the list,
	 * false otherwise.
	 *
	 * @return the in list
	 */
	public boolean getInList() {
		return checkInList.isSelected();
	}
	
	/**
	 * Gets the id type.
	 *
	 * @return the id type
	 */
	public String getIdType() {
		return types.get(typeCombo.getSelectedIndex());
	}
}
