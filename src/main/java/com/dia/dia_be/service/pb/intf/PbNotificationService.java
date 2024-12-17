package com.dia.dia_be.service.pb.intf;

import java.util.List;

import com.dia.dia_be.dto.pb.NotificationDTO;

public interface PbNotificationService {
	List<NotificationDTO> getAllNotifications();

	NotificationDTO getNotificationById(Long id);

	List<NotificationDTO> getNotificationsByCustomerIds(List<Long> customerIds);

	List<NotificationDTO> sendNotifications(NotificationDTO notificationDTO);
}
