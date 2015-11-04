/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 09/ott/2014
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
package org.pdfsam.ui.dialog;

import org.pdfsam.ui.support.Style;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Content for the overwrite confirmation dialog.
 * 
 * @author Andrea Vacondio
 *
 */
class OverwriteConfirmationDialogContent extends HBox {

    private Label messageTitle = new Label();
    private Label messageContent = new Label();

    OverwriteConfirmationDialogContent() {
        getStyleClass().addAll(Style.CONTAINER.css());
        messageTitle.getStyleClass().add("-pdfsam-dialog-title");
        messageContent.getStyleClass().add("-pdfsam-dialog-message");
        VBox messages = new VBox(messageTitle, messageContent);
        messages.getStyleClass().add("-pdfsam-dialog-messages");
        getChildren().addAll(GlyphsDude.createIcon(MaterialDesignIcon.ALERT, "42.0"), messages);
        getStyleClass().addAll("-pdfsam-dialog-content");
    }

    void messageTitle(String title) {
        messageTitle.setText(title);
    }

    void messageContent(String title) {
        messageContent.setText(title);
    }
}
