package com.mycompany.lawfirm.controller;

import com.mycompany.lawfirm.model.Client;
import com.mycompany.lawfirm.service.ClientService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClientController {

    @FXML private TableView<Client> clientTable;
    @FXML private TableColumn<Client, Long> colId;
    @FXML private TableColumn<Client, String> colFullName;
    @FXML private TableColumn<Client, String> colBirthDate;
    @FXML private TableColumn<Client, String> colIdNumber;
    @FXML private TableColumn<Client, String> colAddress;
    @FXML private TableColumn<Client, String> colPhone;
    @FXML private TableColumn<Client, String> colEmail;

    private final ClientService clientService = new ClientService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleLongProperty(data.getValue().getId()).asObject());
        colFullName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFullName()));
        colBirthDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getBirthDate() != null ? data.getValue().getBirthDate().format(DateTimeFormatter.ISO_DATE) : ""));
        colIdNumber.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdNumber()));
        colAddress.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAddress()));
        colPhone.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPhone()));
        colEmail.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));

        loadClients();
    }

    private void loadClients() {
        try {
            List<Client> clients = clientService.getAll();
            clientTable.setItems(FXCollections.observableArrayList(clients));
        } catch (IOException e) {
            showAlert("Error", "Không thể tải danh sách khách hàng: " + e.getMessage());
        }
    }

    @FXML
    private void handleAdd() {
        showAlert("Thêm", "Open Add Client dialog...");
    }

    @FXML
    private void handleEdit() {
        Client selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showAlert("Sửa", "Open Edit Client dialog for: " + selected.getFullName());
        } else {
            showAlert("Warning", "Vui lòng chọn khách hàng để sửa.");
        }
    }

    @FXML
    private void handleDelete() {
        Client selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                clientService.delete(selected.getId());
                loadClients();
                showAlert("Success", "Xóa khách hàng thành công!");
            } catch (IOException e) {
                showAlert("Error", "Xóa thất bại: " + e.getMessage());
            }
        } else {
            showAlert("Warning", "Vui lòng chọn khách hàng để xóa.");
        }
    }

    @FXML
    private void handleRefresh() {
        loadClients();
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
            Stage stage = (Stage) clientTable.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể quay lại màn hình chính: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
