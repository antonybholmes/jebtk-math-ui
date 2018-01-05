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

import org.jebtk.math.matrix.DataFrame;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.table.EmptyTableModel;
import org.jebtk.modern.table.ModernSpreadsheet;
import org.jebtk.modern.widget.ModernWidget;

// TODO: Auto-generated Javadoc
/**
 * The class MatrixPreviewTablePanel.
 */
public class MatrixPreviewTablePanel extends ModernWidget {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The table.
   */
  private ModernSpreadsheet table = new ModernSpreadsheet();

  /**
   * The scroll pane.
   */
  private ModernScrollPane scrollPane;

  /**
   * The member matrix.
   */
  private DataFrame mMatrix;

  /**
   * Instantiates a new matrix preview table panel.
   *
   * @param matrix
   *          the matrix
   */
  public MatrixPreviewTablePanel(DataFrame matrix) {
    mMatrix = matrix;

    // table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    // table.setAutoCreateRowSorter(true);

    scrollPane = new ModernScrollPane(table);
    // scrollPane.getViewport().setBackground(Color.WHITE);
    // scrollPane.showClipBorder(true);
    // scrollPane.getClip().setOpaque(true);
    // scrollPane.getClip().setBackground(Color.WHITE);

    add(scrollPane, BorderLayout.CENTER);

    EditableMatrixTableModel model = new EditableMatrixTableModel(matrix);

    table.setModel(model);
  }

  /**
   * Remove displayed rows.
   */
  public final void clear() {
    // use and empty table model to display nothing
    table.setModel(new EmptyTableModel());
  }

  /**
   * Gets the matrix.
   *
   * @return the matrix
   */
  public DataFrame getMatrix() {
    return mMatrix;
  }
}
