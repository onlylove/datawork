import dao.UserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

public class App {

    public static void main(String [] args) throws Exception{
        try{
            String resource = "mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = factory.openSession();
//            Object object = session.selectOne("test.findUserById",1);
//            System.out.println(object);
            Object object2 = session.selectOne("test.findBlogById",1);
            System.out.println(object2);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
