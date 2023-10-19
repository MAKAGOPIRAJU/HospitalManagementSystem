package HospitalManagement.example.HospitalManagement.Entities;

import HospitalManagement.example.HospitalManagement.Enum.Symptom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer patientId;

    @Column(length = 50, nullable = false)
    @Size(min = 3, max = 50)
    private String patientName;

    @Column(length = 20)
    private String cityName;


    @Email(message = "Email should be a valid email address")
    private String email;

    @Column(length = 10, nullable = false)
    @Size(min = 10, max = 10)
    private String mobNo;

    @Enumerated(value = EnumType.STRING)
    private  Symptom symptom;

}
