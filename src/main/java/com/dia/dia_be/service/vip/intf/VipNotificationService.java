package com.dia.dia_be.service.vip.intf;

import java.util.List;

import com.dia.dia_be.dto.vip.notificationDTO.ResponseNotificationDTO;

public interface VipNotificationService {

	List<ResponseNotificationDTO> getNotifications(Long customerId);

	void deleteAllNotifications(Long customerId);

	void markAllNotificationsAsRead(Long customerId);

}
