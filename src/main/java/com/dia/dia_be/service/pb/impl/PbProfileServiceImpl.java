package com.dia.dia_be.service.pb.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Hashtag;
import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.dto.pb.availabilityDTO.RequestAvailabilityDTO;
import com.dia.dia_be.dto.pb.profileDTO.ResponseEditProfileDTO;
import com.dia.dia_be.dto.pb.profileDTO.ResponseProfileDTO;
import com.dia.dia_be.exception.CommonErrorCode;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.repository.HashtagRepository;
import com.dia.dia_be.repository.PbRepository;
import com.dia.dia_be.service.pb.intf.PbProfileService;

@Service
public class PbProfileServiceImpl implements PbProfileService {
	private final PbRepository pbRepository;
	private final HashtagRepository hashtagRepository;

	public PbProfileServiceImpl(PbRepository pbRepository, HashtagRepository hashtagRepository) {
		this.pbRepository = pbRepository;
		this.hashtagRepository = hashtagRepository;
	}

	@Override
	public ResponseProfileDTO getProfile(Long pbId) {
		Pb pb = pbRepository.findById(pbId)
			.orElseThrow(() -> new GlobalException(PbErrorCode.PROFILE_NOT_FOUND));

		return ResponseProfileDTO.from(pb);
	}

	@Override
	public ResponseEditProfileDTO updateProfile(Long pbId, String introduce, String imgUrl, List<String> hashtags) {
		Pb pb = pbRepository.findById(pbId)
			.orElseThrow(() -> new GlobalException(PbErrorCode.PROFILE_NOT_FOUND));

		List<Hashtag> hashtagList = new LinkedList<>();
		for (String h : hashtags) {
			Hashtag hashtag = Hashtag.create(pb, h);
			Hashtag savedHashtag = hashtagRepository.save(hashtag);
			hashtagList.add(savedHashtag);
		}
		Pb updatedPb = pb.update(imgUrl == null ? pb.getImageUrl() : imgUrl, introduce);
		return ResponseEditProfileDTO.from(updatedPb);
	}

	@Override
	public Pb login(String id, String pw) {
		return pbRepository.findByLoginIdAndPassword(id, pw).get();
	}

	@Override
	public RequestAvailabilityDTO updateAvailability(RequestAvailabilityDTO requestAvailabilityDTO) {
		if (requestAvailabilityDTO == null) {
			throw new GlobalException(CommonErrorCode.REQUEST_NULL);
		}
		Pb pb = pbRepository.findById(requestAvailabilityDTO.getPbId())
			.orElseThrow(() -> new GlobalException(PbErrorCode.PROFILE_NOT_FOUND));
		pb.updateAvailability(requestAvailabilityDTO.isAvailability());
		pbRepository.save(pb);
		return RequestAvailabilityDTO.from(pb);
	}
}
