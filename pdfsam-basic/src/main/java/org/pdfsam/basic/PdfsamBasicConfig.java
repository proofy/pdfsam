/*
 * This file is part of the PDF Split And Merge source code
 * Created on 22/ott/2013
 * Copyright 2017 by Sober Lemur S.r.l. (info@pdfsam.org).
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
package org.pdfsam.basic;

import jakarta.inject.Named;
import javafx.scene.image.Image;
import org.pdfsam.core.AppBrand;
import org.pdfsam.injector.Auto;
import org.pdfsam.injector.Prototype;
import org.pdfsam.injector.Provides;

import java.io.IOException;
import java.util.List;

/**
 * Configuration for PDFsam Basic Edition
 *
 * @author Andrea Vacondio
 */
public class PdfsamBasicConfig {

    @Provides
    @Named("icons")
    @Prototype
    public List<Image> icons() {
        return List.of(new Image(this.getClass().getResourceAsStream("/images/basic/16x16.png")),
                new Image(this.getClass().getResourceAsStream("/images/basic/24x24.png")),
                new Image(this.getClass().getResourceAsStream("/images/basic/32x32.png")),
                new Image(this.getClass().getResourceAsStream("/images/basic/48x48.png")),
                new Image(this.getClass().getResourceAsStream("/images/basic/64x64.png")),
                new Image(this.getClass().getResourceAsStream("/images/basic/96x96.png")),
                new Image(this.getClass().getResourceAsStream("/images/basic/128x128.png")),
                new Image(this.getClass().getResourceAsStream("/images/basic/256x256.png")),
                new Image(this.getClass().getResourceAsStream("/images/basic/512x512.png")));
    }

    @Provides
    @Auto
    public AppBrand pdfsam() throws IOException {
        return new PdfsamBasic();
    }
}
