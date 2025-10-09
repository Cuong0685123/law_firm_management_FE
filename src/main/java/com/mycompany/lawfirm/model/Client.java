package com.mycompany.lawfirm.model;

import java.time.LocalDate;

public class Client {
    private Long id;
    private String fullName;
    private LocalDate birthDate;
    private String idNumber;
    private String address;
    private String phone;
    private String email;

    // Getters và setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    @Override
public String toString() {
    return fullName != null ? fullName : "(Không có tên)";
}

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Client)) return false;
    Client other = (Client) o;
    return id != null && id.equals(other.id);
}

@Override
public int hashCode() {
    return id != null ? id.hashCode() : 0;
}

}
