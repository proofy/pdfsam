/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 08/apr/2014
 * Copyright 2013-2014 by Andrea Vacondio (andrea.vacondio@gmail.com).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pdfsam.split;

import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.pdfsam.i18n.DefaultI18nContext;
import org.pdfsam.support.params.SinglePdfSourceMultipleOutputParametersBuilder;
import org.pdfsam.ui.commons.RadioButtonDrivenTextFieldsPane;
import org.pdfsam.ui.commons.ValidableTextField;
import org.pdfsam.ui.support.Style;
import org.pdfsam.ui.workspace.RestorableView;
import org.sejda.model.parameter.AbstractSplitByPageParameters;
import org.sejda.model.pdf.page.PredefinedSetOfPages;

/**
 * Panel for the Split options
 * 
 * @author Andrea Vacondio
 *
 */
class SplitOptionsPane extends VBox implements SplitParametersBuilderCreator, RestorableView {

    private PredefinedSetOfPagesRadioButton burst = new PredefinedSetOfPagesRadioButton(PredefinedSetOfPages.ALL_PAGES,
            DefaultI18nContext.getInstance().i18n("Burst (Split into single pages)"));
    private PredefinedSetOfPagesRadioButton even = new PredefinedSetOfPagesRadioButton(PredefinedSetOfPages.EVEN_PAGES,
            DefaultI18nContext.getInstance().i18n("Split even pages"));
    private PredefinedSetOfPagesRadioButton odd = new PredefinedSetOfPagesRadioButton(PredefinedSetOfPages.ODD_PAGES,
            DefaultI18nContext.getInstance().i18n("Split odd pages"));

    private ToggleGroup group = new ToggleGroup();
    private SplitAfterRadioButton splitAfter;
    private SplitByEveryRadioButton splitByEvery;

    SplitOptionsPane() {
        super(Style.DEFAULT_SPACING);
        ValidableTextField splitAfterField = new ValidableTextField();
        splitAfter = new SplitAfterRadioButton(splitAfterField);
        ValidableTextField splitByEveryField = new ValidableTextField();
        splitByEvery = new SplitByEveryRadioButton(splitByEveryField);
        RadioButtonDrivenTextFieldsPane grid = new RadioButtonDrivenTextFieldsPane(group);
        burst.setToggleGroup(group);
        burst.setTooltip(new Tooltip(DefaultI18nContext.getInstance().i18n("Explode the document into single pages")));
        burst.setSelected(true);
        even.setToggleGroup(group);
        even.setTooltip(new Tooltip(DefaultI18nContext.getInstance().i18n("Split the document after every even page")));
        odd.setToggleGroup(group);
        odd.setTooltip(new Tooltip(DefaultI18nContext.getInstance().i18n("Split the document after every odd page")));
        splitAfter.setToggleGroup(group);
        splitByEvery.setToggleGroup(group);

        grid.addRow(splitAfter, splitAfterField);
        grid.addRow(splitByEvery, splitByEveryField);

        HBox simpleSplit = new HBox(20, burst, even, odd);
        simpleSplit.getStyleClass().addAll(Style.VITEM.css());
        getStyleClass().addAll(Style.CONTAINER.css());
        getChildren().addAll(simpleSplit, grid);
    }

    public SinglePdfSourceMultipleOutputParametersBuilder<? extends AbstractSplitByPageParameters> getBuilder(
            Consumer<String> onError) {
        return ((SplitParametersBuilderCreator) group.getSelectedToggle()).getBuilder(onError);
    }

    public void saveStateTo(Map<String, String> data) {
        burst.saveStateTo(data);
        even.saveStateTo(data);
        odd.saveStateTo(data);
        splitAfter.saveStateTo(data);
        splitByEvery.saveStateTo(data);
    }

    public void restoreStateFrom(Map<String, String> data) {
        burst.restoreStateFrom(data);
        even.restoreStateFrom(data);
        odd.restoreStateFrom(data);
        splitAfter.restoreStateFrom(data);
        splitByEvery.restoreStateFrom(data);
    }
}
