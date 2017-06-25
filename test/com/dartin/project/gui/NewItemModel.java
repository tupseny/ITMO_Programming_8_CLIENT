package com.dartin.project.gui;

import com.dartin.project.AppLauncher;
import com.dartin.project.exception.NotValidSizeException;
import com.dartin.util.Item;
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Martin on 21.06.2017.
 */
public class NewItemModel {
    private AppLauncher app;
    private Item item;

    public NewItemModel(AppLauncher app) {
        this.app = app;
    }

    private boolean validateData() {
        return true;
    }

    public void loadNewItemData(Map<String, Object> map) {
        if (validateData()) {
            setItemData(app.convertMap(map));
        }
    }

    public void setItemData(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public StringConverter<Double> getLabelFormatter() {
        return new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                try {
                    return convertDoubleToSize(object);
                } catch (NotValidSizeException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Double fromString(String string) {
                try {
                    return convertSizeToDouble(string);
                } catch (NotValidSizeException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    public String convertDoubleToSize(double value) throws NotValidSizeException {
        List<Item.Size> list = Arrays.asList(Item.Size.values());
        value -= 1;
        for (Item.Size size : list) {
            if (list.indexOf(size) == (int) value) return size.toString();
        }
        throw new NotValidSizeException("Cant find size with index " + (int) value);
    }

    public double convertSizeToDouble(String value) throws NotValidSizeException {
        List<Item.Size> list = Arrays.asList(Item.Size.values());

        for (Item.Size size : list) {
            if (size.toString().equals(value)) {
                return list.indexOf(size);
            }
        }
        throw new NotValidSizeException("Not valid size");
    }

    public double getMaxSliderValue() {
        return Item.Size.values().length;
    }
}
