package com.dlsc.formsfx.view.controls;

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

import com.dlsc.formsfx.model.structure.SingleSelectionField;

import javafx.scene.control.ComboBox;

/**
 * This class provides the base implementation for a simple control to edit
 * combobox values.
 *
 * @author Sacha Schmid
 * @author Rinesch Murugathas
 */
public class ComboBoxRenderer<V> extends LabeledRenderer<SingleSelectionField<V>, ComboBox<V>> {

  /**
   * {@inheritDoc}
   */
  @Override
  public void initializeParts() {
    super.initializeParts();

    getStyleClass().add("simple-select-control");
    control = new ComboBox<>(field.getItems());
    int index = field.getItems().indexOf(field.getSelection());
    control.setValue(field.getSelection());
    control.getSelectionModel().clearAndSelect(index);
    // control.getSelectionModel().select(field.getItems().indexOf(field.getSelection()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void layoutParts() {
    super.layoutParts();
    control.setMaxWidth(Double.MAX_VALUE);
    control.setVisibleRowCount(4);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupValueChangedListeners() {
    super.setupValueChangedListeners();
    control.valueProperty().addListener((ob, o, n) -> {
      field.select(control.getSelectionModel().getSelectedIndex());
    });
    field.itemsProperty().addListener((ob, o, n) -> control.setItems(field.getItems()));
    field.selectionProperty().addListener((observable, oldValue, newValue) -> {
      if (field.getSelection() != null) {
        control.getSelectionModel().select(field.getItems().indexOf(field.getSelection()));
      } else {
        control.getSelectionModel().clearSelection();
      }
    });

  }

}
