package service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hellogood.http.vo.ActionVO;
import com.hellogood.service.ActionService;

public class ActionServiceTest extends ServiceTestBase {

	Logger logger = LoggerFactory.getLogger(ActionServiceTest.class);

	@Autowired
	private ActionService actionService;

	/**
	 * 分页查询
	 */
	@Test
	public void getActionByUrl() {
		ActionVO action = actionService
				.getActionByUrl("/pages/action/action-list.jsp");
		logger.info(action.toString());
	}

	@Test
	public void getLocation() {
		String action = actionService
				.getLocation("/pages/action/action-list.jsp");
		logger.info(action.toString());
	}

}
