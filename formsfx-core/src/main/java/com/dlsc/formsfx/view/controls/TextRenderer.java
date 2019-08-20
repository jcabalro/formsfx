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

import com.dlsc.formsfx.model.structure.StringField;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

/**
 * This class provides the base implementation for a simple control to edit
 * string values.
 *
 * @author Rinesch Murugathas
 * @author Sacha Schmid
 */
public class TextRenderer extends LabeledRenderer<StringField, TextInputControl> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initializeParts() {
		super.initializeParts();
		getStyleClass().add("simple-text-control");
		if (field.isMultiline()) {
			control = new TextArea(field.getValue());
			control.setPromptText(field.getPlaceholder());
			control.getStyleClass().add("simple-textarea");
		} else {
			control = new TextField(field.getValue());
			control.setPromptText(field.getPlaceholder());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void layoutParts() {
		super.layoutParts();

		if (field.isMultiline()) {
			TextArea txa = (TextArea) control;
			txa.getStyleClass().add("simple-textarea");
			txa.setPrefHeight(80);
			txa.setPrefRowCount(5);
			txa.setWrapText(true);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setupBindings() {
		super.setupBindings();
		control.textProperty().bindBidirectional(field.userInputProperty());
		control.promptTextProperty().bind(field.placeholderProperty());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setupValueChangedListeners() {
		super.setupValueChangedListeners();
	}

}
