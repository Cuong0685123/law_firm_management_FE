package com.mycompany.lawfirm.controller;

import com.mycompany.lawfirm.model.Contract;
import com.mycompany.lawfirm.model.Case;
import com.mycompany.lawfirm.service.ContractService;
import com.mycompany.lawfirm.service.CaseService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ContractFormController {

    @FXML private TextField txtCode;
    @FXML private TextArea txtContent;
    @FXML private DatePicker dpSignDate;
    @FXML private TextField txtFee;
    @FXML private ComboBox<Case> cbCase;

    private final ContractService contractService = new ContractService();
    private final CaseService caseService = new CaseService();

    private Contract editingContract;
    private Contract resultContract;

    @FXML
    public void initialize() {
        loadCases();
    }

    private void loadCases() {
        try {
            List<Case> cases = caseService.getAll();
            cbCase.setItems(FXCollections.observableArrayList(cases));
            cbCase.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Case c, boolean empty) {
                    super.updateItem(c, empty);
                    setText(empty || c == null ? "" : c.getCode());
                }
            });
            cbCase.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(Case c, boolean empty) {
                    super.updateItem(c, empty);
                    setText(empty || c == null ? "" : c.getCode());
                }
            });
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể tải danh sách vụ án:\n" + e.getMessage());
        }
    }

    public void setContractData(Contract contract) {
        this.editingContract = contract;
        if (contract != null) {
            txtCode.setText(contract.getCode());
            txtContent.setText(contract.getContent());
            dpSignDate.setValue(contract.getSignDate());
            txtFee.setText(contract.getFee() != null ? contract.getFee().toString() : "");

            // Gán vụ án được chọn
            if (contract.getCaseId() != null) {
                cbCase.getItems().stream()
                        .filter(c -> c.getId().equals(contract.getCaseId()))
                        .findFirst()
                        .ifPresent(cbCase::setValue);
            }
        }
    }

    @FXML
    private void handleSave() {
        try {
            if (txtCode.getText().isEmpty() || cbCase.getValue() == null) {
                showAlert("Lỗi", "Vui lòng nhập đủ thông tin!");
                return;
            }

            Contract contract = (editingContract != null) ? editingContract : new Contract();
            contract.setCode(txtCode.getText());
            contract.setContent(txtContent.getText());
            contract.setSignDate(dpSignDate.getValue() != null ? dpSignDate.getValue() : LocalDate.now());
            contract.setFee(new BigDecimal(txtFee.getText()));
            contract.setCaseId(cbCase.getValue().getId());

            resultContract = contract;
            closeForm();
        } catch (Exception e) {
            showAlert("Lỗi", "Không thể lưu: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        resultContract = null;
        closeForm();
    }

    public Contract getResultContract() {
        return resultContract;
    }

    private void closeForm() {
        Stage stage = (Stage) txtCode.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
