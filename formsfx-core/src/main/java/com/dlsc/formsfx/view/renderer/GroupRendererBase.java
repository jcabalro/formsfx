package com.dlsc.formsfx.view.renderer;

/*-
 * ========================LICENSE_START=================================
 * FormsFX
 * %%
 * Copyright (C) 2017 DLSC Software & Consulting
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

import com.dlsc.formsfx.model.structure.Element;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.NodeElement;
import com.dlsc.formsfx.view.util.ViewMixin;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * This class handles shared aspects of groups and sections during rendering.
 *
 * @author Sacha Schmid
 * @author Rinesch Murugathas
 */
public abstract class GroupRendererBase<V extends Group> implements ViewMixin {

  protected StackPane view;

  protected GridPane grid;

  protected V element;

  @Override
  public StackPane getView() {
    return view;
  }

  /**
   *
   */
  @Override
  public void initializeParts() {
    view = new StackPane();
    grid = new GridPane();
    grid.getStyleClass().add("group-grid");
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public void layoutParts() {
    int COLUMN_COUNT = 12;

    for (int i = 0; i < COLUMN_COUNT; i++) {
      ColumnConstraints colConst = new ColumnConstraints();
      colConst.setPercentWidth(100.0 / COLUMN_COUNT);
      grid.getColumnConstraints().add(colConst);
    }

    int currentRow = 0;
    int currentColumnCount = 0;

    // Add the controls in the GridPane in a 12-column layout. If a control
    // takes up too much horizontal space, wrap it to the next row.

    for (Element<?> e : element.getElements()) {
      int span = e.getSpan();

      if (currentColumnCount + span > COLUMN_COUNT) {
        currentRow += 1;
        currentColumnCount = 0;
      }

      if (e instanceof Field) {
        Field f = (Field) e;
        FieldRenderer c = f.getRenderer();
        c.setField(f);
        grid.add(c.getView(), currentColumnCount, currentRow, span, 1);
      } else if (e instanceof NodeElement) {
        grid.add(((NodeElement) e).getNode(), currentColumnCount, currentRow, span, 1);
      }

      currentColumnCount += span;
    }
  }
}
