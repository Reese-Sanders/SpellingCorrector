

public class Person {

    private static int nextID = 0;
    private static int generateID() {
        return ++nextID;
    }

    private int id;
    private String name;
    private int age;
    private YearInSchool year;

    public Person(String name, int age, YearInSchool year) {
        setId(generateID());
        setName(name);
        setAge(age);
        setYear(year);
    }

    public Person(String name, int age){
        this(name, age, YearInSchool.FRESHMAN);
    }
    //end of constructors
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public YearInSchool getYear() {
        return year;
    }

    public void setYear(YearInSchool year) {
        this.year = year;
    }
    //end of setters/getters
    @Override
    public String toString() {
        return String.format("name: %s, age: %d, year: %s", name, age, year);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (obj == this){
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Person p = (Person)obj;

        return (this.name.equals(p.name) &&
                this.age == p.age &&
                this.year == p.year);
    }

    @Override
    public int hashCode() {
        return(name.hashCode() * age * year.hashCode());
    }
}



