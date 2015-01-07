/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 17/ott/2014
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
package org.pdfsam;

import org.junit.Test;

/**
 * @author Andrea Vacondio
 *
 */
@SuppressWarnings("unused")
public class PdfsamTest {
    @Test(expected = IllegalArgumentException.class)
    public void blankName() {
        new Pdfsam(PdfsamEdition.COMMUNITY, " ", "version");
    }

    @Test(expected = IllegalArgumentException.class)
    public void blankVersion() {
        new Pdfsam(PdfsamEdition.COMMUNITY, "name", " ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullEdition() {
        new Pdfsam(null, "name", "version");
    }
}
