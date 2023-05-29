package com.example.lab6;

import com.example.lab6.data.Apartment;
import com.example.lab6.data.DataBaseConnector;
import com.example.lab6.data.DataBaseRepository;
import com.example.lab6.data.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class MainController implements Initializable {
    private Repository repository;

    @FXML
    ListView output;

    @FXML
    ComboBox numberOfRooms;

    @FXML
    ComboBox apartmentCombo;

    @FXML
    TextField maxPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        repository = new DataBaseRepository(new DataBaseConnector("Apartments"));
        updateListsView();
        apartmentCombo.setOnAction(event->applyFilters());
        numberOfRooms.setOnAction(event->applyFilters());
        maxPrice.setOnKeyReleased(event -> applyFilters());

    }

    void updateListsView() {
        List<Apartment> apartments = repository.getAll();
        ObservableList<Apartment> apartmentList = FXCollections.observableArrayList(apartments);
        output.setItems(apartmentList);
        apartmentCombo.setPromptText("Виберіть статус квартири");
        ObservableList<String> options = FXCollections.observableArrayList(
                "усі", "орендовані", "не орендовані"
        );
        apartmentCombo.setItems(options);
        apartmentCombo.getSelectionModel().selectFirst();
        List<Integer> roomOptions = new ArrayList<>();
        roomOptions.addAll(
                apartments
                        .stream()
                        .map(apartment -> apartment.getNumberOfRooms())
                        .distinct()
                        .collect(Collectors.toList())
        );
        roomOptions.add(0);
        Collections.sort(roomOptions);
        ObservableList<Integer> roomOptionsList = FXCollections.observableArrayList(roomOptions);
        numberOfRooms.setItems(roomOptionsList);
        numberOfRooms.getSelectionModel().selectFirst();
    }

    private void applyFilters() {
        String selectedOption = (String) apartmentCombo.getSelectionModel().getSelectedItem();
        Integer selectedNumberOfRooms = (Integer) numberOfRooms.getSelectionModel().getSelectedItem();
        String maxPriceText = maxPrice.getText();

        List<Apartment> filteredApartments = repository.getAll()
                .stream()
                .filter(apartment -> {
                    boolean typeMatch = selectedOption.equals("усі") ||
                            (selectedOption.equals("орендовані") && apartment.isRented()) ||
                            (selectedOption.equals("не орендовані") && !apartment.isRented());

                    boolean roomsMatch = selectedNumberOfRooms == 0 || apartment.getNumberOfRooms() == selectedNumberOfRooms;

                    boolean priceMatch = maxPriceText.isEmpty() || apartment.getPrice() <= Double.parseDouble(maxPriceText);

                    return typeMatch && roomsMatch && priceMatch;
                })
                .collect(Collectors.toList());

        ObservableList<Apartment> apartmentsList = FXCollections.observableList(filteredApartments);
        output.setItems(apartmentsList);
    }

    @FXML
    private void AddApartmentClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-apartment.fxml"));
            Parent addApartmentPane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Додати помешкання");
            stage.setScene(new Scene(addApartmentPane));
            AddApartmentController addApartmentController = loader.getController();
            addApartmentController.set_repository(repository);
            addApartmentController.set_mainController(this);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteApartment(ActionEvent actionEvent) {
        Apartment selectedApartment = (Apartment) output.getSelectionModel().getSelectedItem();
        if (selectedApartment != null) {
            int apartmentId = selectedApartment.getId();
            repository.deleteApartment(apartmentId);
            updateListsView();
        }
    }
}
