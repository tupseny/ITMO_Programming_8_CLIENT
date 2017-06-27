/*
package com.dartin.project.gui.controller;


import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.com.dartin.project.util.StringConverter;

import java.time.LocalDate;

public class NewItemController {
    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox usageChoice;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Slider sliderSize;

    private Stage dialogStage;
    private Item item;
    private boolean okClicked = false;

    public void initialize(){

        sliderSize.setMin(0);
        sliderSize.setMax(5);
        sliderSize.setValue(1);
        sliderSize.setShowTickMarks(true);
        sliderSize.setShowTickLabels(true);
        sliderSize.setMajorTickUnit(1);
        sliderSize.setMinorTickCount(0);
        sliderSize.setBlockIncrement(1);
        sliderSize.setSnapToTicks(true);
        sliderSize.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n < 0.5) return "tiny";
                if (n < 1.5) return "small";
                if (n < 2.5) return "normal";
                if (n < 3.5) return "large";
                if (n < 4.5) return "huge";

                return "undefined";
            }

            @Override
            public Double fromString(String s) {
                switch (s) {
                    case "tiny":
                        return 0d;
                    case "small":
                        return 1d;
                    case "normal":
                        return 2d;
                    case "large":
                        return 3d;
                    case "huge":
                        return 4d;
                    default:
                        return 5d;
                }
            }
        });

        usageChoice.getItems().addAll("u1", "u2", "u3", "u4", "undefined");
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public void setItem(Item item){
        this.item = item;

        nameField.setText(item.name());
        usageChoice.getSelectionModel().select(item.usage());
        sliderSize.setValue(sliderSize.getLabelFormatter().fromString(item.size().toString()));
        datePicker.setValue(LocalDate.now());
    }

    public boolean isOkClicked(){
        return okClicked;
    }
    public Item getNewItem(){
        return this.item;
    }

    @FXML
    private void handleOk(){
        if (isInputValid()){
            Item.Size size = Item.Size.UNDEFINED;

            item = new Item(nameField.getText(),
                    usageChoice.getSelectionModel().getSelectedItem().toString(),
                    Item.Size.valueOf(sliderSize.getLabelFormatter().toString(sliderSize.getValue()).toUpperCase()),
                    datePicker.getValue());


            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    public boolean isInputValid(){
        //TODO: add validation test
        return true;
    }
}
*/
package com.dartin.project.gui.controller;

import com.dartin.project.gui.NewItemModel;
import com.dartin.util.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class NewItemController {

    private static final Pattern PATTERN_WORD = Pattern.compile("[[:alpha:]]+");
    private NewItemModel model;
    private Stage stage;
    private boolean isOkClicked;

    @FXML
    private TextField textFieldName;
    @FXML
    private Slider sliderSize;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox choiceBoxUsage;
    @FXML
    private Button okBut;
    @FXML
    private Button cancelBut;

    @FXML
    private void handleButtonOk(ActionEvent actionEvent) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", textFieldName.getText());
        map.put("usage", choiceBoxUsage.getValue().toString());
        map.put("size", Item.Size.LARGE); //sliderSize.getLabelFormatter().toString(sliderSize.getValue())
        model.loadNewItemData(map);
        isOkClicked = true;
        stage.close();
    }

    @FXML
    private void handleButtonCancel(ActionEvent actionEvent) {
        isOkClicked = false;
        stage.close();
    }

    @FXML
    private void initialize() {
        ObservableList<String> list = FXCollections.observableArrayList(
                "Wear", "Throw", "Eat");


        textFieldName.setPromptText("NAME");
        datePicker.setValue(LocalDate.now());
        choiceBoxUsage.setItems(list);
        choiceBoxUsage.setValue(choiceBoxUsage.getItems().get(0).toString());
    }

    public void setModel(NewItemModel model) {
        this.model = model;
    }

    public void loadFields(Map<String, Object> map) {
        textFieldName.setText((String) map.get("name"));
        sliderSize.setMax(model.getMaxSliderValue());
        sliderSize.setLabelFormatter(model.getLabelFormatter());
        datePicker.setValue((LocalDate) map.get("date"));
        choiceBoxUsage.setValue((String) map.get("usage"));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isOkClicked() {
        return isOkClicked;
    }


}