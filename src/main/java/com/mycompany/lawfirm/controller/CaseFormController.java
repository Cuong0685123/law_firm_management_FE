package com.mycompany.lawfirm.controller;

import com.mycompany.lawfirm.model.Case;
import com.mycompany.lawfirm.service.CaseService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CaseFormController {

    @FXML private TextField txtCode;
    @FXML private TextField txtCategory;
    @FXML private TextField txtFee;
    @FXML private DatePicker dpStart;
    @FXML private DatePicker dpEnd;
    @FXML private TextArea txtRequestContent;

    private final CaseService caseService = new CaseService();
    private Case editingCase;
    private Case resultCase; // ✅ để trả về cho CaseController

    /** Nhận dữ liệu vụ án từ CaseController (nếu là chỉnh sửa) */
    public void setCaseData(Case caseData) {
        this.editingCase = caseData;

        if (caseData != null) {
            txtCode.setText(caseData.getCode());
            txtCategory.setText(caseData.getCategory());
            txtFee.setText(caseData.getFee() != null ? caseData.getFee().toString() : "");
            dpStart.setValue(caseData.getStartDate());
            dpEnd.setValue(caseData.getEndDate());
            txtRequestContent.setText(caseData.getRequestContent());
        }
    }

    /** ✅ Trả kết quả vụ án (sau khi lưu) */
    public Case getResult() {
        return resultCase;
    }

    /** 💾 Lưu dữ liệu */
    @FXML
    private void handleSave() {
        try {
            Case caseData = (editingCase != null) ? editingCase : new Case();

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

    /** ❌ Hủy và đóng form */
    @FXML
    private void handleCancel() {
        resultCase = null;
        closeForm();
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
