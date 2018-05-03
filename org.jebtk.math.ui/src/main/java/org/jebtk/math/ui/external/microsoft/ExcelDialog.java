/**
 * Copyright 2017 Antony Holmes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jebtk.math.ui.external.microsoft;

import java.awt.Frame;

import org.jebtk.modern.io.CsvGuiFileFilter;
import org.jebtk.modern.io.FileDialog;
import org.jebtk.modern.io.FileDialog.OpenDialog;
import org.jebtk.modern.io.FileDialog.OpenFilesSelection;
import org.jebtk.modern.io.FileDialog.SaveDialog;
import org.jebtk.modern.io.FileDialog.SaveFileSelection;
import org.jebtk.modern.io.TxtGuiFileFilter;

/**
 * The Class ExcelDialog.
 */
public class ExcelDialog {

  /**
   * The Class ExcelOpenDialog.
   */
  public static class ExcelOpenDialog {

    /** The m open. */
    private OpenDialog mOpen;

    /**
     * Instantiates a new excel open dialog.
     *
     * @param frame the frame
     */
    private ExcelOpenDialog(Frame frame) {
      mOpen = FileDialog.open(frame);
    }

    /**
     * Xlsx.
     *
     * @return the open files selection
     */
    public OpenFilesSelection xlsx() {
      return mOpen.filter(new AllXlsxGuiFileFilter(),
          new XlsxGuiFileFilter(),
          new CsvGuiFileFilter(),
          new TxtGuiFileFilter());
    }
  }

  /**
   * Open.
   *
   * @param frame the frame
   * @return the excel open dialog
   */
  public static ExcelOpenDialog open(Frame frame) {
    return new ExcelOpenDialog(frame);
  }

  /**
   * The Class ExcelSaveDialog.
   */
  public static class ExcelSaveDialog {

    /** The m save. */
    private SaveDialog mSave;

    /**
     * Instantiates a new excel save dialog.
     *
     * @param frame the frame
     */
    private ExcelSaveDialog(Frame frame) {
      mSave = FileDialog.save(frame);
    }

    /**
     * Xlsx.
     *
     * @return the save file selection
     */
    public SaveFileSelection xlsx() {
      return mSave.filter(new AllXlsxGuiFileFilter(),
          new XlsxGuiFileFilter(),
          new CsvGuiFileFilter(),
          new TxtGuiFileFilter());
    }
  }

  /**
   * Save.
   *
   * @param frame the frame
   * @return the excel save dialog
   */
  public static ExcelSaveDialog save(Frame frame) {
    return new ExcelSaveDialog(frame);
  }
}
