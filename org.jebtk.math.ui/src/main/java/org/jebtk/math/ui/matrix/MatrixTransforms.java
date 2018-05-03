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

import org.jebtk.math.matrix.DataFrame;
import org.jebtk.math.matrix.utils.MatrixArithmetic;
import org.jebtk.math.matrix.utils.MatrixOperations;
import org.jebtk.math.ui.MeanFilterDialog;
import org.jebtk.math.ui.MinExpFilterDialog;
import org.jebtk.math.ui.MinMaxThresholdDialog;
import org.jebtk.math.ui.MinThresholdDialog;
import org.jebtk.math.ui.StdDevFilterDialog;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.window.ModernWindow;

/**
 * The class MatrixTransforms.
 */
public class MatrixTransforms {
  /**
   * Min threshold.
   *
   * @param parent the parent
   * @param matrix the matrix
   * @param min the min
   * @return the annotation matrix
   */
  public static DataFrame minThreshold(ModernWindow parent,
      DataFrame matrix,
      double min) {
    MinThresholdDialog dialog = new MinThresholdDialog(parent, min);

    dialog.setVisible(true);

    if (dialog.getStatus() == ModernDialogStatus.OK) {
      return MatrixOperations.min(matrix, dialog.getValue());
    } else {
      return null;
    }
  }

  /**
   * Subtract.
   *
   * @param parent the parent
   * @param matrix the matrix
   * @param min the min
   * @return the annotation matrix
   */
  public static DataFrame subtract(ModernWindow parent,
      DataFrame matrix,
      double min) {
    MinThresholdDialog dialog = new MinThresholdDialog(parent, min);

    dialog.setVisible(true);

    if (dialog.getStatus() == ModernDialogStatus.OK) {
      DataFrame ret = new DataFrame(matrix, true);
      MatrixArithmetic.subtract(dialog.getValue(), ret);
      return ret;
    } else {
      return null;
    }
  }

  /**
   * Min max threshold.
   *
   * @param parent the parent
   * @param matrix the matrix
   * @param min the min
   * @param max the max
   * @return the annotation matrix
   */
  public static DataFrame minMaxThreshold(ModernWindow parent,
      DataFrame matrix,
      double min,
      double max) {
    MinMaxThresholdDialog dialog = new MinMaxThresholdDialog(parent, min, max);

    dialog.setVisible(true);

    if (dialog.getStatus() == ModernDialogStatus.OK) {
      return MatrixOperations
          .threshold(matrix, dialog.getMin(), dialog.getMax());
    } else {
      return null;
    }
  }

  /**
   * Std dev filter.
   *
   * @param parent the parent
   * @param matrix the matrix
   * @param min the min
   * @return the annotation matrix
   */
  public static DataFrame stdDevFilter(ModernWindow parent,
      DataFrame matrix,
      double min) {
    StdDevFilterDialog dialog = new StdDevFilterDialog(parent, min);

    dialog.setVisible(true);

    if (dialog.getStatus() == ModernDialogStatus.OK) {
      return MatrixOperations.stdDevFilter(matrix, dialog.getMin());
    } else {
      return null;
    }
  }

  /**
   * Mean filter.
   *
   * @param parent the parent
   * @param matrix the matrix
   * @param min the min
   * @return the annotation matrix
   */
  public static DataFrame meanFilter(ModernWindow parent,
      DataFrame matrix,
      double min) {
    MeanFilterDialog dialog = new MeanFilterDialog(parent, min);

    dialog.setVisible(true);

    if (dialog.getStatus() == ModernDialogStatus.OK) {
      return MatrixOperations.meanFilter(matrix, dialog.getMin());
    } else {
      return null;
    }
  }

  /**
   * Normalize.
   *
   * @param parent the parent
   * @param matrix the matrix
   * @return the annotation matrix
   */
  public static DataFrame normalize(ModernWindow parent, DataFrame matrix) {
    return MatrixOperations.normalize(matrix);
  }

  /**
   * Min exp filter.
   *
   * @param parent the parent
   * @param matrix the matrix
   * @param minExp the min exp
   * @param minSamples the min samples
   * @return the annotation matrix
   */
  public static DataFrame minExpFilter(ModernWindow parent,
      DataFrame matrix,
      double minExp,
      int minSamples) {
    MinExpFilterDialog dialog = new MinExpFilterDialog(parent, minExp,
        minSamples);

    dialog.setVisible(true);

    if (dialog.getStatus() == ModernDialogStatus.OK) {
      return MatrixOperations
          .minExpFilter(matrix, dialog.getMinExp(), dialog.getMinSamples());
    } else {
      return null;
    }
  }

}
