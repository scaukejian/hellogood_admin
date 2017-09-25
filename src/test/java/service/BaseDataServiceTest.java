package service;

import com.hellogood.enumeration.BaseDataType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hellogood.service.BaseDataService;

public class BaseDataServiceTest extends ServiceTestBase {

	@Autowired
	private BaseDataService baseDataService;

	@Test
	public void getValidDataByType() {

		System.out.println(baseDataService.getValidDataByType(BaseDataType.SMS_CHANNEL, false));
	}
}
