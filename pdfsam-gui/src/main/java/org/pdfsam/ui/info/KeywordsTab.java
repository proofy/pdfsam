/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 12/mag/2014
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
package org.pdfsam.ui.info;

import static org.sejda.eventstudio.StaticStudio.eventStudio;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.pdfsam.context.DefaultI18nContext;
import org.pdfsam.pdf.PdfDescriptorLoadingStatus;
import org.pdfsam.ui.commons.ShowPdfDescriptorRequest;
import org.sejda.eventstudio.annotation.EventListener;
import org.sejda.model.pdf.PdfMetadataKey;

/**
 * Tab displaying the keywords of the PDF document.
 * 
 * @author Andrea Vacondio
 *
 */
@Named
class KeywordsTab extends Tab {
    private ChangeListener<PdfDescriptorLoadingStatus> loadedListener;
    private Label keywords = new Label();

    KeywordsTab() {
        VBox content = new VBox();
        content.getStyleClass().add("info-props");
        setText(DefaultI18nContext.getInstance().i18n("Keywords"));
        setClosable(false);
        keywords.setWrapText(true);
        keywords.getStyleClass().add("info-property-value");
        content.getChildren().add(keywords);
        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        setContent(scroll);
    }

    @PostConstruct
    void init() {
        eventStudio().addAnnotatedListeners(this);
    }

    @EventListener
    void requestShow(ShowPdfDescriptorRequest event) {
        loadedListener = (o, oldVal, newVal) -> {
            if (newVal == PdfDescriptorLoadingStatus.LOADED) {
                Platform.runLater(() -> {
                    keywords.setText(event.getDescriptor().getInformation(PdfMetadataKey.KEYWORDS.getKey()));
                });
            }
        };
        event.getDescriptor().loadedProperty().addListener(new WeakChangeListener<>(loadedListener));
        keywords.setText(event.getDescriptor().getInformation(PdfMetadataKey.KEYWORDS.getKey()));
    }
}
