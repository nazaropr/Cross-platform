package com.example.lab6

import com.example.lab6.data.Apartment
import com.example.lab6.data.DataBaseConnector
import com.example.lab6.data.DataBaseRepository
import com.example.lab6.data.Repository
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.event.Event
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ComboBox
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.input.KeyEvent
import javafx.stage.Stage
import java.net.URL
import java.util.*
import java.util.function.Predicate
import java.util.stream.Collectors

class MainController : Initializable {
    private lateinit var repository: Repository

    @FXML
    lateinit var output: ListView<*>

    @FXML
    lateinit var numberOfRooms: ComboBox<Int>

    @FXML
    lateinit var apartmentCombo: ComboBox<String>

    @FXML
    lateinit var maxPrice: TextField

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        repository = DataBaseRepository(DataBaseConnector("Apartments"))
        updateListsView()
        apartmentCombo.onAction = EventHandler { event -> applyFilters() }
        numberOfRooms.onAction = EventHandler { event -> applyFilters() }
        maxPrice.onKeyReleased = EventHandler { event -> applyFilters() }
    }


    fun updateListsView() {
        val apartments: List<Apartment> = repository.getAll()
        val apartmentList: ObservableList<Apartment> = FXCollections.observableArrayList(apartments)
        output.items = apartmentList
        apartmentCombo.promptText = "Виберіть статус квартири"
        val options: ObservableList<String> = FXCollections.observableArrayList(
                "усі", "орендовані", "не орендовані"
        )
        apartmentCombo.items = options
        apartmentCombo.selectionModel.selectFirst()
        val roomOptions: MutableList<Int> = ArrayList()
        roomOptions.addAll(
                apartments
                        .stream()
                        .map { apartment -> apartment.numberOfRooms }
                        .distinct()
                        .collect(Collectors.toList())
        )
        roomOptions.add(0)
        roomOptions.sort()
        val roomOptionsList: ObservableList<Int> = FXCollections.observableArrayList(roomOptions)
        numberOfRooms.items = roomOptionsList
        numberOfRooms.selectionModel.selectFirst()
    }


    private fun applyFilters() {
        val selectedOption = apartmentCombo.selectionModel.selectedItem as String
        val selectedNumberOfRooms = numberOfRooms.selectionModel.selectedItem as Int
        val maxPriceText = maxPrice.text
        val filteredApartments = repository.getAll()
                .stream()
                .filter(Predicate<Apartment> { apartment: Apartment ->
                    val typeMatch = selectedOption == "усі" || selectedOption == "орендовані" && apartment.isRented || selectedOption == "не орендовані" && !apartment.isRented
                    val roomsMatch = selectedNumberOfRooms == 0 || apartment.numberOfRooms === selectedNumberOfRooms
                    val priceMatch = maxPriceText.isEmpty() || apartment.price <= maxPriceText.toDouble()
                    typeMatch && roomsMatch && priceMatch
                })
                .collect(Collectors.toList<Apartment>())
        val apartmentsList = FXCollections.observableList(filteredApartments)
        output.items = apartmentsList
    }

    @FXML
    private fun AddApartmentClick(event: ActionEvent) {
        try {
            val loader = FXMLLoader(javaClass.getResource("add-apartment.fxml"))
            val addApartmentPane = loader.load<Parent>()
            val stage = Stage()
            stage.title = "Додати помешкання"
            stage.scene = Scene(addApartmentPane)
            val addApartmentController = loader.getController<AddApartmentController>()
            addApartmentController.set_repository(repository)
            addApartmentController.set_mainController(this)
            stage.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteApartment(actionEvent: ActionEvent?) {
        val selectedApartment = output.selectionModel.selectedItem as Apartment
        if (selectedApartment != null) {
            val apartmentId = selectedApartment.id
            repository.deleteApartment(apartmentId)
            updateListsView()
        }
    }
}
