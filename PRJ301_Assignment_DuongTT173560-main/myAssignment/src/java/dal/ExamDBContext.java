package dal;

import java.util.ArrayList;
import model.Exam;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Assessment;
import model.Lecturer;
import model.LecturerInfo;
import model.Subject;

public class ExamDBContext extends DBContext<Exam> {

    public ArrayList<Lecturer> getLecturersInfoByID(int lid) {
        ArrayList<Lecturer> lecturerInfo = new ArrayList<>();
        String sql = "SELECT DISTINCT s.lid, s.lname, si.gender, si.email, si.dob, si.phone, si.place " +
                     "FROM lecturers s " +
                     "JOIN lecturers_info si ON s.lid = si.lid " +
                     "WHERE s.lid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, lid);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Lecturer l = new Lecturer();
                    l.setId(rs.getInt("lid"));
                    l.setName(rs.getString("lname"));

                    LecturerInfo ls = new LecturerInfo();
                    ls.setDob(rs.getDate("dob"));
                    ls.setGender(rs.getString("gender"));
                    ls.setMail(rs.getString("email"));
                    ls.setPhone(rs.getString("phone"));
                    ls.setPlace(rs.getString("place"));

                    l.setLecturerInfo(ls);
                    lecturerInfo.add(l);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExamDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
        return lecturerInfo;
    }

    public void updateLecturerInfo(LecturerInfo model) {
        String sql = "UPDATE lecturers_info " +
                     "SET gender=?, email=?, dob=?, phone=?, place=? " +
                     "WHERE lid=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, model.getGender());
            stm.setString(2, model.getMail());
            stm.setDate(3, new Date(model.getDob().getTime())); // Assuming model.getDob() returns java.util.Date
            stm.setString(4, model.getPhone());
            stm.setString(5, model.getPlace());
            stm.setInt(6, model.getLid());

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ExamDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
    }

    public ArrayList<Exam> getExamsByCourse(int cid) {
        ArrayList<Exam> exams = new ArrayList<>();
        String sql = "SELECT e.eid, e.duration, e.[from], a.aid, a.aname, a.weight, sub.subid, sub.subname " +
                     "FROM exams e " +
                     "INNER JOIN assessments a ON a.aid = e.aid " +
                     "INNER JOIN subjects sub ON sub.subid = a.subid " +
                     "INNER JOIN courses c ON c.subid = sub.subid " +
                     "WHERE c.cid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, cid);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Exam e = new Exam();
                    e.setId(rs.getInt("eid"));
                    e.setDuration(rs.getInt("duration"));
                    e.setFrom(rs.getTimestamp("from"));

                    Assessment a = new Assessment();
                    a.setId(rs.getInt("aid"));
                    a.setName(rs.getString("aname"));
                    a.setWeight(rs.getFloat("weight"));

                    Subject sub = new Subject();
                    sub.setId(rs.getInt("subid"));
                    sub.setName(rs.getString("subname"));
                    a.setSubject(sub);

                    e.setAssessment(a);
                    exams.add(e);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExamDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
        return exams;
    }

    public ArrayList<Exam> getExamsByExamIds(ArrayList<Integer> eids) {
        ArrayList<Exam> exams = new ArrayList<>();
        if (eids == null || eids.isEmpty()) {
            return exams;
        }

        StringBuilder sql = new StringBuilder("SELECT e.eid, e.[from], a.aid, a.aname, a.weight " +
                                             "FROM exams e " +
                                             "INNER JOIN assessments a ON a.aid = e.aid " +
                                             "WHERE eid IN (");
        for (int i = 0; i < eids.size(); i++) {
            sql.append("?");
            if (i < eids.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");

        try (PreparedStatement stm = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < eids.size(); i++) {
                stm.setInt(i + 1, eids.get(i));
            }

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Exam e = new Exam();
                    e.setId(rs.getInt("eid"));
                    e.setFrom(rs.getTimestamp("from"));

                    Assessment a = new Assessment();
                    a.setId(rs.getInt("aid"));
                    a.setName(rs.getString("aname"));
                    a.setWeight(rs.getFloat("weight"));

                    e.setAssessment(a);
                    exams.add(e);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExamDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
        return exams;
    }

    @Override
    public void insert(Exam model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Exam model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Exam model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exam get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Exam> list() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExamDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
