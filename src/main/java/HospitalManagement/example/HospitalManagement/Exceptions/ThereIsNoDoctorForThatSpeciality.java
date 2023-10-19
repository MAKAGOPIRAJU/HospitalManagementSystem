package HospitalManagement.example.HospitalManagement.Exceptions;

public class ThereIsNoDoctorForThatSpeciality extends  Exception{
    public ThereIsNoDoctorForThatSpeciality(String message) {
        super(message);
    }
}
