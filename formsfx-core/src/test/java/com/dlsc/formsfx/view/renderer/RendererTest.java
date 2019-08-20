package com.dlsc.formsfx.view.renderer;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.framework.junit.TestFXRule;

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

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.Section;
import com.dlsc.formsfx.view.controls.CheckboxListRenderer;
import com.dlsc.formsfx.view.controls.RadioButtonRenderer;
import com.dlsc.formsfx.view.controls.TextRenderer;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;

/**
 * @author Sacha Schmid
 * @author Rinesch Murugathas
 */
public class RendererTest extends ApplicationTest {

	@Rule
	public TestFXRule testFXRule = new TestFXRule();

    @Test
    public void formTest() {
        Form f = Form.of(Group.of(), Section.of(), Group.of(), Group.of(), Section.of());
        FormRenderer r = new FormRenderer(f);

        Assert.assertTrue(r.getChildren().get(0) instanceof GroupRenderer);
        Assert.assertEquals(5, r.getChildren().size());
        Assert.assertFalse(r.getChildren().get(0) instanceof SectionRenderer);
        Assert.assertTrue(r.getChildren().get(1) instanceof SectionRenderer);
    }

    @Test
    public void groupTest() {
        Group g = Group.of(
                Field.ofStringType("").span(7),
                Field.ofBooleanType(false).span(8),
                Field.ofMultiSelectionType(Arrays.asList(1, 2, 3), Collections.singletonList(1)),
                Field.ofSingleSelectionType(Arrays.asList(1, 2, 3), 1).render(new RadioButtonRenderer<>())
        );
        GroupRenderer r = new GroupRenderer(g);

        Assert.assertTrue(r.getChildren().get(0) instanceof GridPane);
        Assert.assertTrue(((GridPane) r.getChildren().get(0)).getChildren().get(0) instanceof TextRenderer);
    }

    @Test
    public void sectionTest() {
        Section s = Section.of(
                Field.ofNumber(2.0),
                Field.ofNumber(1),
                Field.ofMultiSelectionType(Arrays.asList(1, 2, 3), Collections.singletonList(1)).render(new CheckboxListRenderer<>()),
                Field.ofSingleSelectionType(Arrays.asList(1, 2, 3), 1)
        );
        SectionRenderer r = new SectionRenderer(s);

        Assert.assertTrue(r.getChildren().get(0) instanceof TitledPane);
        Assert.assertTrue(((TitledPane) r.getChildren().get(0)).getContent() instanceof GridPane);
        Assert.assertTrue(((TitledPane) r.getChildren().get(0)).isExpanded());

        s.collapse(true);

        Assert.assertFalse(((TitledPane) r.getChildren().get(0)).isExpanded());
    }

}
