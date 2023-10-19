package HospitalManagement.example.HospitalManagement.RepoistoryLayer;

import HospitalManagement.example.HospitalManagement.Entities.Doctor;
import HospitalManagement.example.HospitalManagement.Enum.City;
import HospitalManagement.example.HospitalManagement.Enum.DoctorSpeciality;
import HospitalManagement.example.HospitalManagement.Service.DoctorService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DoctorRepoistory extends JpaRepository<Doctor , Integer> {

    Doctor findDoctorByDoctorNameAndPhoneNumberAndSpeciality(String doctorName , String phoneNumber , DoctorSpeciality speciality);
}
