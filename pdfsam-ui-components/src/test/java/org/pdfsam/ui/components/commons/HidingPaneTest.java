/*
 * This file is part of the PDF Split And Merge source code
 * Created on 11/lug/2014
 * Copyright 2017 by Sober Lemur S.r.l. (info@soberlemur.com).
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
package org.pdfsam.ui.commons;

import org.junit.Ignore;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * @author Andrea Vacondio
 */
@ExtendWith(ApplicationExtension.class)
public class HidingPaneTest {

    private HidingPane victim;

    @Start
    private void start(Stage stage) {
        victim = new HidingPane();
        Scene scene = new Scene(victim);
        stage.setScene(scene);
        stage.show();
    }

    /* @Todo solve i18n problem*/
    @Ignore
    @Test
    public void hide(FxRobot robot) {
        verifyThat(victim, isVisible());
        robot.clickOn(".btn");
        verifyThat(victim, isInvisible());
    }

}
