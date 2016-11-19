/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 02/lug/2014
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
package org.pdfsam.support.params;

import java.util.Set;

import org.sejda.common.collection.NullSafeSet;
import org.sejda.model.input.PdfFileSource;
import org.sejda.model.output.DirectoryTaskOutput;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;

/**
 * Abstract builder for a {@link MultiplePdfSourceMultipleOutputParameters}
 * 
 * @author Andrea Vacondio
 * @param <P>
 *            type of parameter built
 */
public abstract class MultiplePdfSourceMultipleOutputParametersBuilder<P extends MultiplePdfSourceMultipleOutputParameters>
        extends AbstractPdfOutputParametersBuilder<P> implements MultipleOutputTaskParametersBuilder<P> {

    private Set<PdfFileSource> inputs = new NullSafeSet<>();
    private DirectoryTaskOutput output;
    private String prefix;

    public void addSource(PdfFileSource input) {
        this.inputs.add(input);
    }

    @Override
    public void prefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void output(DirectoryTaskOutput output) {
        this.output = output;
    }

    public DirectoryTaskOutput getOutput() {
        return output;
    }

    public String getPrefix() {
        return prefix;
    }

    public Set<PdfFileSource> getInputs() {
        return inputs;
    }

    public boolean hasInput() {
        return !inputs.isEmpty();
    }
}
