package com.hellogood.service.quartz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hellogood.utils.HttpsUtils;
import com.hellogood.utils.StaticFileUtil;

@Component
public class Quartz {

	private final static Logger log = Logger.getLogger(Quartz.class);

	/**
	 * 3分钟后开始, 每10分钟执行一次
	 * @Scheduled(cron = "0 3/10 * * * ?")
     */


	/**
	 * 3分钟后开始, 每5分钟执行一次
	 * @Scheduled(cron = "0 3/5 * * * ?")
	 */


	/**
	 * 每天00:05分执行
	 * 定时处理过期数据
	 * @Scheduled(cron = "0 5 0 * * ?")
     */

	/**
	 * 3分钟后开始, 每20分钟执行一次
	 * @Scheduled(cron = "0 3/20 * * * ?")
	 */

	/**
	 * 每天4:30更新活跃度
	 * @Scheduled(cron = "0 30 4 * * ?")
	 */

	/**
	 * 发送随感短信通知
	 */
	/*private void sendMomentNoticeRemind() {
		// 获取发送员工信息
		List<OrderNoticeReceiver> orderNoticeReceivers = orderNoticeReceiverService
				.getValidReceiver(NoticeReceiverType.MOMENT);
		// 判断是否有接收员工
		if (orderNoticeReceivers == null || orderNoticeReceivers.isEmpty()) {
			return;
		}
		// 获取需要发送的随感信息
		List<Moment> momentList = momentService
				.getMomentBycheckStatus(Code.STATUS_INVALID);
		if (momentList == null || momentList.isEmpty()) return;
		String content = MessageFormat.format("您有{0}条随感等待您的审核", momentList.size());

		for (OrderNoticeReceiver orderNoticeReceiver : orderNoticeReceivers) {
			if (!checkTime(orderNoticeReceiver.getReceiveStartTime(), orderNoticeReceiver.getReceiveEndTime())) continue;
			if (StringUtils.isBlank(orderNoticeReceiver.getUserCode())) continue;
			User user = userService.getByUserCode(orderNoticeReceiver.getUserCode());
			if (user == null) continue;
			//发送短信消息
			MessageVO vo = new MessageVO();
			vo.setPushType(PushConstants.PUSH_TYPE_SINGLE);
			vo.setType(MessageType.MOMENT_CHECK_NOTICE.getCode()); //随感未审核短信通知
			vo.setContent(content);
			vo.setPhone(orderNoticeReceiver.getPhone());
			vo.setUserId(user.getId());
			messageService.save(vo);
		}
	}*/

	/**
	 * 获取执行时间
	 * @param type
	 * @return
     */
	private Date getExecuteTime(String type){
		Date executeTime = null;
		if("moment".equals(type)){
			// 读取发送间隔时间单位分钟
			Integer IntervalSecond =  60 * 10;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.SECOND, IntervalSecond * 60);
			executeTime = calendar.getTime();
		}
		return executeTime;
	}


	/**
	 * 检查是否在发送时间内
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private boolean checkTime(Date startTime, Date endTime) {
		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.setTime(new Date());
		// 开始时间
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startTime);
		startCalendar.set(Calendar.YEAR, nowCalendar.get(Calendar.YEAR));
		startCalendar.set(Calendar.MONTH, nowCalendar.get(Calendar.MONTH));
		startCalendar.set(Calendar.DAY_OF_MONTH,
				nowCalendar.get(Calendar.DAY_OF_MONTH));
		// 结束时间
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endTime);
		endCalendar.set(Calendar.YEAR, nowCalendar.get(Calendar.YEAR));
		endCalendar.set(Calendar.MONTH, nowCalendar.get(Calendar.MONTH));
		endCalendar.set(Calendar.DAY_OF_MONTH,
				nowCalendar.get(Calendar.DAY_OF_MONTH));

		return (nowCalendar.before(endCalendar) && nowCalendar
				.after(startCalendar));
	}


	/**
	 * 获取后台token
	 * 
	 * @return
	 */
	private String getToken() {
		String serverUrl = StaticFileUtil.getProperty("systemConfig",
				"INTERFACE_SERVER");
		String url = serverUrl
				+ StaticFileUtil.getProperty("systemConfig", "CREATE_TOKEN");
		Map<String, String> tokenRequestMap = new HashMap<>();
		tokenRequestMap.put("source",
				StaticFileUtil.getProperty("systemConfig", "ADMIN_SOURCE"));
		JSONObject jsonObject = new JSONObject(HttpsUtils.post(url,
				tokenRequestMap));
		String token = String.valueOf(jsonObject.get("token"));
		return token;
	}

	public static void main(String[] args) throws ParseException {
		// Date now = new Date();
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(now);
		// int hour = calendar.get(Calendar.HOUR_OF_DAY);
		// int minute = calendar.get(Calendar.MINUTE);
		// System.out.println(hour);
		// System.out.println(minute);
		Date startTime = new SimpleDateFormat("HH:mm").parse("08:00");
		Date endTime = new SimpleDateFormat("HH:mm").parse("15:10");

		System.out.println(new Quartz().checkTime(startTime, endTime));

	}
}