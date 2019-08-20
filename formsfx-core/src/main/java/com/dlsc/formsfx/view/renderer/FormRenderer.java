package com.dlsc.formsfx.view.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.dlsc.formsfx.model.structure.Section;
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

  protected VBox view;
  protected Form form;

  @SuppressWarnings("rawtypes")
  protected List<GroupRendererBase> sections = new ArrayList<>();

  /**
   * This is the constructor to pass over data.
   * 
   * @param form The form which gets rendered.
   */
  public FormRenderer(Form form) {
    this.form = form;

    init();
  }
  
  @Override
  public VBox getView() {
    return view;
  }

  @Override
  public String getUserAgentStylesheet() {
    return getClass().getResource("style.css").toExternalForm();
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
    getChildren().addAll(sections.stream()
        .map(GroupRendererBase::getView)
        .collect(Collectors.toList()));
  }

}
