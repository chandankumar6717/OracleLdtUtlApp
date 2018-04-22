package multitoolapp.common;

import multitoolapp.common.JdbcDaoConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcDoaImpl {

    Connection connection = null;
    PreparedStatement ptmt = null;
    ResultSet resultSet = null;

    public JdbcDoaImpl() {

    }

    private Connection getConnection(String InstanceName) throws SQLException {
        Connection conn;
        conn = JdbcDaoConnectionFactory.getInstance().getConnection(InstanceName);
        return conn;
    }

    public void FindAllDbObjects(String InstanceName) {
        try {
            String queryString = "SELECT * FROM student";
            connection = getConnection(InstanceName);
            ptmt = connection.prepareStatement(queryString);
            resultSet = ptmt.executeQuery();
            while (resultSet.next()) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (ptmt != null) {
                    ptmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public String FindDbObject(String InstanceName ,String DbObjectName, String DbObjectType) {

        try {
            String queryString = "select OBJECT_NAME,OBJECT_TYPE,STATUS from SYS.DBA_OBJECTS where object_name = " + "'" + DbObjectName + "'"
                    + " AND Object_type = " + "'" + DbObjectType + "'";
            //System.out.print(queryString + "\t");
            connection = getConnection(InstanceName);
            ptmt = connection.prepareStatement(queryString);
            resultSet = ptmt.executeQuery();
            while (resultSet.next()) {

                return resultSet.getString("STATUS");

            }

            if (resultSet != null) {
                resultSet.close();
            }
            if (ptmt != null) {
                ptmt.close();
            }
            if (connection != null || !connection.isClosed()) {
                connection.close();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(JdbcDoaImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(JdbcDoaImpl.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        } finally {
            try {
                if (connection != null || !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JdbcDoaImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        //return resultSet.getString("STATUS");

    }
    
    public void FindAppCode(String compName) {
        try {
            String queryString = "SELECT * FROM student";
            connection = getConnection(compName);
            ptmt = connection.prepareStatement(queryString);
            resultSet = ptmt.executeQuery();
            while (resultSet.next()) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (ptmt != null) {
                    ptmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    
    
//    public void add(StudentBean studentBean) {
//        try {
//            String queryString = "INSERT INTO student(RollNo, Name, Course, Address) VALUES(?,?,?,?)";
//            connection = getConnection();
//            ptmt = connection.prepareStatement(queryString);
//            ptmt.setInt(1, studentBean.getRollNo());
//            ptmt.setString(2, studentBean.getName());
//            ptmt.setString(3, studentBean.getCourse());
//            ptmt.setString(4, studentBean.getAddress());
//            ptmt.executeUpdate();
//            System.out.println("Data Added Successfully");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (ptmt != null) {
//                    ptmt.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }
//    public void update(StudentBean studentBean) {
//
//        try {
//            String queryString = "UPDATE student SET Name=? WHERE RollNo=?";
//            connection = getConnection();
//            ptmt = connection.prepareStatement(queryString);
//            ptmt.setString(1, studentBean.getName());
//            ptmt.setInt(2, studentBean.getRollNo());
//            ptmt.executeUpdate();
//            System.out.println("Table Updated Successfully");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (ptmt != null) {
//                    ptmt.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//        }
//    }
//
//    public void delete(int rollNo) {
//
//        try {
//            String queryString = "DELETE FROM student WHERE RollNo=?";
//            connection = getConnection();
//            ptmt = connection.prepareStatement(queryString);
//            ptmt.setInt(1, rollNo);
//            ptmt.executeUpdate();
//            System.out.println("Data deleted Successfully");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (ptmt != null) {
//                    ptmt.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }

    
}
