package com.dlsc.formsfx.view.controls;

import java.util.Arrays;

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
import com.dlsc.formsfx.model.structure.MultiSelectionField;
import com.dlsc.formsfx.model.structure.SingleSelectionField;
import com.dlsc.formsfx.model.structure.StringField;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

/**
 * @author Sacha Schmid
 * @author Rinesch Murugathas
 */
public class SimpleControlTest extends ApplicationTest {

	@Rule
	public TestFXRule testFXRule = new TestFXRule();

    @Test
    public void itemsTest() {
        MultiSelectionField<Integer> mf = Field.ofMultiSelectionType(Arrays.asList(1, 2, 3), Arrays.asList(1, 2));
        SingleSelectionField<Integer> sf = Field.ofSingleSelectionType(Arrays.asList(1, 2, 3), 1);

        CheckboxListRenderer<Integer> cb = new CheckboxListRenderer<>();
        cb.setField(mf);

        ListViewRenderer<Integer> lv = new ListViewRenderer<>();
        lv.setField(mf);

        RadioButtonRenderer<Integer> rb = new RadioButtonRenderer<>();
        rb.setField(sf);

        ComboBoxRenderer<Integer> cmb = new ComboBoxRenderer<>();
        cmb.setField(sf);

        Assert.assertEquals(3, ((VBox) cb.getChildren().get(1)).getChildren().size());
        Assert.assertTrue(((CheckBox) ((VBox) cb.getChildren().get(1)).getChildren().get(1)).isSelected());

        Assert.assertEquals(3, ((ListView<?>) lv.getChildren().get(1)).getItems().size());
        Assert.assertTrue(((ListView<?>) lv.getChildren().get(1)).getSelectionModel().isSelected(1));

        Assert.assertEquals(3, ((VBox) rb.getChildren().get(1)).getChildren().size());
        Assert.assertTrue(((RadioButton) ((VBox) rb.getChildren().get(1)).getChildren().get(1)).isSelected());

        Assert.assertEquals(3, ((ComboBox<?>) cmb.getChildren().get(1)).getItems().size());
        Assert.assertTrue(((ComboBox<?>) cmb.getChildren().get(1)).getSelectionModel().isSelected(1));

        mf.items(Arrays.asList(1, 2, 3, 4, 5), Arrays.asList(0, 3));
        sf.items(Arrays.asList(1, 2, 3, 4, 5), 3);

        Assert.assertEquals(5, ((VBox) cb.getChildren().get(1)).getChildren().size());
        Assert.assertTrue(((CheckBox) ((VBox) cb.getChildren().get(1)).getChildren().get(0)).isSelected());

        Assert.assertEquals(5, ((ListView<?>) lv.getChildren().get(1)).getItems().size());
        Assert.assertTrue(((ListView<?>) lv.getChildren().get(1)).getSelectionModel().isSelected(0));

        Assert.assertEquals(5, ((VBox) rb.getChildren().get(1)).getChildren().size());
        Assert.assertTrue(((RadioButton) ((VBox) rb.getChildren().get(1)).getChildren().get(3)).isSelected());

        Assert.assertEquals(5, ((ComboBox<?>) cmb.getChildren().get(1)).getItems().size());
        Assert.assertTrue(((ComboBox<?>) cmb.getChildren().get(1)).getSelectionModel().isSelected(3));
    }

    @Test
    public void styleTest() {
        StringField s = Field.ofStringType("test").styleClass("test");
        TextRenderer t = new TextRenderer();
        t.setField(s);

        Assert.assertEquals(3, t.getStyleClass().size());

        s.styleClass("hello", "world");
        Assert.assertEquals(4, t.getStyleClass().size());

        s.styleClass("hi", "world");
        Assert.assertEquals(4, t.getStyleClass().size());
        Assert.assertEquals("world", t.getStyleClass().get(3));
    }

}
