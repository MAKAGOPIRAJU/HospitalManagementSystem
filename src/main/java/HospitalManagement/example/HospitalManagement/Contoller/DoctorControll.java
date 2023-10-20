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

}


