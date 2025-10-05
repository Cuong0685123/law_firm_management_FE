package com.mycompany.lawfirm.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Staff {
    private Long id;

    // ====== Thông tin đăng nhập ======
    private String username;
    private String password;
    private boolean enabled;
    private boolean accountNonLocked;

    // ====== Thông tin cá nhân ======
    private Integer orderNumber;
    private String fullName;
    private LocalDate birthDate;

    private String idNumber;
    private LocalDate idIssueDate;
    private String idIssuePlace;

    private String socialInsuranceBook;
    private String healthInsurance;
    private String laborBook;

    // ====== Chức vụ & nghề nghiệp ======
    private String role;
    private String position;
    private String otherPositions;

    private LocalDate joinDate;
    private LocalDate apprenticeshipStart;
    private LocalDate apprenticeshipEnd;
    private LocalDate lawyerStartDate;

    // ====== Thu nhập ======
    private BigDecimal monthlyFee;
    private BigDecimal benefitLevel;
    private String incomeTable;

    // ====== Getter & Setter ======
    // (Em viết đủ để tránh lỗi Jackson)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public boolean isAccountNonLocked() { return accountNonLocked; }
    public void setAccountNonLocked(boolean accountNonLocked) { this.accountNonLocked = accountNonLocked; }

    public Integer getOrderNumber() { return orderNumber; }
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    public LocalDate getIdIssueDate() { return idIssueDate; }
    public void setIdIssueDate(LocalDate idIssueDate) { this.idIssueDate = idIssueDate; }

    public String getIdIssuePlace() { return idIssuePlace; }
    public void setIdIssuePlace(String idIssuePlace) { this.idIssuePlace = idIssuePlace; }

    public String getSocialInsuranceBook() { return socialInsuranceBook; }
    public void setSocialInsuranceBook(String socialInsuranceBook) { this.socialInsuranceBook = socialInsuranceBook; }

    public String getHealthInsurance() { return healthInsurance; }
    public void setHealthInsurance(String healthInsurance) { this.healthInsurance = healthInsurance; }

    public String getLaborBook() { return laborBook; }
    public void setLaborBook(String laborBook) { this.laborBook = laborBook; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getOtherPositions() { return otherPositions; }
    public void setOtherPositions(String otherPositions) { this.otherPositions = otherPositions; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    public LocalDate getApprenticeshipStart() { return apprenticeshipStart; }
    public void setApprenticeshipStart(LocalDate apprenticeshipStart) { this.apprenticeshipStart = apprenticeshipStart; }

    public LocalDate getApprenticeshipEnd() { return apprenticeshipEnd; }
    public void setApprenticeshipEnd(LocalDate apprenticeshipEnd) { this.apprenticeshipEnd = apprenticeshipEnd; }

    public LocalDate getLawyerStartDate() { return lawyerStartDate; }
    public void setLawyerStartDate(LocalDate lawyerStartDate) { this.lawyerStartDate = lawyerStartDate; }

    public BigDecimal getMonthlyFee() { return monthlyFee; }
    public void setMonthlyFee(BigDecimal monthlyFee) { this.monthlyFee = monthlyFee; }

    public BigDecimal getBenefitLevel() { return benefitLevel; }
    public void setBenefitLevel(BigDecimal benefitLevel) { this.benefitLevel = benefitLevel; }

    public String getIncomeTable() { return incomeTable; }
    public void setIncomeTable(String incomeTable) { this.incomeTable = incomeTable; }
}
