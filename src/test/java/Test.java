import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Test {

    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/test";
    public static final String NAME = "root";
    public static final String PASSWORD = "root";
    public static Connection connection;

    Logger logger = LoggerFactory.getLogger(Test.class);

    @org.junit.Test
    public void test() {
        logger.info("=================");
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, NAME, PASSWORD);
    }

    @org.junit.Test
    public void connect (){
        try {
            Class.forName(DRIVER);
            connection = Test.getConnection();
        } catch (Exception e) {
            logger.info("连接驱动失败");
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            /*preparedStatement = connection.prepareStatement("select * from apps limit 0,20");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               String name = resultSet.getString("app_name");
               String url = resultSet.getString("url");
               String country = resultSet.getString("country");
                logger.info("name:"+name+"---url:"+url+"-----country:"+country);
            }*/
            System.out.println("快始");
            for (int i = 1; i < 10000; i++) {
                preparedStatement = connection.prepareStatement("insert into apps(app_name,url,country) values(?,?,?)");
                preparedStatement.setString(1, i+"、app名称");
                preparedStatement.setString(2,i+"、app链接");
                preparedStatement.setString(3,i+"、app国家");
                preparedStatement.executeUpdate();
                if (i % 5000 == 0) {
                    System.out.println("一半了");
                    new Thread().sleep(10000);
                }

            }
            System.out.println("结束");
            connection.commit();
        } catch (Exception e) {
           try {
                connection.rollback();
            } catch (Exception ex) {
            }
            logger.info("查询失败");
        } finally {
            try {
                if(!preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
                if(!connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.connect();
    }

    @org.junit.Test
    public void t() {
       /* SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS");
        Calendar calendar = Calendar.getInstance();
        String time = format.format(calendar.getTime());
        int now_month = calendar.get(Calendar.MONTH); //现在月份
        System.out.println(now_month);
        System.out.println(time);
        calendar.set(Calendar.MONTH, now_month - 11); //过去beginMonth月
        time = format.format(calendar.getTime());
        System.out.println(time);*/

        double random = (int)(Math.random() * (80 - 60 + 1)) + 60; //随机增加60-80的随机数
        random = random / 1000; //按浏览数：点赞数=100:1的比例进行计算
        System.out.println(random);
        random = Math.floor(1000 * random);//向下取整点赞数
        int increasePraiseCount = (int) random;
        System.out.println(increasePraiseCount);
    }

}