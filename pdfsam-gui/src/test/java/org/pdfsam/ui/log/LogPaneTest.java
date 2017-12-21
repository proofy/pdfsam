/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 22/ago/2014
 * Copyright 2017 by Sober Lemur S.a.s. di Vacondio Andrea (info@pdfsam.org).
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
package org.pdfsam.ui.log;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.categories.TestFX;
import org.loadui.testfx.utils.FXTestUtils;
import org.pdfsam.context.UserContext;
import org.pdfsam.test.ClearEventStudioRule;
import org.sejda.injector.Injector;
import org.sejda.injector.Provides;

import javafx.scene.Parent;
import javafx.scene.input.Clipboard;

/**
 * @author Andrea Vacondio
 *
 */
@Category(TestFX.class)
public class LogPaneTest extends GuiTest {

    @ClassRule
    public static ClearEventStudioRule clearStudio = new ClearEventStudioRule();
    private Injector injector;

    static class Config {
        @Provides
        UserContext context() {
            UserContext userContext = mock(UserContext.class);
            when(userContext.getNumberOfLogRows()).thenReturn(200);
            return userContext;
        }

        @Provides
        public LogPane pane(LogListView view) {
            return new LogPane(view);
        }

        @Provides
        public LogListView view() {
            LogListView view = new LogListView(context());
            view.onEvent(new LogMessage("A message", LogLevel.INFO));
            view.onEvent(new LogMessage("An Error message", LogLevel.ERROR));
            return view;
        }
    }

    @Override
    protected Parent getRootNode() {
        injector = Injector.start(new Config());
        return injector.instance(LogPane.class);
    }

    @Test
    public void clear() {
        LogListView view = injector.instance(LogListView.class);
        assertEquals(2, view.getItems().size());
        rightClick("A message").click("#clearLogMenuItem");
        assertEquals(0, view.getItems().size());
    }

    @Test
    public void copy() throws Exception {
        FXTestUtils.invokeAndWait(() -> {
            Clipboard.getSystemClipboard().clear();
            assertTrue(isBlank(Clipboard.getSystemClipboard().getString()));
        }, 2);
        click("A message").rightClick("A message").click("#copyLogMenuItem");
        FXTestUtils.invokeAndWait(() -> assertTrue(Clipboard.getSystemClipboard().getString().contains("A message")),
                1);
    }

    @Test
    public void selectAll() {
        LogListView view = injector.instance(LogListView.class);
        rightClick("A message").click("#selectAllLogMenuItem");
        assertEquals(2, view.getSelectionModel().getSelectedItems().size());
    }

}
