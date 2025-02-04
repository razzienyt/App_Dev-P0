public class Person implements Comparable<Person>
{

    private String id, fname, lname, sex, occupation, contactNum;
    private int countryCode, areaCode;

    public Person(String id, String fname, String lname, String sex, String occupation,
            String contactNum, int countryCode, int areaCode)
    {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.sex = sex;
        this.occupation = occupation;
        this.contactNum = contactNum;
        this.countryCode = countryCode;
        this.areaCode = areaCode;
    }

    public String getFName()
    {
        return fname;
    }

    public void setFName(String fname)
    {
        this.fname = fname;
    }

    public String getLName()
    {
        return lname;
    }

    public void setLName(String lname)
    {
        this.lname = lname;
    }

    public String getFullName()
    {
        return this.getFName() + " " + this.getLName();
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getPronoun() {
        if (this.sex.equalsIgnoreCase("Male")) {
            return "His";
        } else if (this.sex.equalsIgnoreCase("Female")) {
            return "Her";
        } else {
            return "Their"; 
        }
    }

    public String getOccupation()
    {
        return occupation;
    }

    public void setOccupation(String occupation)
    {
        this.occupation = occupation;
    }

    public String getContactNum()
    {
        return contactNum;
    }

    public void setContactNum(String contactNum)
    {
        this.contactNum = contactNum;
    }

    public int getAreaCode()
    {
        return areaCode;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setAreaCode(int areaCode)
    {
        this.areaCode = areaCode;
    }

    public int getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(int countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return "+" + this.countryCode + " (" + this.areaCode + ") " + this.contactNum;
    }

    /**
     * Compare the lexicographic order of this person object and another person object. <br>
     * <br>
     * Must compare by last name first, and then first name if both person objects have the same
     * last name. Assume that no two or more person objects will share the same first name.
     * 
     * @param o The person object to be compared.
     * @return Lexicographic flag if this person object should go first before the other person
     *         object. <br>
     *         <br>
     *         -1 if this person object should go first. <br>
     *         <br>
     *         0 if both person objects share the same last name and first name. <br>
     *         <br>
     *         1 if the other person object should go first.
     */

    @Override
public int compareTo(Person o) {
    if (o == null) return 1; // Null objects come last

    // Handle null last names safely
    if (this.lname == null && o.lname == null) return 0;
    if (this.lname == null) return -1; 
    if (o.lname == null) return 1;

    int lastNameCompare = this.lname.compareTo(o.lname);
    if (lastNameCompare != 0) {
        return lastNameCompare; 
    }

    // Handle null first names safely
    if (this.fname == null && o.fname == null) return 0;
    if (this.fname == null) return -1;
    if (o.fname == null) return 1;

    return this.fname.compareTo(o.fname);
}


    public String toString()
    {
        return String.format("%s is a %s. %s number is %s", this.getFullName(),
                this.getOccupation(), this.getPronoun(), this.getPhoneNumber());
    }
}
