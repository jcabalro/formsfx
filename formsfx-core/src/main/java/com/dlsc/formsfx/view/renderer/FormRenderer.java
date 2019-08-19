package com.dlsc.formsfx.view.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.dlsc.formsfx.model.structure.BooleanField;
import com.dlsc.formsfx.model.structure.DateField;
import com.dlsc.formsfx.model.structure.DoubleField;

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

import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.IntegerField;
import com.dlsc.formsfx.model.structure.MultiSelectionField;
import com.dlsc.formsfx.model.structure.PasswordField;
import com.dlsc.formsfx.model.structure.Section;
import com.dlsc.formsfx.model.structure.SingleSelectionField;
import com.dlsc.formsfx.model.structure.StringField;
import com.dlsc.formsfx.view.controls.SimpleBooleanControl;
import com.dlsc.formsfx.view.controls.SimpleComboBoxControl;
import com.dlsc.formsfx.view.controls.SimpleDateControl;
import com.dlsc.formsfx.view.controls.SimpleDoubleControl;
import com.dlsc.formsfx.view.controls.SimpleIntegerControl;
import com.dlsc.formsfx.view.controls.SimpleListViewControl;
import com.dlsc.formsfx.view.controls.SimplePasswordControl;
import com.dlsc.formsfx.view.controls.SimpleTextControl;
import com.dlsc.formsfx.view.util.ViewMixin;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

/**
 * This class is used to render a form.
 *
 * @author Sacha Schmid
 * @author Rinesch Murugathas
 */
public class FormRenderer extends VBox implements ViewMixin {

	private Map<Class<?>, Class<?>> controls = new HashMap<>();

	{
		// Default Controls
		controls.put(BooleanField.class, SimpleBooleanControl.class);
		controls.put(DateField.class, SimpleDateControl.class);
		controls.put(DoubleField.class, SimpleDoubleControl.class);
		controls.put(IntegerField.class, SimpleIntegerControl.class);
		controls.put(MultiSelectionField.class, SimpleListViewControl.class);
		controls.put(PasswordField.class, SimplePasswordControl.class);
		controls.put(SingleSelectionField.class, SimpleComboBoxControl.class);
		controls.put(StringField.class, SimpleTextControl.class);
	}

    protected Form form;

    @SuppressWarnings("rawtypes")
	protected List<GroupRendererBase> sections = new ArrayList<>();

    /**
     * This is the constructor to pass over data.
     * @param form
     *              The form which gets rendered.
     */
    public FormRenderer(Form form) {
        this.form = form;

        init();
    }

    @Override
    public String getUserAgentStylesheet() {
        return FormRenderer.class.getResource("style.css").toExternalForm();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeParts() {
        sections = form.getGroups().stream()
                .map(s -> {
                    if (s instanceof Section) {
                        return new SectionRenderer((Section) s);
                    } else {
                        return new GroupRenderer(s);
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void layoutParts() {
        getStyleClass().add("formsfx-form");

        setPadding(new Insets(10));
        getChildren().addAll(sections);
    }

}
