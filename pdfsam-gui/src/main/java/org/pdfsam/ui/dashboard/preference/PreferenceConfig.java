/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 21/lug/2014
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
package org.pdfsam.ui.dashboard.preference;

import javax.inject.Inject;

import org.pdfsam.context.BooleanUserPreference;
import org.pdfsam.context.DefaultI18nContext;
import org.pdfsam.context.StringUserPreference;
import org.pdfsam.context.UserContext;
import org.pdfsam.support.KeyStringValueItem;
import org.pdfsam.support.LocaleKeyValueItem;
import org.pdfsam.support.io.FileType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for the PDFsam preferences components
 * 
 * @author Andrea Vacondio
 *
 */
@Configuration
public class PreferenceConfig {

    @Inject
    private UserContext userContext;

    @Bean(name = "localeCombo")
    public PreferenceComboBox<LocaleKeyValueItem> localeCombo() {
        return new PreferenceComboBox<>(StringUserPreference.LOCALE, userContext);
    }

    @Bean(name = "themeCombo")
    public PreferenceComboBox<KeyStringValueItem<String>> themeCombo() {
        PreferenceComboBox<KeyStringValueItem<String>> themeCombo = new PreferenceComboBox<>(
                StringUserPreference.THEME, userContext);
        themeCombo.getItems().add(new KeyStringValueItem<>("cornflower.css", "Cornflower"));
        themeCombo.getItems().add(new KeyStringValueItem<>("gray.css", "Gray"));
        themeCombo.getItems().add(new KeyStringValueItem<>("green.css", "Green"));
        themeCombo.getItems().add(new KeyStringValueItem<>("orchid.css", "Orchid"));
        themeCombo.getItems().add(new KeyStringValueItem<>("seagreen.css", "Sea Green"));
        themeCombo.getItems().add(new KeyStringValueItem<>("sienna.css", "Sienna"));
        themeCombo.setValue(new KeyStringValueItem<>(userContext.getTheme(), ""));
        return themeCombo;
    }

    @Bean(name = "thumbnailsCombo")
    public PreferenceComboBox<KeyStringValueItem<String>> thumbnailsCombo() {
        return new PreferenceComboBox<>(StringUserPreference.THUMBNAILS_IDENTIFIER, userContext);
    }

    @Bean(name = "checkForUpdates")
    public PreferenceCheckBox checkForUpdates() {
        return new PreferenceCheckBox(BooleanUserPreference.CHECK_UPDATES, DefaultI18nContext.getInstance().i18n(
                "Check for updates at startup"), userContext.isCheckForUpdates(), userContext);
    }

    @Bean(name = "playSounds")
    public PreferenceCheckBox playSounds() {
        return new PreferenceCheckBox(BooleanUserPreference.PLAY_SOUNDS, DefaultI18nContext.getInstance().i18n(
                "Play alert sounds"), userContext.isPlaySounds(), userContext);
    }

    @Bean(name = "askConfirmation")
    public PreferenceCheckBox askConfirmation() {
        return new PreferenceCheckBox(BooleanUserPreference.ASK_OVERWRITE_CONFIRMATION, DefaultI18nContext
                .getInstance().i18n("Ask for confirmation when the \"Overwrite\" checkbox is selected"),
                userContext.isAskOverwriteConfirmation(), userContext);
    }

    @Bean(name = "highQualityThumbnails")
    public PreferenceCheckBox highQualityThumbnails() {
        return new PreferenceCheckBox(BooleanUserPreference.HIGH_QUALITY_THUMB, DefaultI18nContext.getInstance().i18n(
                "High quality thumbnails"), userContext.isHighQualityThumbnails(), userContext);
    }

    @Bean(name = "smartRadio")
    public PreferenceRadioButton smartRadio() {
        return new PreferenceRadioButton(BooleanUserPreference.SMART_OUTPUT, DefaultI18nContext.getInstance().i18n(
                "Use the selected PDF document directory as output directory"), userContext.isUseSmartOutput(),
                userContext);
    }

    @Bean(name = "workingDirectory")
    public PreferenceBrowsableDirectoryField workingDirectory() {
        PreferenceBrowsableDirectoryField workingDirectory = new PreferenceBrowsableDirectoryField(
                StringUserPreference.WORKING_PATH, userContext);
        workingDirectory.getTextField().setText(userContext.getDefaultWorkingPath());
        return workingDirectory;
    }

    @Bean(name = "workspace")
    public PreferenceBrowsableFileField workspace() {
        PreferenceBrowsableFileField workspace = new PreferenceBrowsableFileField(StringUserPreference.WORKSPACE_PATH,
                FileType.XML, userContext);
        workspace.getTextField().setText(userContext.getDefaultWorkspacePath());
        return workspace;
    }

}
