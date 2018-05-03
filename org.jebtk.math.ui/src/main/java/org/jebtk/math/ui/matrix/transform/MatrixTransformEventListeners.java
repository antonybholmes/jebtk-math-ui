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
import org.jebtk.core.event.EventProducer;

/**
 * Listen for when an underlying matrix changes.
 *
 * @author Antony Holmes Holmes
 */
public class MatrixTransformEventListeners
    extends EventProducer<MatrixTransformListener>
    implements MatrixTransformEventProducer {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.lib.bioinformatics.ui.math.matrix.transform.
   * MatrixTransformEventProducer#addMatrixTransformListener(edu.columbia.rdf.
   * lib. bioinformatics.ui.math.matrix.transform.MatrixTransformListener)
   */
  public void addMatrixTransformListener(MatrixTransformListener l) {
    mListeners.add(l);
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.lib.bioinformatics.ui.math.matrix.transform.
   * MatrixTransformEventProducer#removeMatrixTransformListener(edu.columbia.
   * rdf. lib.bioinformatics.ui.math.matrix.transform.MatrixTransformListener)
   */
  public void removeMatrixTransformListener(MatrixTransformListener l) {
    mListeners.remove(l);
  }

  /**
   * Fire matrix transform changed.
   */
  public void fireMatrixTransformChanged() {
    fireMatrixTransformChanged(
        new ChangeEvent(this, MATRIX_TRANSFORM_CHANGED_EVENT));
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.lib.bioinformatics.ui.math.matrix.transform.
   * MatrixTransformEventProducer#fireMatrixTransformChanged(org.abh.lib.event.
   * ChangeEvent)
   */
  public void fireMatrixTransformChanged(ChangeEvent e) {
    for (MatrixTransformListener l : mListeners) {
      l.matrixTransformChanged(e);
    }
  }
}