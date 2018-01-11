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

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jebtk.core.ColorUtils;
import org.jebtk.math.external.microsoft.Excel;
import org.jebtk.modern.dataview.ModernDataCellStyle;

// TODO: Auto-generated Javadoc
/**
 * Provides a view onto an excel workbook.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class XlsxTableModel extends ExcelTableModel {

  /**
   * Instantiates a new xlsx table model.
   *
   * @param file the file
   * @throws InvalidFormatException the invalid format exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public XlsxTableModel(Path file) throws InvalidFormatException, IOException {
    this(Excel.createXlsxWorkbook(file));
  }

  /**
   * Instantiates a new xlsx table model.
   *
   * @param workbook the workbook
   * @throws InvalidFormatException the invalid format exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public XlsxTableModel(XSSFWorkbook workbook)
      throws InvalidFormatException, IOException {
    this(workbook, false, 0, false);
  }

  /**
   * Instantiates a new xlsx table model.
   *
   * @param file the file
   * @param hasHeader the has header
   * @param rowAnnotations the row annotations
   * @throws InvalidFormatException the invalid format exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public XlsxTableModel(Path file, boolean hasHeader, int rowAnnotations)
      throws InvalidFormatException, IOException {
    this(file, hasHeader, rowAnnotations, false);
  }

  /**
   * Create an excel table model.
   *
   * @param file the file
   * @param hasHeader the has header
   * @param rowAnnotations the row annotations
   * @param supportColor the support color
   * @throws InvalidFormatException the invalid format exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public XlsxTableModel(Path file, boolean hasHeader, int rowAnnotations,
      boolean supportColor) throws InvalidFormatException, IOException {
    this(new XSSFWorkbook(OPCPackage.open(file.toFile())), null, hasHeader,
        rowAnnotations, supportColor);// new
                                      // HSSFWorkbook(file));
  }

  /**
   * Instantiates a new xlsx table model.
   *
   * @param file the file
   * @param sheet the sheet
   * @param hasHeader the has header
   * @param rowAnnotations the row annotations
   * @param supportColor the support color
   * @throws InvalidFormatException the invalid format exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public XlsxTableModel(Path file, String sheet, boolean hasHeader,
      int rowAnnotations, boolean supportColor)
      throws InvalidFormatException, IOException {
    this(new XSSFWorkbook(OPCPackage.open(file.toFile())), sheet, hasHeader,
        rowAnnotations, supportColor);// new
                                      // HSSFWorkbook(file));
  }

  /**
   * Instantiates a new xlsx table model.
   *
   * @param workbook the workbook
   * @param hasHeader the has header
   */
  public XlsxTableModel(XSSFWorkbook workbook, boolean hasHeader) {
    this(workbook, hasHeader, 0, false);
  }

  /**
   * Instantiates a new xlsx table model.
   *
   * @param workbook the workbook
   * @param hasHeader the has header
   * @param rowAnnotations the row annotations
   */
  public XlsxTableModel(XSSFWorkbook workbook, boolean hasHeader,
      int rowAnnotations) {
    this(workbook, hasHeader, rowAnnotations, false);
  }

  /**
   * Instantiates a new xlsx table model.
   *
   * @param workbook the workbook
   * @param hasHeader the has header
   * @param rowAnnotations the row annotations
   * @param supportColor the support color
   */
  public XlsxTableModel(XSSFWorkbook workbook, boolean hasHeader,
      int rowAnnotations, boolean supportColor) {
    this(workbook, null, hasHeader, rowAnnotations, supportColor);
  }

  /**
   * Instantiates a new xlsx table model.
   *
   * @param workbook the workbook
   * @param sheet the sheet
   * @param hasHeader the has header
   * @param rowAnnotations the row annotations
   * @param supportColor the support color
   */
  public XlsxTableModel(XSSFWorkbook workbook, String sheet, boolean hasHeader,
      int rowAnnotations, boolean supportColor) {
    super(sheet != null ? workbook.getSheet(sheet) : workbook.getSheetAt(0),
        hasHeader, rowAnnotations, supportColor);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.dataview.ModernDataGridModel#getCellStyle(int,
   * int)
   */
  @Override
  public ModernDataCellStyle getCellStyle(int row, int column) {
    if (!getSupportColor()) {
      return ModernDataCellStyle.DEFAULT_STYLE;
    }

    if (getSheet().getRow(row) == null) {
      return ModernDataCellStyle.DEFAULT_STYLE;
    }

    if (getSheet().getRow(row).getCell(column) == null) {
      return ModernDataCellStyle.DEFAULT_STYLE;
    }

    if (getSheet().getRow(row).getCell(column).getCellStyle() == null) {
      return ModernDataCellStyle.DEFAULT_STYLE;
    }

    ModernDataCellStyle style = new ModernDataCellStyle();

    String hexColor;
    Color color;

    XSSFColor xssf = ((XSSFCellStyle) getSheet().getRow(row).getCell(column)
        .getCellStyle()).getFont().getXSSFColor();

    if (xssf != null) {
      hexColor = xssf.getARGBHex().substring(2);

      color = ColorUtils.decodeHtmlColor(hexColor);

      style.setColor(color);
    }

    if (((XSSFCellStyle) getSheet().getRow(row).getCell(column).getCellStyle())
        .getFillForegroundXSSFColor() != null) {
      hexColor = ((XSSFCellStyle) getSheet().getRow(row).getCell(column)
          .getCellStyle()).getFillForegroundXSSFColor().getARGBHex()
              .substring(2);

      color = ColorUtils.decodeHtmlColor(hexColor);

      // System.err.println("Background " + color);

      style.setBackground(color);
    }

    return style;
  }
}
