package com.dia.dia_be.service.vip.intf;

import java.util.List;

import com.dia.dia_be.dto.vip.ResponseNotificationGetDTO;

public interface VipNotificationService {

	List<ResponseNotificationGetDTO> getNotifications(Long customerId);
	void deleteAllNotifications(Long customerId);
	void markAllNotificationsAsRead(Long customerId);

}
