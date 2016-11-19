/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 12 ago 2016
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
package org.pdfsam.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.pdfsam.module.ModuleInputOutputType;

/**
 * @author Andrea Vacondio
 *
 */
public class InputPdfArgumentsLoadRequestTest {

    private InputPdfArgumentsLoadRequest victim = new InputPdfArgumentsLoadRequest();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void requiredInputTyle() throws Exception {
        victim.pdfs.add(folder.newFile().toPath());
        assertEquals(ModuleInputOutputType.SINGLE_PDF, victim.requiredInputTyle());
        victim.pdfs.add(folder.newFile().toPath());
        assertEquals(ModuleInputOutputType.MULTIPLE_PDF, victim.requiredInputTyle());
    }

}
