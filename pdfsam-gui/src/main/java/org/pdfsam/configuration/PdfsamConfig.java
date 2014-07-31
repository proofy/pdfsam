/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 13/dic/2011
 * Copyright 2011 by Andrea Vacondio (andrea.vacondio@gmail.com).
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
package org.pdfsam.configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.inject.Inject;

import org.pdfsam.context.DefaultI18nContext;
import org.pdfsam.context.DefaultUserContext;
import org.pdfsam.context.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import de.jensd.fx.fontawesome.AwesomeStyle;

/**
 * IoC configuration
 * 
 * @author Andrea Vacondio
 * 
 */
@Configuration
@PropertySource("classpath:pdfsam.properties")
public class PdfsamConfig {
    private static final Logger LOG = LoggerFactory.getLogger(PdfsamConfig.class);
    @Inject
    private Environment env;

    @Bean
    public ImageView payoff() throws IOException {
        return new ImageView(new ClassPathResource("/images/payoff.png").getURL().toExternalForm());
    }

    @Bean(name = "appVersion")
    public String version() {
        return env.getProperty("pdfsam.version");
    }

    @Bean
    public UserContext userContext() {
        return new DefaultUserContext();
    }

    @Bean(name = "styles")
    public List<String> styles() {
        List<String> styles = new ArrayList<>();
        styles.add(this.getClass().getResource(AwesomeStyle.LIGHT.getStylePath()).toExternalForm());
        styles.add(this.getClass().getResource("/css/defaults.css").toExternalForm());
        styles.add(this.getClass().getResource("/css/pdfsam.css").toExternalForm());
        styles.add(this.getClass().getResource("/css/menu.css").toExternalForm());
        try {
            URL themeUrl = new ClassPathResource("/css/themes/" + userContext().getTheme()).getURL();
            styles.add(themeUrl.toExternalForm());
        } catch (IOException ioe) {
            LOG.warn("Unable to find selected theme.", ioe);
        }
        return styles;
    }

    @Bean(name = "errorPlayer")
    public MediaPlayer error() throws URISyntaxException {
        return mediaPlayer("/sounds/error_sound.wav");
    }

    @Bean(name = "okPlayer")
    public MediaPlayer ok() throws URISyntaxException {
        return mediaPlayer("/sounds/ok_sound.wav");
    }

    private MediaPlayer mediaPlayer(String name) throws URISyntaxException {
        MediaPlayer player = new MediaPlayer(new Media(this.getClass().getResource(name).toURI().toString()));
        player.setAutoPlay(false);
        player.setVolume(1);
        player.setOnError(() -> {
            LOG.error(DefaultI18nContext.getInstance().i18n("Error playing sounds"), player.getError());
        });
        return player;
    }

}
