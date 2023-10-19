package HospitalManagement.example.HospitalManagement.Service;

import HospitalManagement.example.HospitalManagement.Entities.Doctor;
import HospitalManagement.example.HospitalManagement.Entities.Patient;
import HospitalManagement.example.HospitalManagement.Enum.DoctorSpeciality;
import HospitalManagement.example.HospitalManagement.Enum.Symptom;
import HospitalManagement.example.HospitalManagement.Exceptions.*;
import HospitalManagement.example.HospitalManagement.RepoistoryLayer.DoctorRepoistory;
import HospitalManagement.example.HospitalManagement.RepoistoryLayer.PatienRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DoctorService {
    @Autowired
    private DoctorRepoistory doctorRepoistory;
   @Autowired
   private PatienRepository patienRepository;
   @Autowired
   private JavaMailSender javaMailSender;
    public String addDoctor(Doctor doctor)throws Exception {

        Doctor doctorObj= doctorRepoistory.findDoctorByDoctorNameAndPhoneNumberAndSpeciality(doctor.getDoctorName() ,
                doctor.getPhoneNumber() , doctor.getSpeciality());

        if(doctorObj != null) {
            throw new DoctorAlreadyExist("the doctor was already Exist");
        }

        doctorRepoistory.save(doctor);

        // sending mail to the doctor who was registered in our database

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        String body = "Hi "+ doctor.getDoctorName() +" !" +
                "You have successfully registered. We are happy to have you hear!.";

        mailMessage.setFrom("makasrinivasulu01@gmail.com"); // from which mail u want to send
        mailMessage.setTo(doctor.getEmail());//to which one send to mail
        mailMessage.setSubject("Welcome To the Apollo Hospital's !!");//subject
        mailMessage.setText(body);//message in the box

        javaMailSender.send(mailMessage);

        return "doctor " + doctor.getDoctorName() + " added to database successfully";
    }

    public String deleteDoctor(Integer doctorId) throws Exception{

        Optional<Doctor> doctorOptional = doctorRepoistory.findById(doctorId);

        if(!doctorOptional.isPresent()) {
            throw new DoctroNotExist("No Doctor present with " + doctorId + " in the Database!");
        }

        doctorRepoistory.deleteById(doctorId);

        return "deleted the doctor with doctor id " + doctorId + " from the database successfully!";
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
