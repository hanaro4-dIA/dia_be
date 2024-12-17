package com.dia.dia_be.service.pb.intf;

import com.dia.dia_be.dto.pb.NotificationDTO;

import java.util.List;

public interface NotificationService {
	List<NotificationDTO> getAllNotifications();

	NotificationDTO getNotificationById(Long id);

	List<NotificationDTO> getNotificationsByCustomerIds(List<Long> customerIds);

	List<NotificationDTO> sendNotifications(NotificationDTO notificationDTO);
}
