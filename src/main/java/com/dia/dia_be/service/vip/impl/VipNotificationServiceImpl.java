package com.dia.dia_be.service.vip.impl;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Notification;
import com.dia.dia_be.domain.QNotification;
import com.dia.dia_be.dto.vip.ResponseNotificationGetDTO;
import com.dia.dia_be.repository.NotificationRepository;
import com.dia.dia_be.service.vip.intf.VipNotificationService;

import jakarta.transaction.Transactional;

@Service
public class VipNotificationServiceImpl implements VipNotificationService {
	private final NotificationRepository notificationRepository;
	private final QNotification qNotification = QNotification.notification;

	public VipNotificationServiceImpl(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Override
	public List<ResponseNotificationGetDTO> getNotifications(Long customerId) {
		List<Notification> notifications = StreamSupport.stream(notificationRepository.findAll(
			qNotification.customer.id.eq(customerId)
		).spliterator(), false).toList();
		return notifications.stream().map(ResponseNotificationGetDTO::from).toList();
	}

	@Override
	@Transactional
	public void deleteAllNotifications(Long customerId) {
		notificationRepository.deleteByCustomerId(customerId);
	}

	@Override
	@Transactional
	public void markAllNotificationsAsRead(Long customerId) {
		List<Notification> notifications = notificationRepository.findByCustomerId(customerId);
		notifications.forEach(notification -> notification.update(true));
		notificationRepository.saveAll(notifications);
	}
}
