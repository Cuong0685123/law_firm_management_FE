package com.mycompany.lawfirm.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Case {
    private Long id;
    private String code;                // Mã vụ án
    private String category;            // Loại vụ án
    
    private String requestContent;      // Nội dung yêu cầu
    private String legalRelation;       // Quan hệ pháp luật
    private String objective;           // Mục tiêu
    private String applicableLaw;       // Luật áp dụng

    private String resolvingAgency;     // Cơ quan giải quyết
    private String product;             // Sản phẩm/dịch vụ
    private String result;              // Kết quả
    private BigDecimal fee;             // Phí

    private LocalDate startDate;
    private LocalDate endDate;

    private Client client;              // Khách hàng (liên kết vụ án)
private Long clientId;

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getClientId(){return clientId; }
    public void setClientId (Long clientId){this.clientId = clientId;}

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getRequestContent() { return requestContent; }
    public void setRequestContent(String requestContent) { this.requestContent = requestContent; }

    public String getLegalRelation() { return legalRelation; }
    public void setLegalRelation(String legalRelation) { this.legalRelation = legalRelation; }

    public String getObjective() { return objective; }
    public void setObjective(String objective) { this.objective = objective; }

    public String getApplicableLaw() { return applicableLaw; }
    public void setApplicableLaw(String applicableLaw) { this.applicableLaw = applicableLaw; }

    public String getResolvingAgency() { return resolvingAgency; }
    public void setResolvingAgency(String resolvingAgency) { this.resolvingAgency = resolvingAgency; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public BigDecimal getFee() { return fee; }
    public void setFee(BigDecimal fee) { this.fee = fee; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
}

