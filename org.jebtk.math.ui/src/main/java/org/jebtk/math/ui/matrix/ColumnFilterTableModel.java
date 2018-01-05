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

import java.util.ArrayList;
import java.util.List;

import org.jebtk.core.Indexed;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.modern.table.ModernColumnHeaderTableModel;

// TODO: Auto-generated Javadoc
/**
 * The Class ColumnFilterTableModel.
 */
public class ColumnFilterTableModel extends ModernColumnHeaderTableModel {

  /**
   * The Constant HEADER.
   */
  private static final String[] HEADER = { "", "Heading" };

  /**
   * The use.
   */
  private List<Boolean> use = new ArrayList<Boolean>();

  /**
   * The columns.
   */
  private List<Indexed<Integer, String>> mColumns;

  /**
   * Instantiates a new column filter table model.
   *
   * @param columns
   *          the columns
   */
  public ColumnFilterTableModel(List<Indexed<Integer, String>> columns) {

    mColumns = columns;

    for (int i = 0; i < columns.size(); ++i) {
      use.add(true);
    }

    fireDataChanged();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#getColumnCount()
   */
  public final int getColumnCount() {
    return HEADER.length;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#getRowCount()
   */
  public final int getRowCount() {
    // System.out.println("row count" + rows.size());

    return mColumns.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#getColumnAnnotations(int)
   */
  @Override
  public final List<String> getColumnAnnotationText(int column) {
    return CollectionUtils.asList(HEADER[column]);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#getValueAt(int, int)
   */
  @Override
  public final Object getValueAt(int row, int col) {
    switch (col) {
    case 0:
      return use.get(row);
    case 1:
      return mColumns.get(row).getValue();
    }

    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataGridModel#getIsCellEditable(int,
   * int)
   */
  public final boolean getIsCellEditable(int row, int col) {
    return col == 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#setValueAt(int, int,
   * java.lang.Object)
   */
  @Override
  public final void setValueAt(int row, int col, Object value) {
    if (col != 0) {
      return;
    }

    use.set(row, (Boolean) value);

    fireDataChanged();
  }

  /**
   * Gets the indexed value from the table.
   *
   * @param index
   *          the index
   * @return the indexed value
   */
  public final Indexed<Integer, String> get(int index) {
    return mColumns.get(index);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#clear()
   */
  @Override
  public final void clear() {
    for (int i = 0; i < use.size(); ++i) {
      use.set(i, false);
    }

    fireDataChanged();
  }
}
