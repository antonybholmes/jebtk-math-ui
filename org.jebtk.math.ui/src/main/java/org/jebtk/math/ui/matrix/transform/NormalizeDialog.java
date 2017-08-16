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
package org.jebtk.math.ui.matrix.transform;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JDialog;

import org.jebtk.modern.UI;
import org.jebtk.modern.button.ButtonsBox;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.dialog.ModernDialogButton;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernDialogWindow;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.panel.MatrixPanel;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.text.ModernAutoSizeLabel;
import org.jebtk.modern.text.ModernNumericalTextField;
import org.jebtk.modern.text.ModernTextBorderPanel;
import org.jebtk.modern.text.ModernTextField;
import org.jebtk.modern.widget.ModernWidget;
import org.jebtk.modern.window.ModernWindow;


// TODO: Auto-generated Javadoc
/**
 * The class NormalizeDialog.
 */
public class NormalizeDialog extends ModernDialogWindow implements ModernClickListener {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The ok button.
	 */
	private ModernButton okButton = 
			new ModernDialogButton(UI.BUTTON_OK);
	
	/**
	 * The close button.
	 */
	private ModernButton closeButton = 
			new ModernDialogButton(UI.BUTTON_CANCEL);

	/**
	 * The min field.
	 */
	private ModernTextField minField = new ModernNumericalTextField("-4");

	/**
	 * Instantiates a new normalize dialog.
	 *
	 * @param parent the parent
	 * @param scale the scale
	 */
	public NormalizeDialog(ModernWindow parent, double scale) {
		super(parent);
		
		setTitle("Normalize");

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setup(scale);

		createUi();
	}

	/**
	 * Sets the up.
	 *
	 * @param scale the new up
	 */
	private void setup(double scale) {
		okButton.addClickListener(this);
		closeButton.addClickListener(this);
		
		minField.setText(scale);

		setSize(new Dimension(320, 160));
		
		UI.centerWindowToScreen(this);
	}

	/**
	 * Creates the ui.
	 */
	private final void createUi() {
		//this.getContentPane().add(new JLabel("Change " + getProductDetails().getProductName() + " settings", JLabel.LEFT), BorderLayout.PAGE_START);

		//ModernPanel content = new ModernDialogPanel();

		int[] rows = {ModernWidget.WIDGET_HEIGHT};
		int[] cols = {200, 100};
		
		MatrixPanel matrixPanel = 
				new MatrixPanel(rows, cols, ModernWidget.PADDING, ModernWidget.PADDING);

		matrixPanel.add(new ModernAutoSizeLabel("Scale"));
		matrixPanel.add(new ModernTextBorderPanel(minField));

		//content.add(matrixPanel, BorderLayout.CENTER);


		//JPanel buttonPanel = new Panel(new FlowLayout(FlowLayout.LEFT));

		//importButton.setCanvasSize(new Dimension(100, ModernTheme.getInstance().getClass("widget").getInt("height")));
		//exportButton.setCanvasSize(new Dimension(100, ModernTheme.getInstance().getClass("widget").getInt("height")));

		//buttonPanel.add(importButton);
		//buttonPanel.add(exportButton);

		//panel.add(buttonPanel, BorderLayout.PAGE_END);


		setContent(matrixPanel);

		Box buttonPanel = new ButtonsBox();

		buttonPanel.add(okButton);
		buttonPanel.add(ModernPanel.createHGap());
		buttonPanel.add(closeButton);
	
		setButtons(buttonPanel);
	}

	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.event.ModernClickListener#clicked(org.abh.common.ui.ui.event.ModernClickEvent)
	 */
	public final void clicked(ModernClickEvent e) {
		if (e.getMessage().equals(UI.BUTTON_OK)) {
			setStatus(ModernDialogStatus.OK);
		}
		
		close();
	}

	/**
	 * Gets the scale.
	 *
	 * @return the scale
	 */
	public double getScale() {
		return Double.parseDouble(minField.getText());
	}
}
