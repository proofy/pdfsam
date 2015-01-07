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

import static org.pdfsam.support.KeyStringValueItem.keyEmptyValue;
import static org.pdfsam.support.KeyStringValueItem.keyValue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.control.Tooltip;

import javax.inject.Inject;

import org.pdfsam.context.BooleanUserPreference;
import org.pdfsam.context.IntUserPreference;
import org.pdfsam.context.StringUserPreference;
import org.pdfsam.context.UserContext;
import org.pdfsam.i18n.DefaultI18nContext;
import org.pdfsam.module.Module;
import org.pdfsam.module.ModuleKeyValueItem;
import org.pdfsam.support.KeyStringValueItem;
import org.pdfsam.support.LocaleKeyValueItem;
import org.pdfsam.support.io.FileType;
import org.pdfsam.support.validation.Validators;
import org.pdfsam.ui.NewsPolicy;
import org.pdfsam.ui.Theme;
import org.pdfsam.ui.io.RememberingLatestFileChooserWrapper.OpenType;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Configuration for the PDFsam preferences components
 * 
 * @author Andrea Vacondio
 *
 */
@Configuration
public class PreferenceConfig {

    private static final Integer THUMB_SIZE_LOWER = 130;
    private static final Integer THUMB_SIZE_UPPER = 390;

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
        themeCombo.setId("themeCombo");
        themeCombo.getItems().addAll(
                Arrays.stream(Theme.values()).map(t -> keyValue(t.toString(), t.friendlyName()))
                        .collect(Collectors.toList()));

        themeCombo.setValue(keyEmptyValue(userContext.getTheme()));
        return themeCombo;
    }

    @Bean(name = "startupModuleCombo")
    public PreferenceComboBox<KeyStringValueItem<String>> startupModuleCombo(List<Module> modules) {
        PreferenceComboBox<KeyStringValueItem<String>> startupModuleCombo = new PreferenceComboBox<>(
                StringUserPreference.STARTUP_MODULE, userContext);
        startupModuleCombo.setId("startupModuleCombo");
        startupModuleCombo.getItems().add(keyValue("", DefaultI18nContext.getInstance().i18n("Dashboard")));
        modules.stream().map(ModuleKeyValueItem::new).forEach(startupModuleCombo.getItems()::add);
        startupModuleCombo.setValue(keyEmptyValue(userContext.getStartupModule()));
        return startupModuleCombo;
    }

    @Bean(name = "newsDisplayPolicy")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PreferenceComboBox<KeyStringValueItem<String>> newsDisplayPolicy() {
        PreferenceComboBox<KeyStringValueItem<String>> newsDisplayPolicyCombo = new PreferenceComboBox<>(
                StringUserPreference.NEWS_POLICY, userContext);
        newsDisplayPolicyCombo.setId("newsPolicy");
        newsDisplayPolicyCombo.getItems().addAll(
                Arrays.stream(NewsPolicy.values()).map(t -> keyValue(t.toString(), t.friendlyName()))
                        .collect(Collectors.toList()));

        newsDisplayPolicyCombo.setValue(keyEmptyValue(userContext.getNewsPolicy()));
        return newsDisplayPolicyCombo;
    }

    @Bean(name = "thumbnailsCombo")
    public PreferenceComboBox<KeyStringValueItem<String>> thumbnailsCombo() {
        return new PreferenceComboBox<>(StringUserPreference.THUMBNAILS_IDENTIFIER, userContext);
    }

    @Bean(name = "checkForUpdates")
    public PreferenceCheckBox checkForUpdates() {
        PreferenceCheckBox checkForUpdates = new PreferenceCheckBox(BooleanUserPreference.CHECK_UPDATES,
                DefaultI18nContext.getInstance().i18n("Check for updates at startup"), userContext.isCheckForUpdates(),
                userContext);
        checkForUpdates.setId("checkForUpdates");
        checkForUpdates.setTooltip(new Tooltip(DefaultI18nContext.getInstance().i18n(
                "Set whether new version availability should be checked on startup (restart needed)")));
        checkForUpdates.getStyleClass().add("spaced-vitem");
        return checkForUpdates;
    }

    @Bean(name = "playSounds")
    public PreferenceCheckBox playSounds() {
        PreferenceCheckBox playSounds = new PreferenceCheckBox(BooleanUserPreference.PLAY_SOUNDS, DefaultI18nContext
                .getInstance().i18n("Play alert sounds"), userContext.isPlaySounds(), userContext);
        playSounds.setId("playSounds");
        playSounds.setTooltip(new Tooltip(DefaultI18nContext.getInstance().i18n("Turn on or off alert sounds")));
        playSounds.getStyleClass().add("spaced-vitem");
        return playSounds;
    }

    @Bean(name = "highQualityThumbnails")
    public PreferenceCheckBox highQualityThumbnails() {
        PreferenceCheckBox highQualityThumbnails = new PreferenceCheckBox(BooleanUserPreference.HIGH_QUALITY_THUMB,
                DefaultI18nContext.getInstance().i18n("High quality thumbnails"),
                userContext.isHighQualityThumbnails(), userContext);
        highQualityThumbnails.setId("highQualityThumbnails");
        return highQualityThumbnails;
    }

    @Bean(name = "smartRadio")
    public PreferenceRadioButton smartRadio() {
        PreferenceRadioButton smartRadio = new PreferenceRadioButton(BooleanUserPreference.SMART_OUTPUT,
                DefaultI18nContext.getInstance().i18n("Use the selected PDF document directory as output directory"),
                userContext.isUseSmartOutput(), userContext);
        smartRadio.setId("smartRadio");
        return smartRadio;
    }

    @Bean(name = "workingDirectory")
    public PreferenceBrowsableDirectoryField workingDirectory() {
        PreferenceBrowsableDirectoryField workingDirectory = new PreferenceBrowsableDirectoryField(
                StringUserPreference.WORKING_PATH, userContext);
        workingDirectory.getTextField().setText(userContext.getDefaultWorkingPath());
        workingDirectory.setId("workingDirectory");
        return workingDirectory;
    }

    @Bean(name = "workspace")
    public PreferenceBrowsableFileField workspace() {
        PreferenceBrowsableFileField workspace = new PreferenceBrowsableFileField(StringUserPreference.WORKSPACE_PATH,
                FileType.XML, OpenType.OPEN, userContext);
        workspace.getTextField().setText(userContext.getDefaultWorkspacePath());
        workspace.setId("workspace");
        return workspace;
    }

    @Bean(name = "thumbnailsSize")
    public PreferenceIntTextField thumbnailsSize() {
        PreferenceIntTextField thumbnails = new PreferenceIntTextField(IntUserPreference.THUMBNAILS_SIZE, userContext,
                Validators.newPositiveIntRangeString(THUMB_SIZE_LOWER, THUMB_SIZE_UPPER));
        thumbnails.setText(Integer.toString(userContext.getThumbnailsSize()));
        thumbnails.setErrorMessage(DefaultI18nContext.getInstance().i18n("Size must be between {0}px and {1}px",
                THUMB_SIZE_LOWER.toString(), THUMB_SIZE_UPPER.toString()));
        String helpText = DefaultI18nContext.getInstance().i18n(
                "Pixel size of the thumbnails (between {0}px and {1}px)", THUMB_SIZE_LOWER.toString(),
                THUMB_SIZE_UPPER.toString());
        thumbnails.setPromptText(helpText);
        thumbnails.setTooltip(new Tooltip(helpText));
        thumbnails.setId("thumbnailsSize");
        return thumbnails;
    }

}
