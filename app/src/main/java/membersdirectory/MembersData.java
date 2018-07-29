package membersdirectory;

public class MembersData {

    String Name,MobileNumber,EmailId,BloodGroup,Address,Zone;;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public MembersData(String s, String s1, String s2, String s3, String address, String s4) {

        this.Name=s;
        this.MobileNumber=s1;
        this.EmailId=s2;
        this.BloodGroup=s3;
        this.Address=address;
        this.Zone=s4;

    }
}
