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

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.text.TextUtils;
import org.jebtk.math.matrix.DataFrame;
import org.jebtk.math.matrix.MatrixEventListener;
import org.jebtk.modern.table.ModernTableModel;

/**
 * Loads.
 *
 * @author Antony Holmes
 */
public class MatrixTableModel extends ModernTableModel
    implements MatrixEventListener {

  /**
   * The member matrix.
   */
  private DataFrame mMatrix;

  /** The m row anns. */
  protected int mRowAnns = 0;

  /** The m col anns. */
  protected int mColAnns = 0;

  /**
   * Instantiates a new matrix table model.
   *
   * @param matrix the matrix
   */
  public MatrixTableModel(DataFrame matrix) {
    mMatrix = matrix;

    matrix.addMatrixListener(this);

    refresh();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#getColumnCount()
   */
  @Override
  public int getColCount() {
    return mMatrix.getExtCols();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#getRowCount()
   */
  @Override
  public int getRowCount() {
    return mMatrix.getExtRows();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#getValueAt(int, int)
   */
  @Override
  public Object getValueAt(int row, int col) {
    int r = row - mColAnns;
    int c = col - mRowAnns;

    if (c < 0 && r < 0) {
      // We only want to show annotations on the last row so that they
      // are not repeated if there are multiple column annotations
      if (r == -1) {
        return mMatrix.get(r, c);
      } else {
        return TextUtils.EMPTY_STRING;
      }
      // } else if (c > -1 && r == -1 && mColAnns > 1) {
      // return mMatrix.getColumns().getAnnotationName(r) + ": " + mMatrix.get(r, c);
    } else {
      return mMatrix.get(r, c);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#setValueAt(int, int,
   * java.lang.Object)
   */
  @Override
  public void setValueAt(int row, int col, Object o) {
    mMatrix.set(row - mColAnns, col - mRowAnns, o);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.dataview.ModernDataModel#getIsCellEnabled(int, int)
   */
  @Override
  public boolean getIsCellEnabled(int row, int col) {
    int r = row - mColAnns;
    int c = col - mRowAnns;

    return r >= 0 && c >= 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.math.matrix.MatrixEventListener#matrixChanged(org.abh.lib.
   * event. ChangeEvent)
   */
  @Override
  public void matrixChanged(ChangeEvent e) {
    // If the matrix changes, notify the table to
    // update. Since the matrix is static, we need only fire the
    // update command rather than changed since the underlying structure
    // will not have changed

    refresh();

    fireDataUpdated();
  }

  /**
   * Determine number of annotation columns/rows from underlying matrix.
   */
  private void refresh() {
    //SysUtils.err().println("refresh ", mMatrix.getColumns().getNames());
    
    mRowAnns = mMatrix.getIndex().getNames().size();
    mColAnns = mMatrix.getColumnHeader().getNames().size();
  }

  /**
   * Gets the matrix.
   *
   * @return the matrix
   */
  public DataFrame getMatrix() {
    return mMatrix;
  }

  @Override
  public int getHeadingIndex(String heading) {
    String lh = heading.toLowerCase();
    
    for (int i = 0; i < mMatrix.getCols(); ++i) {
      if (mMatrix.getColumnName(i).toLowerCase().contains(lh)) {
        return i;
      }
    }

    return -1;
  }
}