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

import org.jebtk.modern.UI;
import org.jebtk.modern.dialog.ModernDialogTaskWindow;
import org.jebtk.modern.panel.HExpandBox;
import org.jebtk.modern.panel.VBoxAutoWidth;
import org.jebtk.modern.spinner.ModernCompactSpinner;
import org.jebtk.modern.window.ModernWindow;

// TODO: Auto-generated Javadoc
/**
 * The class MinExpFilterDialog.
 */
public class MinExpFilterDialog extends ModernDialogTaskWindow {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The min exp field.
   */
  private ModernCompactSpinner minExpField = new ModernCompactSpinner(1);

  /**
   * The min samples field.
   */
  private ModernCompactSpinner minSamplesField = new ModernCompactSpinner(1);

  /**
   * Instantiates a new min exp filter dialog.
   *
   * @param parent the parent
   * @param minExp the min exp
   * @param minSamples the min samples
   */
  public MinExpFilterDialog(ModernWindow parent, double minExp,
      int minSamples) {
    super(parent);

    setTitle("Expression Filter");

    setup(minExp, minSamples);

    createUi();

  }

  /**
   * Setup.
   *
   * @param minExp the min exp
   * @param minSamples the min samples
   */
  private void setup(double minExp, int minSamples) {
    minExpField.setValue(minExp);
    minSamplesField.setValue(minSamples);

    setSize(360, 200);

    UI.centerWindowToScreen(this);
  }

  /**
   * Creates the ui.
   */
  private final void createUi() {
    // this.getWindowContentPanel().add(new JLabel("Change " +
    // getProductDetails().getProductName() + " settings", JLabel.LEFT),
    // BorderLayout.PAGE_START);

    VBoxAutoWidth box = new VBoxAutoWidth();

    box.add(new HExpandBox("Minimum Expression", minExpField));
    box.add(UI.createVGap(5));
    box.add(new HExpandBox("Minimum Samples", minSamplesField));

    setDialogCardContent(box);
  }

  /**
   * Gets the min exp.
   *
   * @return the min exp
   */
  public double getMinExp() {
    return minExpField.getValue();
  }

  /**
   * Gets the min samples.
   *
   * @return the min samples
   */
  public int getMinSamples() {
    return minSamplesField.getIntValue();
  }
}
