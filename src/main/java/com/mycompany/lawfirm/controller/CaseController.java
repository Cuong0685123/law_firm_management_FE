package com.mycompany.lawfirm.controller;

import com.mycompany.lawfirm.model.Case;
import com.mycompany.lawfirm.service.CaseService;
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

public class CaseController {

    @FXML private TableView<Case> caseTable;
    @FXML private TableColumn<Case, Long> colId;
    @FXML private TableColumn<Case, String> colCode;
    @FXML private TableColumn<Case, String> colCategory;
    @FXML private TableColumn<Case, String> colStart;
    @FXML private TableColumn<Case, String> colEnd;
    @FXML private TableColumn<Case, BigDecimal> colFee;

    private final CaseService caseService = new CaseService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleLongProperty(data.getValue().getId()).asObject());
        colCode.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCode()));
        colCategory.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCategory()));
        colStart.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getStartDate() != null ? data.getValue().getStartDate().format(DateTimeFormatter.ISO_DATE) : ""));
        colEnd.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getEndDate() != null ? data.getValue().getEndDate().format(DateTimeFormatter.ISO_DATE) : ""));
        colFee.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getFee()));

        loadCases();
    }

    private void loadCases() {
        try {
            List<Case> cases = caseService.getAll();
            caseTable.setItems(FXCollections.observableArrayList(cases));
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể tải danh sách vụ án: " + e.getMessage());
        }
    }

    @FXML
    private void handleAdd() {
        showAlert("Thêm", "Mở dialog thêm vụ án...");
    }

    @FXML
    private void handleEdit() {
        Case selected = caseTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showAlert("Chỉnh sửa", "Mở dialog chỉnh sửa vụ án: " + selected.getCode());
        } else {
            showAlert("Cảnh báo", "Vui lòng chọn vụ án để chỉnh sửa.");
        }
    }

    @FXML
    private void handleDelete() {
        Case selected = caseTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                caseService.delete(selected.getId());
                loadCases();
                showAlert("Thành công", "Đã xóa vụ án!");
            } catch (IOException e) {
                showAlert("Lỗi", "Không thể xóa vụ án: " + e.getMessage());
            }
        } else {
            showAlert("Cảnh báo", "Vui lòng chọn vụ án để xóa.");
        }
    }

    @FXML
    private void handleRefresh() {
        loadCases();
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
            Stage stage = (Stage) caseTable.getScene().getWindow();
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
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
