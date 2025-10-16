package com.mycompany.lawfirm.controller;

import com.mycompany.lawfirm.model.CaseFile;
import com.mycompany.lawfirm.service.CaseFileService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CaseFileController {

    @FXML private TableView<CaseFile> fileTable;
    @FXML private TableColumn<CaseFile, String> colName;
    @FXML private TableColumn<CaseFile, String> colType;
    @FXML private TableColumn<CaseFile, String> colDate;

    private final CaseFileService fileService = new CaseFileService();
    private Long caseId;

    public void setCaseId(Long id) {
        this.caseId = id;
        loadFiles();
    }

    @FXML
    private void initialize() {
        colName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFileName()));
        colType.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFileType()));
        colDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getUploadDate().toString()));
    }

    private void loadFiles() {
        try {
            List<CaseFile> files = fileService.getFilesByCase(caseId);
            fileTable.setItems(FXCollections.observableArrayList(files));
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể tải danh sách file: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpload() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Tài liệu", "*.pdf", "*.doc", "*.docx")
        );
        File selectedFile = chooser.showOpenDialog(fileTable.getScene().getWindow());
        if (selectedFile != null) {
            try {
                fileService.uploadFile(caseId, selectedFile);
                loadFiles();
                showAlert("Thành công", "Đã tải lên file!");
            } catch (IOException e) {
                showAlert("Lỗi", "Không thể tải lên: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleDownload() {
        CaseFile selected = fileTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Cảnh báo", "Vui lòng chọn file để tải xuống!");
            return;
        }

        try {
            FileChooser chooser = new FileChooser();
            chooser.setInitialFileName(selected.getFileName());
            File saveFile = chooser.showSaveDialog(fileTable.getScene().getWindow());
            if (saveFile != null) {
                fileService.downloadFile(selected.getId(), saveFile);
                showAlert("Thành công", "Đã tải file về!");
            }
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể tải file: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        CaseFile selected = fileTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Cảnh báo", "Vui lòng chọn file để xóa!");
            return;
        }

        try {
            fileService.deleteFile(selected.getId());
            loadFiles();
            showAlert("Thành công", "Đã xóa file!");
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể xóa file: " + e.getMessage());
        }
    }

    @FXML
    private void handleRefresh() {
        loadFiles();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
