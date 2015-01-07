/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 21/ott/2013
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
package org.pdfsam.ui.dashboard.about;

import static org.pdfsam.support.io.ObjectCollectionWriter.writeContent;

import java.util.Arrays;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.pdfsam.Pdfsam;
import org.pdfsam.i18n.DefaultI18nContext;
import org.pdfsam.ui.commons.UrlButton;
import org.pdfsam.ui.support.Style;

import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;

/**
 * Panel displaying PDFsam logo and About panel informations
 * 
 * @author Andrea Vacondio
 * 
 */
@Named
public class AboutDashboardPane extends HBox {

    @Inject
    public AboutDashboardPane(Pdfsam pdfsam) {
        getStyleClass().add("dashboard-container");
        VBox left = new VBox(5);
        addSectionTitle(pdfsam.name(), left);
        Label copyright = new Label("Copyright 2014 by Andrea Vacondio");
        AwesomeDude.setIcon(copyright, AwesomeIcon.COPYRIGHT);
        left.getChildren().addAll(new Label(String.format("ver. %s", pdfsam.version())), copyright);
        addHyperlink(null, "http://www.gnu.org/licenses/agpl-3.0.html", "GNU Affero General Public License v3", left);
        addHyperlink(AwesomeIcon.HOME, "http://www.pdfsam.org", "www.pdfsam.org", left);
        addHyperlink(AwesomeIcon.RSS_SQUARE, "http://www.pdfsam.org/feed/",
                DefaultI18nContext.getInstance().i18n("Subscribe to the official news feed"), left);

        addSectionTitle(DefaultI18nContext.getInstance().i18n("Environment"), left);
        Label runtime = new Label(String.format("%s %s", System.getProperty("java.runtime.name"),
                System.getProperty("java.runtime.version")));
        Label fxRuntime = new Label(String.format("JavaFX %s", com.sun.javafx.runtime.VersionInfo.getRuntimeVersion()));
        Label memory = new Label(DefaultI18nContext.getInstance().i18n("Max memory {0}",
                FileUtils.byteCountToDisplaySize(Runtime.getRuntime().maxMemory())));
        Button copyButton = new Button(DefaultI18nContext.getInstance().i18n("Copy to clipboard"));
        AwesomeDude.setIcon(copyButton, AwesomeIcon.COPY);
        copyButton.getStyleClass().addAll(Style.BUTTON.css());
        copyButton.setId("copyEnvDetails");
        copyButton.setOnAction(a -> {
            ClipboardContent content = new ClipboardContent();
            writeContent(
                    Arrays.asList(pdfsam.name(), pdfsam.version(), runtime.getText(), fxRuntime.getText(),
                            memory.getText())).to(content);
            Clipboard.getSystemClipboard().setContent(content);
        });
        left.getChildren().addAll(runtime, fxRuntime, memory, copyButton);

        addSectionTitle(DefaultI18nContext.getInstance().i18n("Thanks to"), left);
        addHyperlink(null, "http://www.pdfsam.org/thanks_to",
                DefaultI18nContext.getInstance().i18n("The open source projects making PDFsam possible"), left);
        VBox right = new VBox(5);
        addSectionTitle(DefaultI18nContext.getInstance().i18n("Support"), right);
        addHyperlink(AwesomeIcon.BUG, "http://www.pdfsam.org/issue_tracker",
                DefaultI18nContext.getInstance().i18n("Bug and feature requests"), right);
        addHyperlink(AwesomeIcon.QUESTION_CIRCLE, "http://www.pdfsam.org/wiki", "HowTo wiki", right);
        addHyperlink(AwesomeIcon.YOUTUBE_PLAY, "http://www.pdfsam.org/quickstart_video", DefaultI18nContext
                .getInstance().i18n("Play the \"get started\" video"), right);

        addSectionTitle(DefaultI18nContext.getInstance().i18n("Contribute"), right);
        addHyperlink(AwesomeIcon.GITHUB, "http://www.pdfsam.org/scm",
                DefaultI18nContext.getInstance().i18n("Fork PDFsam on GitHub"), right);
        addHyperlink(AwesomeIcon.FLAG_ALT, "http://www.pdfsam.org/translate",
                DefaultI18nContext.getInstance().i18n("Translate"), right);
        addHyperlink(AwesomeIcon.DOLLAR, "http://www.pdfsam.org/donate", DefaultI18nContext.getInstance()
                .i18n("Donate"), right);

        addSectionTitle(DefaultI18nContext.getInstance().i18n("Social"), right);
        addHyperlink(AwesomeIcon.TWITTER_SQUARE, "http://www.pdfsam.org/twitter", DefaultI18nContext.getInstance()
                .i18n("Follow us on Twitter"), right);
        addHyperlink(AwesomeIcon.GOOGLE_PLUS_SQUARE, "http://www.pdfsam.org/gplus", DefaultI18nContext.getInstance()
                .i18n("Follow us on Google Plus"), right);
        addHyperlink(AwesomeIcon.FACEBOOK_SQUARE, "http://www.pdfsam.org/facebook", DefaultI18nContext.getInstance()
                .i18n("Like us on Facebook"), right);
        getChildren().addAll(left, right);

    }

    private void addSectionTitle(String title, Pane pane) {
        Label label = new Label(title);
        label.getStyleClass().add("section-title");
        pane.getChildren().add(label);
    }

    private void addHyperlink(AwesomeIcon icon, String url, String text, Pane pane) {
        UrlButton button = new UrlButton(text, url);
        button.getStyleClass().setAll("pdfsam-hyperlink");
        if (icon != null) {
            AwesomeDude.setIcon(button, icon);
        }
        pane.getChildren().add(button);
    }
}
