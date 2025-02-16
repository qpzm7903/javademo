package com.qpzm7903.day2.model;

public class UserDTO {
    private Long id;
    private String displayName;
    private String emailMasked;

    public UserDTO(Long id, String displayName, String emailMasked) {
        this.id = id;
        this.displayName = displayName;
        this.emailMasked = emailMasked;
    }

    // getters
    public Long getId() { return id; }
    public String getDisplayName() { return displayName; }
    public String getEmailMasked() { return emailMasked; }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                ", emailMasked='" + emailMasked + '\'' +
                '}';
    }
} 