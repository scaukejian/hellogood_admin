/**
 * Created by Administrator on 2017/5/28.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据处理
 */
public class DataFormat {

    public static String driver = "com.mysql.jdbc.Driver";
    public static String dbName = "hellogood_admin";
    public static String password = "mysql88";
    public static String userName = "root";
    public static String url = "jdbc:mysql://192.168.1.88:3306/" + dbName;
    public static Connection conn;
    public static PreparedStatement ps;
    public static ResultSet rs;

    static {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName,
                    password);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 相册处理
     * @return
     * @throws SQLException
     */
    public static List<Photo> tempPhotosFormat() throws SQLException {
        List<Photo> list = new ArrayList<Photo>();
        try {
            String sql = "select id,user_name,temp_photo from api_user where temp_photo is not null ";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Photo p = new Photo(rs.getInt(1), rs.getString(3));
                list.add(p);
//                System.out.println("id : " + rs.getInt(1) + " user_name : "
//                        + rs.getString(2) + " temp_photo : " + rs.getString(3));

            }
            for (Photo photo : list) {
                String[] arrs = photo.getImgName().split(",");
                for (String s : arrs) {
                    Photo p = new Photo(photo.getUserId(), s);
                    insertUserPhoto(p);
                }
            }
        } finally {
            closeConnection(conn, ps, rs);

        }
        return list;
    }

    public static void insertUserPhoto(Photo photo) throws SQLException {
        String sql = "insert into api_user_photo(user_id, img_name, original_img_name, head_flag) " +
                "values (" + photo.getUserId() + ",'" + photo.getImgName() + "','" + photo.getImgName() + "', "+photo.getHeadFlag()+");";
        ps = conn.prepareStatement(sql);
        System.out.println(sql);
//        ps.setInt(1, photo.getUserId());
//        ps.setString(2, photo.getImgName());
//        ps.setString(3, photo.getRealImgName());
//        ps.setInt(4, photo.getHeadFlag());
        ps.executeUpdate(sql);
    }



        public static void main(String[] args) {
        try {
            List<Photo> list = tempPhotosFormat();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
