package com.example.lab6;

import com.example.lab6.data.Apartment;
import com.example.lab6.data.Repository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class AddApartmentController {
    private Repository _repository;

    private MainController _mainController;

    public void set_mainController(MainController _mainController) {
        this._mainController = _mainController;
    }

    public void set_repository(Repository _repository) {
        this._repository = _repository;
    }

    @FXML
    TextField address;

    @FXML
    TextField numberOfRooms;

    @FXML
    TextField price;

    @FXML
    TextField rentDate;

    @FXML
    TextField termRent;

    @FXML
    CheckBox isRented;

    public void addApartmentToDb(ActionEvent actionEvent) {
        String address_ = address.getText();
        int numberOfRooms_ = Integer.parseInt(numberOfRooms.getText());
        int price_ = Integer.parseInt(price.getText());
        Boolean isRented_ = isRented.isSelected();
        String rentDate_ = rentDate.getText();
        String termRent_ = termRent.getText();
        if (!isRented_){
            rentDate_ = "";
            termRent_ = "";
        }

        Apartment newApartment = new Apartment(address_, numberOfRooms_, price_,isRented_ , rentDate_, termRent_ );
        _repository.addApartment(newApartment);

        _mainController.updateListsView();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
