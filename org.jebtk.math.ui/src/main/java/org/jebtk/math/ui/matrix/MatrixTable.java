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

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.List;

import org.jebtk.core.Mathematics;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.modern.table.ModernSpreadsheet;
import org.jebtk.modern.table.ModernTableNumericCellRenderer;
import org.jebtk.modern.zoom.ZoomModel;

// TODO: Auto-generated Javadoc
/**
 * Table model for displaying matrices.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class MatrixTable extends ModernSpreadsheet {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new matrix table.
	 */
	public MatrixTable() {
		setFormat();
	}

	public MatrixTable(ZoomModel zoomModel) {
		this();
		
		setZoomModel(zoomModel);
	}

	/**
	 * Sets the format.
	 */
	public void setFormat() {
		mCellRendererModel.setDefault(new ModernTableNumericCellRenderer());

		fireDataUpdated();
	}

	/**
	 * Gets the selected columns.
	 *
	 * @return the selected columns
	 */
	public List<Integer> getSelectedColumns() {
		if (mModel == null) {
			return Collections.emptyList();
		}

		return Mathematics.subtract(CollectionUtils.toList(getColumnModel().getSelectionModel()), 
				((MatrixTableModel)mModel).getMatrix().getRowAnnotationNames().size());
	}

	/**
	 * Gets the selected rows.
	 *
	 * @return the selected rows
	 */
	public List<Integer> getSelectedRows() {
		if (mModel == null) {
			return Collections.emptyList();
		}
		
		return Mathematics.subtract(CollectionUtils.toList(getRowModel().getSelectionModel()), 
				((MatrixTableModel)mModel).getMatrix().getColumnAnnotationNames().size());
	}
	
	/* (non-Javadoc)
	 * @see org.abh.common.ui.graphics.ModernCanvas#drawBackground(java.awt.Graphics2D)
	 */
	@Override
	public void drawBackground(Graphics2D g2) {
		fill(g2, Color.WHITE);
	}
}