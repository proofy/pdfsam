/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 25/lug/2014
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
package org.pdfsam.ui.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.sejda.eventstudio.StaticStudio.eventStudio;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.pdfsam.module.TaskExecutionRequestEvent;
import org.pdfsam.test.ClearEventStudioRule;
import org.pdfsam.test.InitializeJavaFxThreadRule;
import org.pdfsam.test.JavaFXThreadRule;
import org.sejda.model.notification.event.TaskExecutionCompletedEvent;
import org.sejda.model.notification.event.TaskExecutionFailedEvent;
import org.sejda.model.parameter.base.TaskParameters;
import org.sejda.model.task.NotifiableTaskMetadata;

/**
 * @author Andrea Vacondio
 *
 */
public class RunButtonTest {

    @Rule
    public JavaFXThreadRule fxThread = new InitializeJavaFxThreadRule();
    @Rule
    public ClearEventStudioRule cearEventStudio = new ClearEventStudioRule();
    private RunButton victim;

    @Before
    public void setUp() {
        victim = new RunButton();
    }

    @Test
    public void disableOnRequest() {
        victim.setDisable(false);
        TaskParameters parameters = mock(TaskParameters.class);
        eventStudio().broadcast(new TaskExecutionRequestEvent("id", parameters));
        assertTrue(victim.isDisabled());
    }

    @Test
    public void disableOnFail() {
        victim.setDisable(true);
        NotifiableTaskMetadata taskMetadata = mock(NotifiableTaskMetadata.class);
        eventStudio().broadcast(new TaskExecutionFailedEvent(null, taskMetadata));
        assertFalse(victim.isDisabled());
    }

    @Test
    public void enableOnComplete() {
        victim.setDisable(true);
        NotifiableTaskMetadata taskMetadata = mock(NotifiableTaskMetadata.class);
        eventStudio().broadcast(new TaskExecutionCompletedEvent(1, taskMetadata));
        assertFalse(victim.isDisabled());
    }
}
