package com.mycompany.lawfirm.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Contract {
    private Long id;
    private String code;        // Mã hợp đồng
    private String content;     // Nội dung hợp đồng
    private LocalDate signDate; // Ngày ký
    private BigDecimal fee;     // Phí
    private Long caseId;        // Liên kết với Case (chỉ lưu ID)

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDate getSignDate() { return signDate; }
    public void setSignDate(LocalDate signDate) { this.signDate = signDate; }

    public BigDecimal getFee() { return fee; }
    public void setFee(BigDecimal fee) { this.fee = fee; }

    public Long getCaseId() { return caseId; }
    public void setCaseId(Long caseId) { this.caseId = caseId; }
}
