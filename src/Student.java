public class Student {
    private int id;
    private String name;
    private String email;
    private String department;

    public Student(){}

    public Student(int id, String name, String email, String department){
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
    }

    //getter and setter
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getDepartment(){
        return department;
    }
    public void setDepartment(String department){
        this.department = department;
    }

    @Override
    public String toString(){
        return String.format("Id: %d, Name: %s, Email: %s, Department: %s.", id, name,email, department);
    }
}
