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
package org.jebtk.math.ui.external.microsoft;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.jebtk.modern.table.ModernColumnHeaderTableModel;

/**
 * Provides a view onto an excel workbook where the first row is considered to
 * be a header.
 * 
 * @author Antony Holmes Holmes
 *
 */
public abstract class ExcelTableModel extends ModernColumnHeaderTableModel {

  /**
   * The member sheet.
   */
  private Sheet mSheet;

  /**
   * The member header.
   */
  // private List<String> mHeader = new ArrayList<String>();

  /**
   * The member header map.
   */
  // private Map<String, Integer> mHeaderMap = new HashMap<String, Integer>();

  /**
   * The member support color.
   */
  private boolean mSupportColor;

  /**
   * The member cols.
   */
  private int mCols;

  /**
   * The member rows.
   */
  private int mRows;

  private int mRowAnns;

  private int mColAnns;

  /**
   * Create a new table model from an Excel sheet.
   *
   * @param sheet the sheet
   * @param hasHeader the has header
   * @param rowAnnotations the row annotations
   * @param supportColor the support color
   */
  public ExcelTableModel(Sheet sheet, boolean hasHeader, int rowAnns,
      boolean supportColor) {
    mSheet = sheet;
    mSupportColor = supportColor;

    Row row = sheet.getRow(0);

    mRows = mSheet.getPhysicalNumberOfRows();
    mCols = row.getPhysicalNumberOfCells();

    mRowAnns = rowAnns;
    mColAnns = hasHeader ? 1 : 0;

    /*
     * if (hasHeader) { Cell cell;
     * 
     * for (int i = mRowAnnotations; i < row.getPhysicalNumberOfCells(); ++i) {
     * cell = row.getCell(i);
     * 
     * String value = cell != null ? cell.getStringCellValue() : "";
     * 
     * mHeaderMap.put(value, mHeader.size()); mHeader.add(value); } }
     */
  }

  /**
   * Gets the sheet.
   *
   * @return the sheet
   */
  protected Sheet getSheet() {
    return mSheet;
  }

  /**
   * Gets the support color.
   *
   * @return the support color
   */
  protected boolean getSupportColor() {
    return mSupportColor;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#getColumnCount()
   */
  @Override
  public final int getColCount() {
    return mCols;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#getRowCount()
   */
  @Override
  public final int getRowCount() {
    return mRows;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#getValueAt(int, int)
   */
  @Override
  public Object getValueAt(int row, int column) {
    if (row == -1 || column == -1 || mSheet.getRow(row) == null
        || mSheet.getRow(row).getCell(column) == null) {
      return null;
    }

    //System.err.println("excel " + row + " " + column);

    if (mSheet.getRow(row).getCell(column)
        .getCellType() == Cell.CELL_TYPE_NUMERIC) {
      return mSheet.getRow(row).getCell(column).getNumericCellValue();
    } else {
      return mSheet.getRow(row).getCell(column).getStringCellValue();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataModel#setValueAt(int, int,
   * java.lang.Object)
   */
  @Override
  public void setValueAt(int row, int column, Object o) {
    String value = o.toString();

    mSheet.getRow(row).getCell(column).setCellValue(value);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.ui.dataview.ModernDataGridModel#getIsCellEditable(int,
   * int)
   */
  @Override
  public boolean getIsCellEditable(int row, int column) {
    return false;
  }

  @Override
  public boolean getIsCellEnabled(int row, int col) {
    int r = row - mColAnns;
    int c = col - mRowAnns;

    return r >= 0 && c >= 0;
  }

  @Override
  public int getHeadingIndex(String heading) {
    String lh = heading.toLowerCase();

    for (int i = 0; i < getColCount(); ++i) {
      if (mSheet.getRow(0).getCell(i).getStringCellValue().toLowerCase()
          .contains(lh)) {
        return i;
      }
    }

    return -1;
  }
}
