package HospitalManagement.example.HospitalManagement.Contoller;

import HospitalManagement.example.HospitalManagement.Entities.Doctor;
import HospitalManagement.example.HospitalManagement.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorControll {
    @Autowired
    private DoctorService doctorService;

    @PostMapping("/addTheDoctor")
    public ResponseEntity addDoctor(@RequestBody Doctor doctor) {

        try{
            String response = doctorService.addDoctor(doctor);

            return new ResponseEntity(response , HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteDoctor/{id}")
    public ResponseEntity deleteDoctor(@PathVariable ("id") Integer id) {
        try{
          String response = doctorService.deleteDoctor(id);

          return new ResponseEntity(response , HttpStatus.OK);
        }catch (Exception e) {
            return  new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    //suggest the doctor basing on the location and symptom

    @GetMapping("/suggestDoctor/{id}")
    public ResponseEntity suggestDoctor(@PathVariable("id") Integer patientId) throws Exception {

         try {
             List<Doctor> doctorList = doctorService.suggestDoctor(patientId);
             return new ResponseEntity(doctorList , HttpStatus.OK);
         }catch (Exception e) {
             return  new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
         }
    }
}
//  DOUBTS

// 1. WHY MAIL WAS NULL IN THE PATIENT
// 3. name should atleast 3 characters how to fix this
// 4.mobile number should atleast 10 characters how to fix this
// 5. mail should be valid how to check this

