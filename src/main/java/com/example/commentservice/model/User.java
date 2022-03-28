package com.example.commentservice.model;

import com.example.commentservice.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {


    private String userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private Date dateOfBirth;
    private Gender gender;
    private String address;
    private String employeeId;
    private String bloodGroup;
    private String email;
    private String password;

}
