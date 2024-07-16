package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Assessment;
import model.Exam;
import model.Grade;
import model.Student;
import model.StudentInfo;
import model.Subject;

/**
 * Data Access Layer for Student-related database operations.
 */
public class StudentDBContext extends DBContext<Student> {
    
    /**
     * Retrieves a list of grades for a given student ID.
     * 
     * @param model

     */
   

    public void updateStudentInfo(StudentInfo model) {
        PreparedStatement stm_insert = null;
        try {
            String sql_insert_emp = "UPDATE student_info "
                    + "SET gender=?, email=?, "
                    + "dob=?, phone=?, "
                    + "place=? WHERE sid=?";

            stm_insert = connection.prepareStatement(sql_insert_emp);
         

                  stm_insert.setString(1, model.getGender());
            stm_insert.setString(2, model.getMail());
            stm_insert.setDate(3, new Date(model.getDob().getTime())); // Assuming model.getDob() returns java.util.Date
            stm_insert.setString(4, model.getPhone());
            stm_insert.setString(5, model.getPlace());
            stm_insert.setInt(6, model.getSid());
            
            stm_insert.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm_insert.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    /**
     * Retrieves a list of students enrolled in a given course ID.
     * 

     * @param cid the course ID
     * @return a list of students enrolled in the course
     */
    
  public ArrayList<Student> getStudentsInfoByID(int sid) {
    ArrayList<Student> studentInfo = new ArrayList<>();
    PreparedStatement stm = null;
    ResultSet rs = null;

    try {
        String sql = "SELECT DISTINCT s.sid, s.sname, si.gender, si.email, si.dob, si.phone, si.place " +
                     "FROM students s " +
                     "JOIN student_info si ON s.sid = si.sid " +
                     "WHERE s.sid = ?";
        stm = connection.prepareStatement(sql);
        stm.setInt(1, sid);
        rs = stm.executeQuery();

        if (rs.next()) {
            Student s = new Student();
            s.setId(rs.getInt("sid"));
            s.setName(rs.getString("sname"));

            StudentInfo si = new StudentInfo();
            si.setDob(rs.getDate("dob"));
            si.setGender(rs.getString("gender"));
            si.setMail(rs.getString("email"));
            si.setPhone(rs.getString("phone"));
            si.setPlace(rs.getString("place"));

            s.setStudentInfo(si);
            studentInfo.add(s);
            
            // Debugging output
            System.out.println("Retrieved student: " + s.getName());
        }
    } catch (SQLException ex) {
        Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        disconnect();
    }

    return studentInfo;
}

    public ArrayList<Student> getStudentsByCourse(int cid) {
        ArrayList<Student> students = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT s.sid, s.sname FROM students s "
                       + "INNER JOIN students_courses sc ON s.sid = sc.sid "
                       + "INNER JOIN courses c ON c.cid = sc.cid "
                       + "WHERE c.cid = ?";

            stm = connection.prepareStatement(sql);
            stm.setInt(1, cid);
            rs = stm.executeQuery();

            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("sid"));
                s.setName(rs.getString("sname"));
                students.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            disconnect();
        }

        return students;
    }

    @Override
    public void insert(Student model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Student model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Student model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Student get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Student> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
