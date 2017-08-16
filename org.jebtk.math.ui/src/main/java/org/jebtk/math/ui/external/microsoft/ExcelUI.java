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
import java.awt.Frame;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.PathUtils;
import org.jebtk.math.external.microsoft.Excel;
import org.jebtk.math.ui.matrix.EditableMatrixTableModel;
import org.jebtk.modern.dataview.ModernDataCellStyle;
import org.jebtk.modern.dataview.ModernDataModel;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.io.CsvGuiFileFilter;
import org.jebtk.modern.io.FileDialog;
import org.jebtk.modern.io.GuiFileExtFilter;
import org.jebtk.modern.io.RecentFilesService;
import org.jebtk.modern.io.TxtGuiFileFilter;
import org.jebtk.modern.table.ModernTableModel;
import org.jebtk.modern.window.ModernWindow;

// TODO: Auto-generated Javadoc
/**
 * The class ExcelUI.
 */
public class ExcelUI {
	
	/** The Constant EXCEL_FILE_FILTERS. */
	public static final GuiFileExtFilter EXCEL_FILE_FILTERS = 
			new AllExcelGuiFileFilter();

	
	/**
	 * Write xlsx locked.
	 *
	 * @param model the model
	 * @param file the file
	 * @param password the password
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeXlsxLocked(ModernTableModel model, 
			Path file,
			String password) throws IOException {
		XSSFWorkbook workbook = createWorkbook(model);
		
		Excel.writeXlsxLocked(workbook, file, password);
	}
	
	/**
	 * Write.
	 *
	 * @param model the model
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void write(ModernDataModel model, Path file) throws IOException {
		String ext = PathUtils.getFileExt(file);
		
		if (ext.equals(Excel.XLSX_EXTENSION)) {
			writeXlsx(model, file);
		} else {
			ModernDataModel.write(model, file);
		}
	}
	
	/**
	 * Create a workbook from a model and then write it to file.
	 *
	 * @param model the model
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeXlsx(ModernDataModel model, Path file) throws IOException {
		XSSFWorkbook workbook = createWorkbook(model);
		
		Excel.writeXlsx(workbook, file);
	}
	
	/**
	 * Create a workbook from a model.
	 *
	 * @param model the model
	 * @return the XSSF workbook
	 */
	public static XSSFWorkbook createWorkbook(ModernDataModel model) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		//XSSFFont defaultFont = workbook.getFontAt((short)0);
		//workbook.getFontAt((short)0).setFontName("Arial");
		
		//defaultFont.setFontName("Arial");
		//defaultFont.setFontHeightInPoints((short)11);
	
		Sheet sheet = workbook.createSheet("Sheet1");
		
		// Keep track of how many rows we have created.
		int r = 0;
		
		// All cells get a default style
		
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short)11);
		font.setFontName("Arial");

		
		// Because of some stupid bug in POI, black appears as white
		// in the Excel file, so we pick a color very close to black
		// and use that instead
		//font.setColor(new XSSFColor(new Color(1, 1, 1)));
	    
	    XSSFCellStyle headerStyle = workbook.createCellStyle();
	    headerStyle.setFont(font);
	    headerStyle.setWrapText(true);
	    
	    XSSFCellStyle defaultStyle = workbook.createCellStyle();
		defaultStyle.setFont(font);
		defaultStyle.setWrapText(true);
		
		Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
		
		styles.put(ModernDataCellStyle.DEFAULT_STYLE.toString(), defaultStyle);

		XSSFRow row;
		XSSFCell cell;
		
		if (model.getColumnAnnotationNames() != null) {
			// create header row
			row = (XSSFRow)sheet.createRow(r);

			for (int i = 0; i < model.getColumnCount(); ++i) {
				cell = row.createCell(i);

				cell.setCellStyle(headerStyle);
				cell.setCellValue(model.getColumnName(i));
			}

			++r;
		}

		for (int i = 0; i < model.getRowCount(); ++i) {
			row = (XSSFRow)sheet.createRow(r);
			
			for (int j = 0; j < model.getColumnCount(); ++j) {
				cell = row.createCell(j);
				
				Object value = model.getValueAt(i, j); // != null ? model.getValueAt(i, j).toString() : "";
				
				if (value != null) {
					if (value instanceof Double) {
						cell.setCellValue((Double)value);
					} else if (value instanceof Integer) {
						cell.setCellValue((Integer)value);
					} else {
						String s = value.toString();
						
						try {
							cell.setCellValue(Double.parseDouble(s));
						} catch (NumberFormatException e) {
							cell.setCellValue(s);
						}
					}
				}
				
				//cell.setCellValue(new XSSFRichTextString(value));
				
				ModernDataCellStyle style = model.getCellStyle(i, j);
				
				if (!styles.containsKey(style.toString())) {
					//System.err.println(i + " " + j + " " + model.getCellStyle(i, j).toString());
					
					XSSFCellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setFont(font);
					
					// Again we must ignore black and white because POI encodes
					// them incorrectly
					if (!style.getBackground().equals(Color.WHITE) && !style.getBackground().equals(Color.BLACK)) {
						cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
						cellStyle.setFillForegroundColor(new XSSFColor(style.getBackground()));
					}
					
					cellStyle.setFont(workbook.createFont());
					
					if (!style.getColor().equals(Color.WHITE) && !style.getColor().equals(Color.BLACK)) {
						cellStyle.getFont().setColor(new XSSFColor(style.getColor()));
					}
					
					styles.put(style.toString(), cellStyle);
				}
				
				cell.setCellStyle(styles.get(style.toString()));
			}
			
			++r;
		}
		
		//Auto size all the columns
	    for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
	        //sheet.autoSizeColumn(i);
	        
	        sheet.setColumnWidth(i, 256 * 30);
	    }
		
		return workbook;
	}
	
	/**
	 * Gets the text from file.
	 *
	 * @param parent the parent
	 * @param pwd the pwd
	 * @param skipHeader the skip header
	 * @return the text from file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InvalidFormatException the invalid format exception
	 */
	public static List<String> getTextFromFile(ModernWindow parent, 
			Path pwd, 
			boolean skipHeader) throws IOException, InvalidFormatException {
		Path file = openExcelFileDialog(parent, pwd);
		
		if (file != null) {
			return Excel.getTextFromFile(file, skipHeader);
		} else {
			return Collections.emptyList();
		}
	}
	
	/**
	 * Saves a data model an xlsx or text file.
	 *
	 * @param parent the parent
	 * @param model the model
	 * @param pwd the pwd
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void saveXlsxFileDialog(ModernWindow parent, 
			ModernDataModel model, 
			Path pwd) throws IOException {
		Path file = saveXlsxFileDialog(parent, pwd);
		
		if (file == null) {
			return;
		}
		
		if (FileUtils.exists(file)) {
			ModernDialogStatus status = ModernMessageDialog.createFileReplaceDialog(parent, file);

    		if (status == ModernDialogStatus.CANCEL) {
    			saveXlsxFileDialog(parent, model, pwd);

    			return;
    		}
    	}
		
		write(model, file);
		
		RecentFilesService.getInstance().add(file);

		ModernMessageDialog.createFileSavedDialog(parent, parent.getAppInfo().getName(), file);
	}
	
	
	
	/**
	 * We only support saving to xlsx as this is a more open format.
	 *
	 * @param parent the parent
	 * @param pwd the pwd
	 * @return the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Path saveXlsxFileDialog(Frame parent, Path pwd) throws IOException  {
		return FileDialog.saveFile(parent, 
				pwd, 
				new XlsxGuiFileFilter(),
				new TxtGuiFileFilter());
	}
	
	/**
	 * Open excel file dialog.
	 *
	 * @param parent the parent
	 * @param pwd the pwd
	 * @return the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Path openExcelFileDialog(Frame parent, Path pwd) throws IOException  {
		return FileDialog.openFile(parent, 
				pwd, 
				new AllXlsxGuiFileFilter(),
				new XlsxGuiFileFilter(),
				new CsvGuiFileFilter(),
				new TxtGuiFileFilter());
	}
	
	/**
	 * Open excel files dialog.
	 *
	 * @param parent the parent
	 * @param pwd the pwd
	 * @return the list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static List<Path> openExcelFilesDialog(Frame parent, Path pwd) throws IOException  {
		return FileDialog.openFiles(parent, 
				pwd, 
				new AllXlsxGuiFileFilter(),
				new XlsxGuiFileFilter(),
				new CsvGuiFileFilter(),
				new TxtGuiFileFilter());
	}
	
	/**
	 * Load from xlsx.
	 *
	 * @param file the file
	 * @param hasHeader the has header
	 * @param skipMatches the skip matches
	 * @param rowAnnotations the row annotations
	 * @param delimiter the delimiter
	 * @return the matrix table model
	 * @throws InvalidFormatException the invalid format exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static EditableMatrixTableModel loadFromXlsx(Path file,
			boolean hasHeader,
			List<String> skipMatches,
			int rowAnnotations, 
			String delimiter) throws InvalidFormatException, IOException {
		return new EditableMatrixTableModel(Excel.convertToMatrix(file, hasHeader, skipMatches, rowAnnotations, delimiter));
	}
}
