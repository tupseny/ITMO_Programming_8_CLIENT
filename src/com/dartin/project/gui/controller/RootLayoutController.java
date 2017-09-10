package com.dartin.project.gui.controller;

import com.dartin.project.client.UserPreferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.util.EventObject;

/**
 * @author root
 *         on 08/09/17.
 */
public class RootLayoutController {

    @FXML public void switchLanguage_en_IN(ActionEvent actionEvent) {
        UserPreferences.switchLanguage("en_IN");
    }

    @FXML public void switchLanguage_et(ActionEvent actionEvent) {
        UserPreferences.switchLanguage("et");
    }

    @FXML public void switchLanguage_sq(ActionEvent actionEvent) {
        UserPreferences.switchLanguage("sq");
    }

    @FXML public void switchLanguage_ru(ActionEvent actionEvent) {
        UserPreferences.switchLanguage("ru");
    }
}
