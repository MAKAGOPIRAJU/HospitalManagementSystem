package HospitalManagement.example.HospitalManagement.Service;

import HospitalManagement.example.HospitalManagement.Entities.Patient;
import HospitalManagement.example.HospitalManagement.Exceptions.PatientAlreadyExist;
import HospitalManagement.example.HospitalManagement.Exceptions.PatientNotExist;
import HospitalManagement.example.HospitalManagement.RepoistoryLayer.PatienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class PateintService {
    @Autowired
    private PatienRepository patienRepository;
    @Autowired
    private JavaMailSender mailSender; //for Email Integration
    public String addPatient(Patient patient) throws Exception{


        patienRepository.save(patient);


        // sending the mail to the patient who is registered in this application

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        String body = "Hi "+ patient.getPatientName() +" !" +
                "You have successfully registered. You can start Search for the Doctors basing you Symptom now.";

        mailMessage.setFrom("makasrinivasulu01@gmail.com"); // from which mail u want to send
        mailMessage.setTo(patient.getEmail());//to which one send to mail
        mailMessage.setSubject("Welcome To the Apollo Hospital's !!");//subject
        mailMessage.setText(body);//message in the box

        mailSender.send(mailMessage);

        return "the patient " + patient.getPatientName() + " is added to the db successfully !";
    }

    public String deletePatient(Integer patientId) throws Exception{

        //validation
        Optional<Patient> optionalPatient = patienRepository.findById(patientId);

        if(!optionalPatient.isPresent()) {

            throw new PatientNotExist("there is no Patient with patient id as " + patientId + " in the Database!");
        }

        // if the patient is present
       patienRepository.deleteById(patientId);//remove the patient entity

        return " the patient with patient id  " + patientId + " deleted successfully from the Database!";
    }


}
