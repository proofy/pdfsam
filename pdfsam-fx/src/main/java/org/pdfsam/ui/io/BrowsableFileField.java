/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 30/ott/2013
 * Copyright 2013 by Andrea Vacondio (andrea.vacondio@gmail.com).
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
package org.pdfsam.ui.io;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.trim;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

import org.apache.commons.lang3.ObjectUtils;
import org.pdfsam.context.DefaultI18nContext;
import org.pdfsam.support.io.FileType;
import org.pdfsam.support.validation.Validator;
import org.pdfsam.support.validation.Validators;

/**
 * Component letting the user select a File of an expected type. By default no validation is enforced and the filetype is used only in the file chooser but the component provides a
 * method to initialize validation.
 * 
 * @author Andrea Vacondio
 * 
 */
public class BrowsableFileField extends BrowsableField {

    private final FileType fileType;
    private BrowseEventHandler handler = new BrowseEventHandler();

    public BrowsableFileField(FileType fileType) {
        setBrowseWindowTitle(DefaultI18nContext.getInstance().i18n("Select a file"));
        getBrowseButton().setOnAction(handler);
        getTextField().setOnAction(handler);
        this.fileType = ObjectUtils.defaultIfNull(fileType, FileType.ALL);
        if (FileType.ALL != fileType) {
            getTextField().setPromptText(
                    String.format("%s: %s", DefaultI18nContext.getInstance().i18n("Select a file"), fileType
                            .getFilter().getExtensions()));
        } else {
            getTextField().setPromptText(DefaultI18nContext.getInstance().i18n("Select a file"));
        }
        setOnDragOver(e -> dragConsume(e, this.onDragOverConsumer()));
        setOnDragDropped(e -> dragConsume(e, this.onDragDropped()));
    }

    /**
     * Configure validation for the field
     * 
     * @param selectedFileMustExists
     * @param allowBlankString
     */
    public void enforceValidation(boolean selectedFileMustExists, boolean allowBlankString) {
        Validator<String> validator = Validators.newFileTypeString(fileType, selectedFileMustExists);
        if (allowBlankString) {
            validator = Validators.decorateAsValidBlankString(validator);
        }
        getTextField().setValidator(validator);
        getTextField().setErrorMessage(buildErrorMessage(selectedFileMustExists));
    }

    private String buildErrorMessage(boolean selectedFileMustExists) {
        String errorMessage = selectedFileMustExists ? DefaultI18nContext.getInstance().i18n(
                "The selected file must exist. ") : "";
        if (FileType.ALL != fileType) {
            errorMessage += DefaultI18nContext.getInstance().i18n("Allowed extensions are {0}",
                    fileType.getFilter().getExtensions().toString());
        }
        return trim(errorMessage);
    }

    /**
     * {@link EventHandler} opening the {@link javafx.stage.FileChooser} and letting the user select the input file populating the ValidableTextField.
     * 
     * @author Andrea Vacondio
     * 
     */
    private class BrowseEventHandler implements EventHandler<ActionEvent> {

        public void handle(ActionEvent event) {
            RememberingLatestFileChooserWrapper fileChooser = FileChoosers.getFileChooser(fileType,
                    getBrowseWindowTitle());
            String currentSelection = getTextField().getText();
            if (isNotBlank(currentSelection)) {
                Path path = Paths.get(currentSelection);
                if (Files.exists(path)) {
                    fileChooser.setInitialDirectory(path.getParent().toFile());
                    fileChooser.setInitialFileName(path.getFileName().toString());
                }
            }
            setTextFromFile(fileChooser.showOpenDialog(getTextField().getScene().getWindow()));
        }
    }

    @Override
    void setTextFromFile(File inputFile) {
        if (inputFile != null) {
            getTextField().setText(inputFile.getAbsolutePath());
            getTextField().validate();
        }
    }

    private void dragConsume(DragEvent e, Consumer<DragEvent> c) {
        List<File> files = e.getDragboard().getFiles();
        if (files != null && !files.isEmpty()) {
            c.accept(e);
        }
        e.consume();
    }

    private Consumer<DragEvent> onDragOverConsumer() {
        return (DragEvent e) -> {
            e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        };
    }

    private Consumer<DragEvent> onDragDropped() {
        return (DragEvent e) -> {
            e.getDragboard().getFiles().stream().filter(f -> fileType.matches(f.getName())).findFirst()
                    .ifPresent((file) -> setTextFromFile(file));
            e.setDropCompleted(true);
        };
    }
}
