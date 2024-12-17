package com.dia.dia_be.service.pb.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Notification;
import com.dia.dia_be.dto.pb.NotificationDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.repository.CustomerRepository;
import com.dia.dia_be.repository.NotificationRepository;
import com.dia.dia_be.service.pb.intf.PbNotificationService;

@Service
public class PbNotificationServiceImpl implements PbNotificationService {

	private final NotificationRepository notificationRepository;
	private final CustomerRepository customerRepository;

	public PbNotificationServiceImpl(NotificationRepository notificationRepository,
		CustomerRepository customerRepository) {
		this.notificationRepository = notificationRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public List<NotificationDTO> getAllNotifications() {
		return notificationRepository.findAll().stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}

	@Override
	public NotificationDTO getNotificationById(Long id) {
		Notification notification = notificationRepository.findById(id)
			.orElseThrow(() -> new GlobalException(PbErrorCode.NOTIFICATION_NOT_FOUND));
		return convertToDTO(notification);
	}

	@Override
	public List<NotificationDTO> getNotificationsByCustomerIds(List<Long> customerIds) {
		List<Notification> notifications = notificationRepository.findByCustomerIdIn(customerIds);
		return notifications.stream()
			.map(notification -> new NotificationDTO(
				notification.getId(),
				Collections.singletonList(notification.getCustomer().getId()),  // List<Long> 형태로 수정
				notification.getTitle(),
				notification.getContent(),
				notification.getDate(),
				notification.isRead()
			))
			.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public List<NotificationDTO> sendNotifications(NotificationDTO notificationDTO) {
		List<Long> customerIds = notificationDTO.getCustomerIds();

		return customerIds.stream()
			.map(customerId -> {
				Customer customer = customerRepository.findById(customerId)
					.orElseThrow(() -> new GlobalException(PbErrorCode.CUSTOMER_NOT_FOUND));

				Notification notification = Notification.create(
					customer,
					notificationDTO.getTitle(),
					notificationDTO.getContent(),
					notificationDTO.getDate(),
					notificationDTO.isRead()
				);

				notificationRepository.save(notification);

				return convertToDTO(notification);
			})
			.collect(Collectors.toList());
	}

	private NotificationDTO convertToDTO(Notification notification) {
		return new NotificationDTO(
			notification.getId(),
			Collections.singletonList(notification.getCustomer().getId()),  // List<Long> 형태로 수정
			notification.getTitle(),
			notification.getContent(),
			notification.getDate(),
			notification.isRead()
		);
	}
}
