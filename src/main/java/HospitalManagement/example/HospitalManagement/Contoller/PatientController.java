package HospitalManagement.example.HospitalManagement.Contoller;


import HospitalManagement.example.HospitalManagement.Entities.Patient;
import HospitalManagement.example.HospitalManagement.Service.PateintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {

  @Autowired
  private PateintService pateintService;

  @PostMapping("/add")
  public ResponseEntity addPatient(@RequestBody Patient patient) {

    try{
      String response = pateintService.addPatient(patient);

      return new ResponseEntity(response , HttpStatus.OK);
    }catch (Exception e) {
      return  new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/deletePatient/{id}")
  public ResponseEntity deletePatient(@PathVariable ("id") Integer patientId) throws Exception{

    try{
      String response = pateintService.deletePatient(patientId);

      return new ResponseEntity(response , HttpStatus.OK);
    }
    catch (Exception e) {
      return new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
    }
  }
}
