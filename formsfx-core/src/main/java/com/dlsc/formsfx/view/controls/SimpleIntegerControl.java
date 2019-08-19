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

import com.dlsc.formsfx.model.structure.IntegerField;

import javafx.scene.control.SpinnerValueFactory;

/**
 * This class provides a specific implementation to edit integer values.
 *
 * @author Rinesch Murugathas
 * @author Sacha Schmid
 */
public class SimpleIntegerControl extends SimpleNumberControl<IntegerField, Number> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeParts() {
        super.initializeParts();

        getStyleClass().addAll("simple-integer-control");
        SpinnerValueFactory<Number> f = new SpinnerValueFactory<Number>() {

          IntegerSpinnerValueFactory vf;
          
          {
            vf = new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE, (Integer) field.getValue());
          }
          
          @Override
          public void decrement(int steps) {
            vf.decrement(steps);
            setValue(vf.getValue());
          }

          @Override
          public void increment(int steps) {
            vf.increment(steps);
            setValue(vf.getValue());
          }          
        };
        editableSpinner.setValueFactory(f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupValueChangedListeners() {
        super.setupValueChangedListeners();

        field.errorMessagesProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(editableSpinner));
        field.tooltipProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(editableSpinner));
    }

}
