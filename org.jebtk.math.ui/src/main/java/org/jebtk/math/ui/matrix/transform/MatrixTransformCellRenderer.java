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

import java.awt.Component;
import java.awt.Graphics2D;

import org.jebtk.core.text.Formatter;
import org.jebtk.math.matrix.DataFrame;
import org.jebtk.modern.AssetService;
import org.jebtk.modern.history.ModernHistoryListBasicCellRenderer;
import org.jebtk.modern.list.ModernList;
import org.jebtk.modern.theme.MaterialService;

/**
 * The class MatrixTransformCellRenderer.
 */
public class MatrixTransformCellRenderer extends ModernHistoryListBasicCellRenderer {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member text1.
   */
  private String mText1;

  /**
   * The member text2.
   */
  // private String mText2;

  /**
   * The member text3.
   */
  private String mText3;

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.ModernWidget#drawForegroundAA(java.awt.Graphics2D)
   */
  @Override
  public void drawForegroundAA(Graphics2D g2) {
    int y = 0;
    int x = PADDING;

    g2.setColor(TEXT_COLOR);
    g2.setFont(MaterialService.instance().getFonts().text());

    y += AssetService.ICON_SIZE_20;

    g2.drawString(mText1, x, y);

    y += AssetService.ICON_SIZE_20;
    g2.setFont(MaterialService.instance().getFonts().subtext());
    g2.setColor(ALT_TEXT_COLOR);
    g2.drawString(mText3, x, y);

    // y += UIResources.ICON_SIZE_20;
    // g2.drawString(text3, x, y);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.ui.history.ModernHistoryListBasicCellRenderer#
   * getCellRendererComponent(org.abh.common.ui.ui.list.ModernList,
   * java.lang.Object, boolean, boolean, boolean, int)
   */
  @Override
  public Component getCellRendererComponent(ModernList<?> list, Object value, boolean highlight, boolean isSelected,
      boolean hasFocus, int row) {

    super.getCellRendererComponent(list, value, highlight, isSelected, hasFocus, row);

    MatrixTransform t = (MatrixTransform) value;

    setText(row, t.getName(), t.getDescription(), t.getMatrix());

    return this;
  }

  /**
   * Sets the text.
   *
   * @param row         the row
   * @param name        the name
   * @param description the description
   * @param matrix      the matrix
   */
  private void setText(int row, String name, String description, DataFrame matrix) {
    mText1 = Integer.toString(row + 1) + ". " + name;
    // mText2 = description;
    mText3 = new StringBuilder().append(Formatter.number().format((matrix.getRows()))).append(" rows x ")
        .append(Formatter.number().format(matrix.getCols())).append(" columns").toString();
  }

}
