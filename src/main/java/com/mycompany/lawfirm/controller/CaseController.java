package com.mycompany.lawfirm.controller;

import com.mycompany.lawfirm.model.Case;
import com.mycompany.lawfirm.service.CaseService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class CaseController {

    @FXML private TableView<Case> caseTable;
    @FXML private TableColumn<Case, Long> colId;
    @FXML private TableColumn<Case, String> colCode;
    @FXML private TableColumn<Case, String> colCategory;
    @FXML private TableColumn<Case, String> colRequestContent;
    @FXML private TableColumn<Case, String> colLegalRelation;
    @FXML private TableColumn<Case, String> colObjective;
    @FXML private TableColumn<Case, String> colApplicableLaw;
    @FXML private TableColumn<Case, String> colResolvingAgency;
    @FXML private TableColumn<Case, String> colProduct;
    @FXML private TableColumn<Case, String> colResult;
    @FXML private TableColumn<Case, BigDecimal> colFee;
    @FXML private TableColumn<Case, String> colStart;
    @FXML private TableColumn<Case, String> colEnd;
    @FXML private TableColumn<Case, String> colClient;

    private final CaseService caseService = new CaseService();

    @FXML
    public void initialize() {
        setupColumns();
        loadCases();
    }

    /** Thiết lập mapping các cột với model */
    private void setupColumns() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleLongProperty(data.getValue().getId()).asObject());
        colCode.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCode()));
        colCategory.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCategory()));
        colRequestContent.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getRequestContent()));
        colLegalRelation.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLegalRelation()));
        colObjective.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getObjective()));
        colApplicableLaw.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getApplicableLaw()));
        colResolvingAgency.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getResolvingAgency()));
        colProduct.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getProduct()));
        colResult.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getResult()));
        colFee.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getFee()));
        colStart.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getStartDate() != null ? data.getValue().getStartDate().format(DateTimeFormatter.ISO_DATE) : ""
        ));
        colEnd.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getEndDate() != null ? data.getValue().getEndDate().format(DateTimeFormatter.ISO_DATE) : ""
        ));
        colClient.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getClient() != null ? data.getValue().getClient().getFullName() : ""
        ));
    }

    /** 🔄 Load danh sách vụ án */
    private void loadCases() {
        try {
            List<Case> cases = caseService.getAll();
            caseTable.setItems(FXCollections.observableArrayList(cases));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách vụ án:\n" + e.getMessage());
        }
    }

    /** ➕ Thêm vụ án mới */
    @FXML
    private void handleAdd() {
        Case newCase = showCaseForm(null);
        if (newCase != null) {
            try {
                caseService.create(newCase);
                loadCases();
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã thêm vụ án mới!");
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm vụ án:\n" + e.getMessage());
            }
        }
    }

    /** ✏️ Chỉnh sửa vụ án */
    @FXML
    private void handleEdit() {
        Case selected = caseTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Case updatedCase = showCaseForm(selected);
            if (updatedCase != null) {
                try {
                    caseService.update(selected.getId(), updatedCase);
                    loadCases();
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã cập nhật vụ án!");
                } catch (IOException e) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật vụ án:\n" + e.getMessage());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn vụ án để chỉnh sửa.");
        }
    }

    /** ❌ Xóa vụ án */
    @FXML
    private void handleDelete() {
        Case selected = caseTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Xác nhận");
            confirm.setHeaderText("Xóa vụ án " + selected.getCode() + "?");
            confirm.setContentText("Hành động này không thể hoàn tác.");
            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    caseService.delete(selected.getId());
                    loadCases();
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã xóa vụ án!");
                } catch (IOException e) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể xóa vụ án:\n" + e.getMessage());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn vụ án để xóa.");
        }
    }

    /** 🔁 Làm mới */
    @FXML
    private void handleRefresh() {
        loadCases();
    }

    /** ⬅️ Quay lại MainView */
    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/mainview/MainView.fxml"));
            Stage stage = (Stage) caseTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể quay lại màn hình chính:\n" + e.getMessage());
        }
    }

    /** ⚙️ Hiển thị form thêm/sửa vụ án */
    private Case showCaseForm(Case selectedCase) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CaseFormView.fxml"));
            Parent root = loader.load();

            CaseFormController controller = loader.getController();
            controller.setCaseData(selectedCase);

            Stage stage = new Stage();
            stage.setTitle(selectedCase == null ? "Thêm vụ án" : "Chỉnh sửa vụ án");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            return controller.getResult();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể mở form vụ án:\n" + e.getMessage());
            return null;
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
