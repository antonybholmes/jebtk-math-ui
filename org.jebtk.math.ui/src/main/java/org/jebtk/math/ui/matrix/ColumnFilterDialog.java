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

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Box;

import org.jebtk.core.Indexed;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.Io;
import org.jebtk.core.text.TextUtils;
import org.jebtk.modern.UI;
import org.jebtk.modern.UIService;
import org.jebtk.modern.button.ButtonsBox;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.button.ModernButtonWidget;
import org.jebtk.modern.dialog.ModernDialogButton;
import org.jebtk.modern.dialog.ModernDialogFlatButton;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernDialogWindow;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.icons.OpenFolderVectorIcon;
import org.jebtk.modern.io.FileDialog;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.panel.ModernPaddedPanel;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.table.ModernRowTable;
import org.jebtk.modern.table.ModernSimpleTable;
import org.jebtk.modern.table.ModernTableCheckboxCellEditor;
import org.jebtk.modern.table.ModernTableCheckboxCellRenderer;
import org.jebtk.modern.window.ModernWindow;

// TODO: Auto-generated Javadoc
/**
 * The Class ColumnFilterDialog.
 */
public class ColumnFilterDialog extends ModernDialogWindow
    implements ModernClickListener {

  /**
   * The Constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member model.
   */
  private ColumnFilterTableModel mModel;

  /**
   * The table.
   */
  private ModernRowTable mTable = new ModernSimpleTable();

  /**
   * The scroll pane.
   */
  private ModernScrollPane scrollPane;

  /**
   * The member columns.
   */
  private List<Indexed<Integer, String>> mColumns;

  /**
   * The toggle button.
   */
  private ModernButton toggleButton = new ModernDialogFlatButton("Toggle");

  /**
   * The select all button.
   */
  private ModernButton selectAllButton = new ModernDialogFlatButton(
      "Select All");

  /**
   * The unselect all button.
   */
  private ModernButton unselectAllButton = new ModernDialogFlatButton(
      "Unselect All");

  /**
   * The member load button.
   */
  private ModernButton mLoadButton = new ModernDialogFlatButton("Load...",
      UIService.getInstance().loadIcon(OpenFolderVectorIcon.class, 16));

  /**
   * The member working directory.
   */
  private Path mPwd;

  /**
   * The member name map.
   */
  private Map<String, Integer> mNameMap;

  /**
   * Instantiates a new column filter dialog.
   *
   * @param parent the parent
   * @param ids the ids
   * @param workingDirectory the working directory
   */
  public ColumnFilterDialog(ModernWindow parent,
      List<Indexed<Integer, String>> ids, Path workingDirectory) {
    super(parent);

    mColumns = ids;

    mNameMap = Indexed.mapValuesToIndex(ids);

    mPwd = workingDirectory;

    setup();
  }

  /**
   * Setup.
   */
  private void setup() {
    setTitle("Column Filter");

    mModel = new ColumnFilterTableModel(mColumns);

    mTable.setShowHeader(false);
    mTable.setModel(mModel);
    mTable.getColumnModel().setWidth(0, 24);
    mTable.getRendererModel().setCol(0, new ModernTableCheckboxCellRenderer());
    mTable.getEditorModel().setCol(0, new ModernTableCheckboxCellEditor());
    mTable.getColumnModel().setWidth(1, 500);

    ModernPaddedPanel content = new ModernPaddedPanel();

    scrollPane = new ModernScrollPane(mTable);
    // scrollPane.setBorder(ModernTheme.getInstance().getClass("widget").getBorder("line"));
    // scrollPane.setBorder(ModernWidget.LINE_BORDER);
    // scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
    // scrollPane.getViewport().setBackground(Color.WHITE);

    content.setBody(scrollPane);

    Box box = HBox.create();

    box.add(mLoadButton);
    box.add(ModernPanel.createHGap());
    box.add(toggleButton);
    box.add(ModernPanel.createHGap());
    box.add(selectAllButton);
    box.add(ModernPanel.createHGap());
    box.add(unselectAllButton);

    box.setBorder(ModernPanel.TOP_BORDER);

    content.setFooter(box);

    setContent(content);

    Box buttonPanel = new ButtonsBox();

    ModernButtonWidget button = new ModernDialogButton(UI.BUTTON_OK);
    button.addClickListener(this);
    buttonPanel.add(button);

    buttonPanel.add(ModernPanel.createHGap());

    button = new ModernDialogButton(UI.BUTTON_CANCEL);
    button.addClickListener(this);
    buttonPanel.add(button);

    setButtons(buttonPanel);

    toggleButton.addClickListener(this);
    selectAllButton.addClickListener(this);
    unselectAllButton.addClickListener(this);
    mLoadButton.addClickListener(this);

    setSize(640, 480);

    UI.centerWindowToScreen(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * ui. event.ModernClickEvent)
   */
  public final void clicked(ModernClickEvent e) {
    if (e.getSource().equals(toggleButton)) {
      toggle();
    } else if (e.getSource().equals(selectAllButton)) {
      checkAll(true);
    } else if (e.getSource().equals(unselectAllButton)) {
      checkAll(false);
    } else if (e.getSource().equals(mLoadButton)) {
      try {
        importIds();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    } else if (e.getMessage().equals(UI.BUTTON_OK)) {
      setStatus(ModernDialogStatus.OK);

      close();
    } else if (e.getMessage().equals(UI.BUTTON_CANCEL)) {
      close();
    } else {
      // do nothing
    }
  }

  /**
   * Import ids.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void importIds() throws IOException {

    Path file = FileDialog.open(mParent).all().getFile(mPwd);

    if (file == null) {
      return;
    }

    importIds(file);
  }

  /**
   * Import ids.
   *
   * @param file the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void importIds(Path file) throws IOException {
    mModel.clear();

    List<String> ids = new ArrayList<String>();

    BufferedReader reader = FileUtils.newBufferedReader(file);

    String line;

    try {
      // Skip header
      reader.readLine();

      while ((line = reader.readLine()) != null) {
        if (Io.isEmptyLine(line)) {
          continue;
        }

        List<String> tokens = TextUtils.fastSplit(line,
            TextUtils.TAB_DELIMITER);

        ids.add(tokens.get(0));
      }
    } finally {
      reader.close();
    }

    for (String id : ids) {
      Integer index = mNameMap.get(id);

      if (index != null) {
        mModel.setValueAt(index, 0, true);
      }
    }
  }

  /**
   * Toggle.
   */
  private void toggle() {
    for (int i : mTable.getRowModel().getSelectionModel()) {
      mTable.setValueAt(i, 0, !(Boolean) mTable.getValueAt(i, 0));
    }
  }

  /**
   * Check all.
   *
   * @param value the value
   */
  private void checkAll(boolean value) {

    for (int i = 0; i < mTable.getRowCount(); ++i) {
      mTable.setValueAt(i, 0, value);
    }
  }

  /**
   * Get the list of selected columns.
   *
   * @return the columns
   */
  public List<Indexed<Integer, String>> getColumns() {

    List<Indexed<Integer, String>> ids = new ArrayList<Indexed<Integer, String>>();

    for (int i = 0; i < mModel.getRowCount(); ++i) {
      if (!(Boolean) mTable.getValueAt(i, 0)) {
        continue;
      }

      ids.add(mModel.get(i));
    }

    return ids;
  }
}
