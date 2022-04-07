package com.example.commentservice.model;

import com.example.commentservice.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {


    @Id
    private String userId;
    @NotEmpty(message = "First name is required")
    private String firstName;
    @NotEmpty(message = "Middle name is required")
    private String middleName;
    @NotEmpty(message = "Last name is required")
    private String lastName;
    @NotEmpty(message = "Phone Number is required")
    @Size(min=10,max = 10)
    private String phoneNumber;
    @NotNull(message = "Date of Birth is required")
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotEmpty(message = "address is required")
    private String address;
    @NotEmpty(message = "Employee Id is required")
    private String employeeId;
    @NotNull(message = "Blood Group is required")
    private String bloodGroup;
    @NotEmpty(message = "Email is required")
    private String email;

}
