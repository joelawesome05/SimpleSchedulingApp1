/*
 * GenericResource.java
 */

package bo.edu.ucbcba.simplescheduling.resource;

import bo.edu.ucbcba.simplescheduling.model.MyClass;
import bo.edu.ucbcba.simplescheduling.model.Student;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Diego
 */
public class GenericResource {
    
    private static final Map<Integer, Student> studentMap = new HashMap<>();
    private static final Map<String, MyClass> myClassMap = new HashMap<>();
    
    public static void putStudent(Student student) {
        if (student != null) {
            getStudentMap().put(student.getStudentId(), student);
        }
    }
    
    public static Student getStudent(Integer studentId) {
        return getStudentMap().get(studentId);
    }
    
    public static boolean removeStudent(Integer studentId) {
        return getStudentMap().remove(studentId) != null;
    }
    
    public static boolean removeClass(String code) {
        return getClassMap().remove(code) != null;
    }

    /**
     * @return the studentMap
     */
    public static Map<Integer, Student> getStudentMap() {
        return studentMap;
    }

     public static Map<String, MyClass> getClassMap() {
        return myClassMap;
    }
     
     public static MyClass getClass(String code) {
         return getClassMap().get(code);
     }
     
     public static void putClass(MyClass clas){
     if(clas!=null) {
        getClassMap().put(clas.getCode(), clas);
        }
     }
     
    static Object getCode(String code) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 
}
