package com.Hello_User.Hello_User;

public class Member {
    private String name;
    private Long id;
    private String email;
    private String phoneNumber;
    public Member(long id, String name ,String email, String phoneNumber ){
        this.name=name;
        this.id=id;
        this.email=email;
        this.phoneNumber=phoneNumber;
    }
 
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
   
    
}
