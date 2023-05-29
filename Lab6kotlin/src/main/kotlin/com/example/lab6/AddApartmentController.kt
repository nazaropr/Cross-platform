package com.example.lab6

import com.example.lab6.data.Apartment
import com.example.lab6.data.Repository
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.CheckBox
import javafx.scene.control.TextField
import javafx.stage.Stage

class AddApartmentController {
    private var _repository: Repository? = null

    private var _mainController: MainController? = null

    fun set_mainController(_mainController: MainController?) {
        this._mainController = _mainController
    }

    fun set_repository(_repository: Repository?) {
        this._repository = _repository
    }

    @FXML
    var address: TextField? = null

    @FXML
    var numberOfRooms: TextField? = null

    @FXML
    var price: TextField? = null

    @FXML
    var rentDate: TextField? = null

    @FXML
    var termRent: TextField? = null

    @FXML
    var isRented: CheckBox? = null

    fun addApartmentToDb(actionEvent: ActionEvent) {
        val address_ = address!!.text
        val numberOfRooms_ = numberOfRooms!!.text.toInt()
        val price_ = price!!.text.toInt()
        val isRented_ = isRented!!.isSelected
        var rentDate_ = rentDate!!.text
        var termRent_ = termRent!!.text
        if (!isRented_) {
            rentDate_ = ""
            termRent_ = ""
        }
        val newApartment = Apartment(address_, numberOfRooms_, price_, isRented_, rentDate_, termRent_)
        _repository!!.addApartment(newApartment)
        _mainController?.updateListsView()
        val stage = (actionEvent.source as Node).scene.window as Stage
        stage.close()
    }
}
