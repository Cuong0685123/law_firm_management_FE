package com.mycompany.lawfirm.controller;

import com.mycompany.lawfirm.model.Case;
import com.mycompany.lawfirm.model.Client;
import com.mycompany.lawfirm.service.CaseService;
import com.mycompany.lawfirm.service.ClientService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class CaseFormController {

    @FXML private ComboBox<Client> cbClient;
    
    @FXML private TextField txtCode;
    @FXML private TextField txtCategory;
    @FXML private TextField txtFee;
    @FXML private DatePicker dpStart;
    @FXML private DatePicker dpEnd;
    @FXML private TextArea txtRequestContent;

    private final CaseService caseService = new CaseService();
    private final ClientService clientService = new ClientService();

    private Case editingCase;
    private Case resultCase;

    @FXML
    public void initialize() {
        loadClients();
    }

    /** 🧭 Load danh sách khách hàng vào combobox */
    private void loadClients() {
        try {
            List<Client> clients = clientService.getAll();
            cbClient.setItems(FXCollections.observableArrayList(clients));
            cbClient.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Client c, boolean empty) {
                    super.updateItem(c, empty);
                    setText(empty || c == null ? "" : c.getFullName());
                }
            });
            cbClient.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(Client c, boolean empty) {
                    super.updateItem(c, empty);
                    setText(empty || c == null ? "" : c.getFullName());
                }
            });
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể tải danh sách khách hàng:\n" + e.getMessage());
        }
    }

    /** Gán dữ liệu vụ án vào form (khi sửa) */
    public void setCaseData(Case caseData) {
        this.editingCase = caseData;
        if (caseData != null) {
            txtCode.setText(caseData.getCode());
            txtCategory.setText(caseData.getCategory());
            txtFee.setText(caseData.getFee() != null ? caseData.getFee().toString() : "");
            dpStart.setValue(caseData.getStartDate());
            dpEnd.setValue(caseData.getEndDate());
            txtRequestContent.setText(caseData.getRequestContent());
            cbClient.setValue(caseData.getClient());
        }
    }

    /** Lưu vụ án */
    @FXML
    private void handleSave() {
        try {
            if (cbClient.getValue() == null) {
                showAlert("Lỗi", "Vui lòng chọn khách hàng!");
                return;
            }

            Case caseData = (editingCase != null) ? editingCase : new Case();

            caseData.setClientId(cbClient.getValue().getId());

            
            caseData.setCode(txtCode.getText());
            caseData.setCategory(txtCategory.getText());
            caseData.setFee(new BigDecimal(txtFee.getText()));
            caseData.setStartDate(dpStart.getValue());
            caseData.setEndDate(dpEnd.getValue());
            caseData.setRequestContent(txtRequestContent.getText());

            if (editingCase == null) {
                resultCase = caseService.create(caseData);
         

                showAlert("Thành công", "Đã thêm vụ án mới!");
            } else {
                resultCase = caseService.update(caseData.getId(), caseData);
                showAlert("Thành công", "Đã cập nhật vụ án!");
            }

            closeForm();

        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Phí phải là số hợp lệ!");
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể lưu vụ án:\n" + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        resultCase = null;
        closeForm();
    }

    public Case getResult() {
        return resultCase;
    }

    private void closeForm() {
        Stage stage = (Stage) txtCode.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
