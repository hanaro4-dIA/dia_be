package com.dia.dia_be.service.pb.intf;

import java.util.List;

import com.dia.dia_be.dto.pb.notificationDTO.RequestNotificationDTO;
import com.dia.dia_be.dto.pb.notificationDTO.ResponseNotificationDTO;

public interface PbNotificationService {
	List<ResponseNotificationDTO> getAllNotifications();

	ResponseNotificationDTO getNotificationById(Long id);

	List<ResponseNotificationDTO> getNotificationsByCustomerIds(List<Long> customerIds);

	List<ResponseNotificationDTO> sendNotifications(RequestNotificationDTO notificationDTO);
}
