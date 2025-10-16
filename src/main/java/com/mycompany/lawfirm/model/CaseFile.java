package com.mycompany.lawfirm.model;

import java.time.LocalDateTime;

public class CaseFile {
    private Long id;
    private String fileName;
    private String fileType;
    private String filePath;
    private LocalDateTime uploadDate;
    private Long caseId; // ánh xạ tới vụ án

    // Constructor mặc định
    public CaseFile() {
        // Nếu không được gán uploadDate thì tự động lấy thời gian hiện tại
        this.uploadDate = LocalDateTime.now();
    }

    // Constructor có tham số (nếu cần)
    public CaseFile(String fileName, String fileType, String filePath, Long caseId) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.caseId = caseId;
        this.uploadDate = LocalDateTime.now(); // luôn có giá trị mặc định
    }

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public LocalDateTime getUploadDate() { return uploadDate; }
    public void setUploadDate(LocalDateTime uploadDate) { 
        // Nếu truyền vào null thì vẫn gán thời gian hiện tại
        this.uploadDate = (uploadDate != null) ? uploadDate : LocalDateTime.now();
    }

    public Long getCaseId() { return caseId; }
    public void setCaseId(Long caseId) { this.caseId = caseId; }

    @Override
    public String toString() {
        // Hiển thị ngày hợp lệ, tránh lỗi khi null
        String dateStr = (uploadDate != null) ? uploadDate.toLocalDate().toString() : "Chưa xác định";
        return fileName + " (" + dateStr + ")";
    }
}
