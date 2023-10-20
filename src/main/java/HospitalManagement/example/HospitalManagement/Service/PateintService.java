package HospitalManagement.example.HospitalManagement.Service;

import HospitalManagement.example.HospitalManagement.Entities.Doctor;
import HospitalManagement.example.HospitalManagement.Entities.Patient;
import HospitalManagement.example.HospitalManagement.Enum.DoctorSpeciality;
import HospitalManagement.example.HospitalManagement.Enum.Symptom;
import HospitalManagement.example.HospitalManagement.Exceptions.NoDoctorInthatLocation;
import HospitalManagement.example.HospitalManagement.Exceptions.PatientAlreadyExist;
import HospitalManagement.example.HospitalManagement.Exceptions.PatientNotExist;
import HospitalManagement.example.HospitalManagement.Exceptions.ThereIsNoDoctorForThatSpeciality;
import HospitalManagement.example.HospitalManagement.RepoistoryLayer.DoctorRepoistory;
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
    private DoctorRepoistory doctorRepoistory;
    @Autowired
    private JavaMailSender mailSender; //for Email Integration
    public String addPatient(Patient patient) throws Exception{


        patienRepository.save(patient);


        // sending the mail to the patient who is registered in this application

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            String body = "Hi " + patient.getPatientName() + " !" +
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


    public List<Doctor> suggestDoctor(Integer patientId)throws Exception {


        Optional<Patient> optionalPatient = patienRepository.findById(patientId);

        if(!optionalPatient.isPresent()) {

            throw new PatientNotExist("there is no Patient with patient id as " + patientId + " in the Database!");
        }

        Patient patient = optionalPatient.get();//get the patient

        Symptom symptom = patient.getSymptom();//get the symptom

        String symptomName = symptom.name(); // get the symptom name

        String cityName = patient.getCityName();

        DoctorSpeciality speciality = null;

        switch (symptomName) {
            case "Arthritis", "BackPain", "TissueInjuries" -> speciality = DoctorSpeciality.ORTHOPEDIC;
            case "Dysmenorrhea" -> speciality = DoctorSpeciality.GYNECOLOGY;
            case "SkinInfection", "SkinBurn" -> speciality = DoctorSpeciality.DERMATOLAGIST;
            case "EarPain" -> speciality = DoctorSpeciality.ENTSPECIALIST;
        }
        List<Doctor> doctorList = doctorRepoistory.findAll();

        List<Doctor> ans = new ArrayList<>();

        boolean doctorExistInthatCity = false;

        for(Doctor doctor : doctorList) {

            if(doctor.getCity().name().equals(cityName)) {
                doctorExistInthatCity = true;
                if(doctor.getSpeciality().equals(speciality)) ans.add(doctor);//doctor found
            }
        }

        if(!doctorExistInthatCity) {
            throw new NoDoctorInthatLocation("We are still waiting to expand to your location");
        }

        if(doctorExistInthatCity && ans.size() == 0) {
            //if the doctors are available in that location but there is no doctor for that speciality
            throw new ThereIsNoDoctorForThatSpeciality("There isn’t any doctor present at your location for your symptom");
        }

        return ans;
    }

}
