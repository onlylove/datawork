import com.alibaba.fastjson.JSON;
import com.chenzr.datawork.executor.resultset.DefaultResultSetHandler;
import dao.UserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.IntegerTypeHandler;

import java.io.Reader;
import java.sql.*;

public class App {

    public static void main(String [] args) throws Exception{
        try{
            String resource = "mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = factory.openSession();
//            Object object = session.selectOne("test.findUserById",1);
//            System.out.println(object);
            Object object2 = session.selectList("test.findBlogById",3);
            System.out.println(JSON.toJSON(object2));
            //IntegerTypeHandler

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main33(String[] args) throws ClassNotFoundException, SQLException {
        //1.声明链接参数
        String url = "jdbc:mysql://localhost:3306/mybatis";//数据库的路径
        String user = "root";
        String password = "root";
        //2.注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //3.建立数据库连接，需要用到驱动管理器
        Connection con = DriverManager.getConnection(url, user, password);
        //4.定义SQL语句
        String sql = "SELECT\n" +
                "\tB.id AS id,\n" +
                "\tB.title AS title,\n" +
                "\tA.id AS author_id,\n" +
                "\tA.username AS author_username,\n" +
                "\tA. PASSWORD AS author_password,\n" +
                "\tA.email AS author_email,\n" +
                "\tA.bio AS author_bio,\n" +
                "\tA.favourite_section AS author_favourite_section,\n" +
                "\tP.id AS post_id,\n" +
                "\tP.blog_id AS post_blog_id,\n" +
                "\tP.author_id AS post_author_id,\n" +
                "\tP.created_on AS post_created_on,\n" +
                "\tP.section AS post_section,\n" +
                "\tP. SUBJECT AS post_subject,\n" +
                "\tP.body AS post_body,\n" +
                "\tP.draft AS draft,\n" +
                "\tC.id AS post_comment_id,\n" +
                "\tC.post_id AS post_comment_post_id,\n" +
                "\tC. NAME AS post_comment_name,\n" +
                "\tC. COMMENT AS post_comment_text,\n" +
                "\tT.id AS post_tag_id,\n" +
                "\tT. NAME AS post_tag_name\n" +
                "FROM\n" +
                "\tBlog B\n" +
                "LEFT OUTER JOIN Author A ON B.author_id = A.id\n" +
                "LEFT OUTER JOIN Post P ON B.id = P.blog_id\n" +
                "LEFT OUTER JOIN COMMENT C ON P.id = C.post_id\n" +
                "LEFT OUTER JOIN Post_Tag PT ON PT.post_id = P.id\n" +
                "LEFT OUTER JOIN Tag T ON PT.tag_id = T.id ";
        //5.创建sql发送器，是由链接对象创建的
        PreparedStatement pst = con.prepareStatement(sql);
        //6.发送并执行sql语句，得到结果集
         pst.execute();
        DefaultResultSetHandler handler = new DefaultResultSetHandler();
        handler.handleResultSets(pst);
        pst.close();
        con.close();
    }


}
