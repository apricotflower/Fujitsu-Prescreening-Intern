import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author  Xiayan Zhong
 * This application can read a text file containing employee information and outputs the sorted data to the console.
 *
 */

public class EReport {

    /**
     * List for storing employees' information read from the text file.
     */
    private List<Employee> employees;

    /**
     * Sort the employees alphabetically by employee last name and send the output to the console.
     */
    private void sortByLastName(){
        System.out.println("Processing by last (family) Name...");
        employees.sort(Comparator.comparing(Employee::getLastName));
        employees.forEach(Employee::getReadLine);
    }

    /**
     * Sort the employees by employee number and send the output to the console.
     */
    private void sortByNumber(){
        System.out.println("Processing by employee number...");
        employees.sort(Comparator.comparing(Employee::getNumber));
        employees.forEach(Employee::getReadLine);
        System.out.println("\r");
    }

    /**
     *  Read the input file and store the information of employees into the List "employees" after filtering.
     */
    private void readFile() {
        String fileName = "employees.dat";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            employees = stream
                    .filter(line -> !line.equals("") && !line.substring(0,1).equals("#"))
                    .map(Employee::new).filter(e -> e.getFlag() == 1)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Read file Error!");
        }
    }

    /**
     *  Run the application.
     */
    private void run(){
        readFile();
        sortByNumber();
        sortByLastName();
    }

    public static void main(String[] args) {
        new EReport().run();
    }
}

/**
 *
 * class of employee.The attributes of employee is here.
 *
 */
class Employee{
    private int number;
    private String firstName;
    private  String lastName;

    /**
     * This is the original String of one line from the input file.
     */
    private String readLine;

    /**
     * This attribute is for format error handling.
     * If flag == 1, it means the format is correct.
     * If the flag == -1, it means the format is not correct.
     */
    private int flag = 1;

    /**
     * Employee constructor
     *
     * @param readLine The original String of one line from the input file.
     *
     */
    public Employee(String readLine){
            this.readLine = readLine;
            build(readLine);
        }

    /**
     * Deal with the readLine string and get the information to assign the attribute number, firstName, LastName and flag.
     *
     * @param readLine  The original String of one line from the input file.
     *
     */
    private void build(String readLine){
        try {
            this.number =  Integer.parseInt(readLine.split(",")[0].trim());
            String name = readLine.split(",")[1].trim();
            this.firstName =  name.split("\\s+")[0];
            this.lastName =  name.split("\\s+")[1];
        }catch (Exception e){
            this.flag = -1;
        }

    }

    public String getReadLine() {
        System.out.println(readLine);
        return readLine;
    }

    public int getNumber() {
        return number;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getFlag() {
        return flag;
    }
}
