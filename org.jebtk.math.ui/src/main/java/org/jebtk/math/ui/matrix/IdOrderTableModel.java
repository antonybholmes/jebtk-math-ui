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

import java.util.List;

import org.jebtk.core.Indexed;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.modern.table.ModernColumnHeaderTableModel;

// TODO: Auto-generated Javadoc
/**
 * Table model for ordering rows/columns.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class IdOrderTableModel extends ModernColumnHeaderTableModel {

  /**
   * The constant HEADER.
   */
  private static final String[] HEADER = { "", "Heading" };

  /**
   * The member ids.
   */
  private List<Indexed<Integer, String>> mIds;

  /**
   * Instantiates a new id order table model.
   *
   * @param ids
   *          the ids
   */
  public IdOrderTableModel(List<Indexed<Integer, String>> ids) {

    mIds = ids;

    fireDataChanged();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#getColumnCount()
   */
  @Override
  public final int getColumnCount() {
    return HEADER.length;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#getRowCount()
   */
  @Override
  public final int getRowCount() {
    // System.out.println("row count" + rows.size());

    return mIds.size();
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
  public final Object getValueAt(int row, int col) {
    switch (col) {
    case 0:
      return mIds.get(row).getIndex() + 1;
    case 1:
      return mIds.get(row).getValue();
    }

    return null;
  }

  /**
   * Swap up.
   *
   * @param indices
   *          the indices
   */
  public void swapUp(List<Integer> indices) {

    List<Integer> sorted = CollectionUtils.sort(indices);

    for (int i : sorted) {
      if (i == 0) {
        continue;
      }

      Indexed<Integer, String> t = mIds.get(i - 1);

      mIds.set(i - 1, mIds.get(i));
      mIds.set(i, t);
    }

    this.fireDataChanged();
  }

  /**
   * Swap down.
   *
   * @param indices
   *          the indices
   */
  public void swapDown(List<Integer> indices) {
    // System.err.println("swap down " + indices.toString());

    List<Integer> sorted = CollectionUtils.reverseSort(indices);

    for (int i : sorted) {
      if (i == mIds.size() - 1) {
        continue;
      }

      Indexed<Integer, String> t = mIds.get(i + 1);

      mIds.set(i + 1, mIds.get(i));
      mIds.set(i, t);
    }

    this.fireDataChanged();
  }

  /**
   * Gets the.
   *
   * @param index
   *          the index
   * @return the indexed value
   */
  public final Indexed<Integer, String> get(int index) {

    return mIds.get(index);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataGridModel#getIsCellEditable(int,
   * int)
   */
  @Override
  public boolean getIsCellEditable(int row, int column) {
    return false;
  }
}
