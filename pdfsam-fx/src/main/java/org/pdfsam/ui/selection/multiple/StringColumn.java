/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 27/nov/2013
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
package org.pdfsam.ui.selection.multiple;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.defaultString;

import java.util.Comparator;

import org.pdfsam.i18n.DefaultI18nContext;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Definition of the {@link String} columns of the selection table
 * 
 * @author Andrea Vacondio
 * 
 */
public enum StringColumn implements SelectionTableColumn<String> {
    PAGE_SELECTION {
        public String getColumnTitle() {
            return DefaultI18nContext.getInstance().i18n("Page ranges");
        }

        @Override
        public ObservableValue<String> getObservableValue(SelectionTableRowData data) {
            return data.pageSelectionProperty();
        }

        @Override
        public String getTextValue(String item) {
            return defaultString(item, EMPTY);
        }

        public Comparator<String> comparator() {
            return Comparator.naturalOrder();
        }

        @Override
        public TableColumn<SelectionTableRowData, String> getTableColumn() {
            TableColumn<SelectionTableRowData, String> tableColumn = super.getTableColumn();
            tableColumn.setEditable(true);
            tableColumn.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow())
                    .setPageSelection(t.getNewValue()));
            return tableColumn;
        }

        public Callback<TableColumn<SelectionTableRowData, String>, TableCell<SelectionTableRowData, String>> cellFactory() {
            return new Callback<TableColumn<SelectionTableRowData, String>, TableCell<SelectionTableRowData, String>>() {
                public TableCell<SelectionTableRowData, String> call(TableColumn<SelectionTableRowData, String> param) {
                    return new TooltippedTextFieldTableCell();
                }
            };
        }
    }
}
