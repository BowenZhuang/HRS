package example.com.hrs;

/**
 * Created by kdkim on 02/18/2015.
 */
public class PatientInfo {
    public int id;
    public String name;
    public String dob;
    public String phone;
    public int bCalled;
    public int bSentSMS;
    PatientInfo() {
        id=0;
        name = "";
        dob = "";
        phone = "";
        bCalled = 0;
        bSentSMS = 0;
    }
}
