/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 03/mag/2014
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
package org.pdfsam.ui.banner;

import javafx.geometry.Side;
import javafx.scene.control.Tooltip;

import javax.inject.Inject;
import javax.inject.Named;

import org.pdfsam.i18n.DefaultI18nContext;

import de.jensd.fx.fontawesome.AwesomeIcon;

/**
 * Button to open the menu
 * 
 * @author Andrea Vacondio
 *
 */
@Named
class MenuButton extends BannerButton {

    @Inject
    MenuButton(AppContextMenu menu) {
        super(AwesomeIcon.BARS);
        setTooltip(new Tooltip(DefaultI18nContext.getInstance().i18n("Application menu")));
        setOnAction(e -> menu.show(this, Side.BOTTOM, 0, 0));
    }
}
