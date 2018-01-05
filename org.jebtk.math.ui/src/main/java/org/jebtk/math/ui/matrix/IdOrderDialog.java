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
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;

import org.jebtk.core.Indexed;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.Io;
import org.jebtk.core.text.TextUtils;
import org.jebtk.math.matrix.DataFrame;
import org.jebtk.modern.BorderService;
import org.jebtk.modern.UI;
import org.jebtk.modern.UIService;
import org.jebtk.modern.button.ButtonsBox;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.button.ModernButtonWidget;
import org.jebtk.modern.combobox.ModernComboBox;
import org.jebtk.modern.dialog.ModernDialogButton;
import org.jebtk.modern.dialog.ModernDialogFlatButton;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernDialogWindow;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.icons.ArrowDownVectorIcon;
import org.jebtk.modern.graphics.icons.ArrowUpVectorIcon;
import org.jebtk.modern.graphics.icons.OpenFolderVectorIcon;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.table.ModernRowTable;
import org.jebtk.modern.text.ModernAutoSizeLabel;
import org.jebtk.modern.window.ModernWindow;

// TODO: Auto-generated Javadoc
/**
 * Allow ordering of columns or rows in a table.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class IdOrderDialog extends ModernDialogWindow implements ModernClickListener {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member model.
   */
  private IdOrderTableModel mModel = null;

  /**
   * The member table.
   */
  private ModernRowTable mTable = new ModernRowTable();

  /**
   * The member up button.
   */
  private ModernButton mUpButton = new ModernDialogFlatButton(
      UIService.getInstance().loadIcon(ArrowUpVectorIcon.class, 16));

  /**
   * The member down button.
   */
  private ModernButton mDownButton = new ModernDialogFlatButton(
      UIService.getInstance().loadIcon(ArrowDownVectorIcon.class, 16));

  /**
   * The member alphabetical button.
   */
  private ModernButton mAlphabeticalButton = new ModernDialogFlatButton("Alphabetical",
      UIService.getInstance().loadIcon("alphabetical", 16));

  /**
   * The member load button.
   */
  private ModernButton mLoadButton = new ModernDialogFlatButton("Load order",
      UIService.getInstance().loadIcon(OpenFolderVectorIcon.class, 16));

  /**
   * The member type combo.
   */
  private ModernComboBox mTypeCombo = new ModernComboBox();

  /**
   * The member ids.
   */
  private List<List<Indexed<Integer, String>>> mIds;

  /**
   * The working directory.
   */
  private Path workingDirectory;

  /**
   * The class TypeChangeEvents.
   */
  private class TypeChangeEvents implements ModernClickListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.ui.ui.event.ModernClickListener#clicked(org.abh.common.ui.ui.
     * event.ModernClickEvent)
     */
    @Override
    public void clicked(ModernClickEvent e) {
      changeIds();
    }

  }

  /**
   * Instantiates a new id order dialog.
   *
   * @param parent
   *          the parent
   * @param title
   *          the title
   * @param types
   *          the types
   * @param ids
   *          the ids
   * @param workingDirectory
   *          the working directory
   */
  public IdOrderDialog(ModernWindow parent, String title, List<String> types, List<List<Indexed<Integer, String>>> ids,
      Path workingDirectory) {
    super(parent);

    setTitle(title);

    this.workingDirectory = workingDirectory;

    this.mIds = ids;

    for (String type : types) {
      if (type.equals(DataFrame.ROW_NAMES)) {
        mTypeCombo.addMenuItem("Row Names");
      } else if (type.equals(DataFrame.COLUMN_NAMES)) {
        mTypeCombo.addMenuItem("Column Names");
      } else {
        mTypeCombo.addMenuItem(type);
      }
    }

    mTypeCombo.setSelectedIndex(0);

    mTypeCombo.addClickListener(new TypeChangeEvents());

    ModernPanel content = new ModernPanel();

    Box box = HBox.create();

    ModernAutoSizeLabel label = new ModernAutoSizeLabel("Type");

    box.add(label);
    box.add(UI.createHGap(10));

    UI.setSize(mTypeCombo, new Dimension(200, 24));
    box.add(mTypeCombo);

    box.setBorder(ModernPanel.LARGE_BORDER);

    content.add(box, BorderLayout.PAGE_START);

    ModernScrollPane scrollPane = new ModernScrollPane(mTable);
    // scrollPane.setBorder(ModernTheme.getInstance().getClass("widget").getBorder("dialog"));
    // scrollPane.setBorder(ModernWidget.LINE_BORDER);
    // scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
    // scrollPane.getViewport().setBackground(Color.WHITE);

    content.setBody(scrollPane);

    box = Box.createVerticalBox();

    mUpButton.addClickListener(this);
    box.add(mUpButton);

    box.add(ModernPanel.createVGap());

    mDownButton.addClickListener(this);
    box.add(mDownButton);

    box.setBorder(BorderService.getInstance().createLeftBorder(10));

    content.add(box, BorderLayout.LINE_END);

    box = HBox.create();

    box.setBorder(ModernPanel.TOP_BORDER);

    box.add(mLoadButton);
    box.add(ModernPanel.createHGap());
    box.add(mAlphabeticalButton);

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

    changeIds();

    setSize(640, 480);

    UI.centerWindowToScreen(this);

    mLoadButton.addClickListener(this);
    mAlphabeticalButton.addClickListener(this);
  }

  /**
   * Load ids.
   *
   * @param ids
   *          the ids
   */
  private void loadIds(List<Indexed<Integer, String>> ids) {
    mModel = new IdOrderTableModel(ids);

    mTable.setModel(mModel);
    mTable.getColumnModel().setWidth(0, 50);
    mTable.getColumnModel().setWidth(1, 400);
    mTable.setShowHeader(false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.ui.event.ModernClickListener#clicked(org.abh.common.ui.ui.
   * event.ModernClickEvent)
   */
  public final void clicked(ModernClickEvent e) {
    if (e.getSource().equals(mUpButton)) {
      swapUp();
    } else if (e.getSource().equals(mDownButton)) {
      swapDown();
    } else if (e.getSource().equals(mLoadButton)) {
      try {
        sortByExternalIdList();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    } else if (e.getSource().equals(mAlphabeticalButton)) {
      sortAlphabetically();
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
   * Sort alphabetically.
   */
  private void sortAlphabetically() {
    Map<String, List<Integer>> rowMap = new HashMap<String, List<Integer>>();

    for (int i = 0; i < mTable.getRowCount(); ++i) {
      Indexed<Integer, String> column = mModel.get(i);

      // We must deal with multiple samples with the same name.
      if (!rowMap.containsKey(column.getValue())) {
        rowMap.put(column.getValue(), new ArrayList<Integer>());
      }

      rowMap.get(column.getValue()).add(column.getIndex());
    }

    List<String> sortedNames = CollectionUtils.sort(rowMap.keySet());

    List<Indexed<Integer, String>> columns = new ArrayList<Indexed<Integer, String>>();

    for (String name : sortedNames) {
      List<Integer> ids = CollectionUtils.sort(rowMap.get(name));

      for (int id : ids) {
        columns.add(new Indexed<Integer, String>(id, name));
      }
    }

    loadIds(columns);
  }

  /**
   * Swap up.
   */
  private void swapUp() {
    List<Integer> indices = new ArrayList<Integer>();

    for (int i = 0; i < mTable.getRowModel().size(); ++i) {
      if (!mTable.getRowModel().isSelected(i)) {
        continue;
      }

      indices.add(i);
    }

    mModel.swapUp(indices);

    // columnTable.getCellSelectionModel().clear();
    mTable.getRowModel().unselectAll();

    for (int i : indices) {
      if (i == 0) {
        continue;
      }

      // columnTable.getCellSelectionModel().getRowSelectionModel().add(i - 1);

      mTable.getRowModel().setSelected(i - 1);
    }
  }

  /**
   * Swap down.
   */
  private void swapDown() {
    List<Integer> indices = new ArrayList<Integer>();

    for (int i = 0; i < mTable.getRowModel().size(); ++i) {
      if (!mTable.getRowModel().isSelected(i)) {
        continue;
      }

      indices.add(i);
    }

    mModel.swapDown(indices);

    mTable.getRowModel().unselectAll();

    for (int i : indices) {
      if (i == mTable.getRowCount() - 1) {
        continue;
      }

      mTable.getRowModel().setSelected(i + 1);
    }
  }

  /**
   * Sort by external id list.
   *
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  private void sortByExternalIdList() throws IOException {

    Path file = UI.selectFile(this, workingDirectory);

    if (file == null) {
      return;
    }

    sortByExternalIdList(file);
  }

  /**
   * Sort by external id list.
   *
   * @param file
   *          the file
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  private void sortByExternalIdList(Path file) throws IOException {
    BufferedReader reader = FileUtils.newBufferedReader(file);

    String line;

    List<String> ids = new ArrayList<String>();

    try {
      // Skip header
      reader.readLine();

      while ((line = reader.readLine()) != null) {
        if (Io.isEmptyLine(line)) {
          continue;
        }

        List<String> tokens = TextUtils.fastSplit(line, TextUtils.TAB_DELIMITER);

        ids.add(tokens.get(0));
      }
    } finally {
      reader.close();
    }

    // Now find those items in the list of indices

    List<Indexed<Integer, String>> sorted = new ArrayList<Indexed<Integer, String>>();

    Set<Integer> used = new HashSet<Integer>();

    for (String id : ids) {
      for (int i = 0; i < mModel.getRowCount(); ++i) {
        Indexed<Integer, String> v = mModel.get(i);

        if (v.getValue().equals(id)) {
          sorted.add(v);
          used.add(i);
          break;
        }
      }
    }

    // Add all the ids that are not sorted by this method

    for (int i = 0; i < mModel.getRowCount(); ++i) {
      if (used.contains(i)) {
        continue;
      }

      sorted.add(mModel.get(i));
    }

    loadIds(sorted);
  }

  /**
   * Change ids.
   */
  private void changeIds() {
    loadIds(mIds.get(this.mTypeCombo.getSelectedIndex()));
  }

  /**
   * Gets the indices.
   *
   * @return the indices
   */
  public List<Indexed<Integer, String>> getIndices() {

    List<Indexed<Integer, String>> ids = new ArrayList<Indexed<Integer, String>>();

    for (int i = 0; i < mModel.getRowCount(); ++i) {
      ids.add(mModel.get(i));
    }

    return ids;
  }
}
