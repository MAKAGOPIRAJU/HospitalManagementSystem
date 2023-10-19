package HospitalManagement.example.HospitalManagement.Entities;

import HospitalManagement.example.HospitalManagement.Enum.City;
import HospitalManagement.example.HospitalManagement.Enum.DoctorSpeciality;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Doctor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorId;

    private String doctorName;

    @Enumerated(value = EnumType.STRING)
    private City city;

    private  String email;

    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private DoctorSpeciality speciality;


}
