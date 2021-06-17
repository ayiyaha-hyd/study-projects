package hyd;

import com.hyd.config.SpringConfig;
import com.hyd.domain.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class JdbcTemplateTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test9(){
        StringBuffer sql = new StringBuffer();
        sql.append("select * from account where money= ? ");
        List<Object> params = new ArrayList<Object>();
        params.add(20);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(), params.toArray());
        List<Account> list2 = jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<Account>(Account.class));
        System.out.println(list);
        System.out.println(list2);
    }
    @Test
    public void test8(){
        Long res = jdbcTemplate.queryForObject("select count(*) from account", Long.class);
        System.out.println(res);
    }
    @Test
    public void test7(){
        List<Object> params = new ArrayList<Object>();
        params.add(10);
        params.add("zhangsan");
        int res1 = jdbcTemplate.update("update account set money=? where name=?", params.toArray());
        System.out.println(res1);
    }

    @Test
    public void test6(){
        Account res3 = jdbcTemplate.queryForObject("select * from account where name=?", new BeanPropertyRowMapper<Account>(Account.class), "zhangsan");
        System.out.println(res3);
    }

    @Test
    public void test5(){
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from account");
        System.out.println(list);
    }

    @Test
    public void test4(){
        List<Account> list = jdbcTemplate.query("select * from account", new RowMapper<Account>() {
            public Account mapRow(ResultSet resultSet, int i) throws SQLException {
                Account a = new Account();
                a.setName(resultSet.getString("name"));
                a.setMoney(resultSet.getDouble("money"));
                return a;
            }
        });
        System.out.println(list);
    }

    @Test
    public void test3(){
        List<Account> list = jdbcTemplate.query("select * from account", new BeanPropertyRowMapper<Account>(Account.class));
        System.out.println(list);
    }
    @Test
    public void test2(){
        System.out.println(dataSource);
    }
    @Test
    public void test1(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
    }
}
