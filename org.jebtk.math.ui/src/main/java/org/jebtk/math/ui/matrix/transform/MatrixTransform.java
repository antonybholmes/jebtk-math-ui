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

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.math.matrix.DataFrame;
import org.jebtk.modern.window.ModernWindow;

/**
 * Wrapper for providing a GUI to edit matrix transformations.
 * 
 * @author Antony Holmes
 *
 */
public class MatrixTransform implements MatrixTransformEventProducer {

  /**
   * The member name.
   */
  private String mName;

  /**
   * The member input matrix.
   */
  protected DataFrame mMatrix;

  /**
   * The member description.
   */
  private String mDescription;

  /**
   * The member parent.
   */
  protected ModernWindow mParent;

  /**
   * The listeners.
   */
  private MatrixTransformEventListeners mListeners = new MatrixTransformEventListeners();

  /**
   * Instantiates a new matrix transform.
   *
   * @param parent the parent
   * @param name the name
   * @param inputMatrix the input matrix
   */
  public MatrixTransform(ModernWindow parent, String name,
      DataFrame inputMatrix) {
    this(parent, name, name, inputMatrix);
  }

  /**
   * Instantiates a new matrix transform.
   *
   * @param parent the parent
   * @param name the name
   * @param description the description
   * @param inputMatrix the input matrix
   */
  public MatrixTransform(ModernWindow parent, String name, String description,
      DataFrame inputMatrix) {
    mParent = parent;
    mName = name;
    mDescription = description;
    mMatrix = inputMatrix;
  }

  /**
   * Sets the matrix.
   *
   * @param inputMatrix the new matrix
   */
  public void setMatrix(DataFrame inputMatrix) {
    mMatrix = inputMatrix;
  }

  /**
   * Gets the matrix.
   *
   * @return the matrix
   */
  public DataFrame getMatrix() {
    return mMatrix;
  }

  /**
   * Gets the parent.
   *
   * @return the parent
   */
  public ModernWindow getParent() {
    return mParent;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return mName;
  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return mDescription;
  }

  /**
   * Apply transform via ui action. This action is only called when the user
   * clicks on an item to activate it and therefore can be used to separate
   * actions that should be automatically run and those that should be started
   * only when the user does something.
   */
  public void uiApply() {
    apply();
  }

  /**
   * Enable gui editing of function.
   */
  public void apply() {
    mListeners.fireMatrixTransformChanged();
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.lib.bioinformatics.ui.math.matrix.transform.
   * MatrixTransformEventProducer#addMatrixTransformListener(edu.columbia.rdf.
   * lib. bioinformatics.ui.math.matrix.transform.MatrixTransformListener)
   */
  @Override
  public void addMatrixTransformListener(MatrixTransformListener l) {
    mListeners.addMatrixTransformListener(l);
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.lib.bioinformatics.ui.math.matrix.transform.
   * MatrixTransformEventProducer#removeMatrixTransformListener(edu.columbia.
   * rdf. lib.bioinformatics.ui.math.matrix.transform.MatrixTransformListener)
   */
  @Override
  public void removeMatrixTransformListener(MatrixTransformListener l) {
    mListeners.removeMatrixTransformListener(l);
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.lib.bioinformatics.ui.math.matrix.transform.
   * MatrixTransformEventProducer#fireMatrixTransformChanged(org.abh.lib.event.
   * ChangeEvent)
   */
  @Override
  public void fireMatrixTransformChanged(ChangeEvent event) {
    mListeners.fireMatrixTransformChanged(event);
  }
}
