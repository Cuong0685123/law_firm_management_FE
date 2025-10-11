package com.mycompany.lawfirm.controller;

import com.mycompany.lawfirm.model.Contract;
import com.mycompany.lawfirm.service.ContractService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ContractController {

    @FXML private TableView<Contract> contractTable;
    @FXML private TableColumn<Contract, Long> colId;
    @FXML private TableColumn<Contract, String> colCode;
    @FXML private TableColumn<Contract, String> colContent;
    @FXML private TableColumn<Contract, String> colSignDate;
    @FXML private TableColumn<Contract, BigDecimal> colFee;
    @FXML private TableColumn<Contract, Long> colCaseId;

    private final ContractService contractService = new ContractService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleLongProperty(data.getValue().getId()).asObject());
        colCode.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCode()));
        colContent.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getContent()));
        colSignDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getSignDate() != null ? data.getValue().getSignDate().format(DateTimeFormatter.ISO_DATE) : ""));
        colFee.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getFee()));
        colCaseId.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getCaseId()));

        loadContracts();
    }

    private void loadContracts() {
        try {
            List<Contract> contracts = contractService.getAll();
            contractTable.setItems(FXCollections.observableArrayList(contracts));
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể tải danh sách hợp đồng: " + e.getMessage());
        }
    }

    @FXML
private void handleAdd() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/contract/ContractForm.fxml"));
        Parent root = loader.load();

        ContractFormController formController = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Thêm Hợp Đồng");
        stage.setScene(new Scene(root));
        stage.showAndWait();

        Contract result = formController.getResultContract();
        if (result != null) {
            contractService.create(result);
            loadContracts();
        }

    } catch (IOException e) {
        showAlert("Lỗi", "Không thể mở form: " + e.getMessage());
    }
}

@FXML
private void handleEdit() {
    Contract selected = contractTable.getSelectionModel().getSelectedItem();
    if (selected == null) {
        showAlert("Cảnh báo", "Vui lòng chọn hợp đồng để chỉnh sửa.");
        return;
    }

    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/contract/ContractForm.fxml"));
        Parent root = loader.load();

        ContractFormController formController = loader.getController();
        formController.setContractData(selected);

        Stage stage = new Stage();
        stage.setTitle("Chỉnh Sửa Hợp Đồng");
        stage.setScene(new Scene(root));
        stage.showAndWait();

        Contract result = formController.getResultContract();
        if (result != null) {
            contractService.update(selected.getId(), result);
            loadContracts();
        }

    } catch (IOException e) {
        showAlert("Lỗi", "Không thể mở form chỉnh sửa: " + e.getMessage());
    }
}


    @FXML
    private void handleDelete() {
        Contract selected = contractTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                contractService.delete(selected.getId());
                loadContracts();
                showAlert("Thành công", "Đã xóa hợp đồng!");
            } catch (IOException e) {
                showAlert("Lỗi", "Không thể xóa hợp đồng: " + e.getMessage());
            }
        } else {
            showAlert("Cảnh báo", "Vui lòng chọn hợp đồng để xóa.");
        }
    }

    @FXML
    private void handleRefresh() {
        loadContracts();
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/mainview/MainView.fxml"));
            Stage stage = (Stage) contractTable.getScene().getWindow();
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
