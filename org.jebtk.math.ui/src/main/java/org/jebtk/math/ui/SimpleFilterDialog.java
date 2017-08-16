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
package org.jebtk.math.ui;

import java.awt.Dimension;

import javax.swing.Box;

import org.jebtk.modern.UI;
import org.jebtk.modern.dialog.ModernDialogTaskWindow;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.text.ModernAutoSizeLabel;
import org.jebtk.modern.text.ModernNumericalTextField;
import org.jebtk.modern.text.ModernTextBorderPanel;
import org.jebtk.modern.text.ModernTextField;
import org.jebtk.modern.window.ModernWindow;


// TODO: Auto-generated Javadoc
/**
 * The class StdDevFilterDialog.
 */
public class SimpleFilterDialog extends ModernDialogTaskWindow {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The member min field.
	 */
	private ModernTextField mMinField = new ModernNumericalTextField("1.5");

	/**
	 * Instantiates a new std dev filter dialog.
	 *
	 * @param parent the parent
	 * @param name the name
	 * @param min the min
	 */
	public SimpleFilterDialog(ModernWindow parent, 
			String name,
			double min) {
		super(parent);
		
		setTitle("Filter");

		setup(min);

		createUi(name);

	}

	/**
	 * Sets the up.
	 *
	 * @param min the new up
	 */
	private void setup(double min) {
		mMinField.setText(Double.toString(min));
		
		setSize(new Dimension(360, 160));
		
		UI.centerWindowToScreen(this);
	}

	/**
	 * Creates the ui.
	 *
	 * @param name the name
	 */
	private final void createUi(String name) {
		//this.getContentPane().add(new JLabel("Change " + getProductDetails().getProductName() + " settings", JLabel.LEFT), BorderLayout.PAGE_START);

		Box box = HBox.create();
		
		box.add(new ModernAutoSizeLabel(name));
		box.add(Box.createHorizontalGlue());
		box.add(new ModernTextBorderPanel(mMinField, 100));

		setDialogCardContent(box);
	}

	/**
	 * Gets the min.
	 *
	 * @return the min
	 */
	public double getMin() {
		return Double.parseDouble(mMinField.getText());
	}
}
