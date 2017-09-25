package service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.hellogood.http.vo.EmployeeVO;
import com.hellogood.service.EmployeeService;

@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
public class UpdatePasswordServiceTest extends ServiceTestBase {

	@Autowired
	private EmployeeService employeeService;

	@Test
	public void updatePassword() {
		// 获取所用用户
		List<EmployeeVO> empList = employeeService.findAll();
		for (EmployeeVO employeeVO : empList) {
			System.out.println("原密码：" + employeeVO.getPassword());
			employeeVO.setPassword("123456");
			System.out.println("现密码：" + employeeVO.getPassword());
			employeeService.update(employeeVO);
		}
	}
}
