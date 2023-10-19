package HospitalManagement.example.HospitalManagement.RepoistoryLayer;

import HospitalManagement.example.HospitalManagement.Entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatienRepository extends JpaRepository<Patient , Integer> {

}
