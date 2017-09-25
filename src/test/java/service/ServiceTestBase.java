package service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by kejian on 2017/6/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class ServiceTestBase extends AbstractTransactionalJUnit4SpringContextTests{

    @Override
    @Resource(name = "xDataSource")
    public void setDataSource(DataSource dataSource){
        // TODO Auto-generated method stub
        super.setDataSource(dataSource);
    }

}
