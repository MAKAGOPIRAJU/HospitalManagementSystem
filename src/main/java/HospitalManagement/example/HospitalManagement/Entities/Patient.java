package HospitalManagement.example.HospitalManagement.Entities;

import HospitalManagement.example.HospitalManagement.Enum.Symptom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


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
    @Size(min = 3, max = 50, message = "Name should be have at least three characters")
    private String patientName;

    @Column(length = 20)
    @Size(max = 20, message = "Please select a city with less than 20 characters")
    private String cityName;


    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$", message = "Enter a valid email address")
    private String email;


    @Column(length = 10, nullable = false)
    @Size(min = 10, max = 10 ,message = "please enter a  valid mobile number")
    private String mobNo;

    @Enumerated(value = EnumType.STRING)
    private  Symptom symptom;

}
