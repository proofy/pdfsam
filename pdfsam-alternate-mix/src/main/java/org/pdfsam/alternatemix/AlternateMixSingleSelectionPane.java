/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 27/giu/2014
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
package org.pdfsam.alternatemix;

import java.util.function.Consumer;

import org.pdfsam.i18n.DefaultI18nContext;
import org.pdfsam.support.params.TaskParametersBuildStep;
import org.pdfsam.ui.selection.single.SingleSelectionPane;
import org.pdfsam.ui.support.FXValidationSupport.ValidationState;
import org.sejda.model.input.PdfFileSource;

/**
 * {@link SingleSelectionPane} used by the alternate mix module
 * 
 * @author Andrea Vacondio
 *
 */
abstract class AlternateMixSingleSelectionPane extends SingleSelectionPane implements
        TaskParametersBuildStep<AlternateMixParametersBuilder> {

    public AlternateMixSingleSelectionPane(String ownerModule) {
        super(ownerModule);
    }

    public void apply(AlternateMixParametersBuilder builder, Consumer<String> onError) {
        getField().getTextField().validate();
        if (getField().getTextField().getValidationState() == ValidationState.VALID) {
            onValidSource(builder, getPdfDocumentDescriptor().toPdfFileSource());
        } else {
            onError.accept(DefaultI18nContext.getInstance().i18n("The selected PDF document is invalid"));
        }
    }

    abstract void onValidSource(AlternateMixParametersBuilder builder, PdfFileSource source);
}
