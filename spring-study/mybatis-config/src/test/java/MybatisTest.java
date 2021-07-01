import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hyd.dao.UserMapper;
import com.hyd.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class MybatisTest {
    @Test
    public void test1() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //设置分页参数
        PageHelper.startPage(1,5);

        List<User> userList = userMapper.findAll();
        for (User user : userList) {
            System.out.println(user);
        }
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        //userPageInfo...
        sqlSession.close();
    }

    @Test
    public void test2()throws Exception{
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = null;
//        Date date = new Date();
//        for (int i=0;i<20;i++){
//            user = new User();
//            user.setUsername("user"+i);
//            user.setPassword("1234-"+i);
//            user.setBirthday(date);
//            userMapper.save(user);
//            user = null;
//        }
        sqlSession.close();
    }
}
