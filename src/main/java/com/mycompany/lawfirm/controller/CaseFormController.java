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
    @FXML private TextArea txtRequestContent;
    @FXML private TextField txtLegalRelation;
    @FXML private TextField txtObjective;
    @FXML private TextField txtApplicableLaw;
    @FXML private TextField txtResolvingAgency;
    @FXML private TextField txtProduct;
    @FXML private TextField txtResult;
    @FXML private TextField txtFee;
    @FXML private DatePicker dpStart;
    @FXML private DatePicker dpEnd;

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
        txtRequestContent.setText(caseData.getRequestContent());
        txtLegalRelation.setText(caseData.getLegalRelation());
        txtObjective.setText(caseData.getObjective());
        txtApplicableLaw.setText(caseData.getApplicableLaw());
        txtResolvingAgency.setText(caseData.getResolvingAgency());
        txtProduct.setText(caseData.getProduct());
        txtResult.setText(caseData.getResult());
        txtFee.setText(caseData.getFee() != null ? caseData.getFee().toString() : "");
        dpStart.setValue(caseData.getStartDate());
        dpEnd.setValue(caseData.getEndDate());

        // ✅ Trì hoãn việc setValue đến sau khi ComboBox load xong
        javafx.application.Platform.runLater(() -> {
            if (caseData.getClient() != null) {
                cbClient.setValue(caseData.getClient());
            } else if (caseData.getClientId() != null) {
                Client matched = cbClient.getItems()
                        .stream()
                        .filter(c -> c.getId().equals(caseData.getClientId()))
                        .findFirst()
                        .orElse(null);
                cbClient.setValue(matched);
               

            }
        });
    }
}



    /** ✅ Lưu dữ liệu */
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
            caseData.setRequestContent(txtRequestContent.getText());
            caseData.setLegalRelation(txtLegalRelation.getText());
            caseData.setObjective(txtObjective.getText());
            caseData.setApplicableLaw(txtApplicableLaw.getText());
            caseData.setResolvingAgency(txtResolvingAgency.getText());
            caseData.setProduct(txtProduct.getText());
            caseData.setResult(txtResult.getText());
            caseData.setFee(new BigDecimal(txtFee.getText()));
            caseData.setStartDate(dpStart.getValue());
            caseData.setEndDate(dpEnd.getValue());
            caseData.setClient(cbClient.getValue());
caseData.setClientId(cbClient.getValue().getId());


            // Gọi service để lưu (nếu bạn muốn lưu ngay)
            
            resultCase = caseData;
            closeForm();

        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Phí phải là số hợp lệ!");
        } catch (Exception e) {
            showAlert("Lỗi", "Không thể lưu dữ liệu:\n" + e.getMessage());
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
