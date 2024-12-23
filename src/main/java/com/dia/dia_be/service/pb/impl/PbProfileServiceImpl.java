package com.dia.dia_be.service.pb.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Hashtag;
import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.dto.pb.availabilityDTO.RequestAvailabilityDTO;
import com.dia.dia_be.dto.pb.profileDTO.ResponseEditProfileDTO;
import com.dia.dia_be.dto.pb.profileDTO.ResponseProfileDTO;
import com.dia.dia_be.exception.CommonErrorCode;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.global.S3.S3Url;
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

		List<Hashtag> existingHashtags = hashtagRepository.findAllByPb(pb);

		Map<String, Hashtag> existingHashtagMap = new HashMap<>();
		for (Hashtag tag : existingHashtags) {
			existingHashtagMap.put(tag.getName(), tag);
		}

		Set<String> newHashtagSet = new HashSet<>(hashtags);

		for (Map.Entry<String, Hashtag> entry : existingHashtagMap.entrySet()) {
			String oldName = entry.getKey();
			if (!newHashtagSet.contains(oldName)) {
				//기존 해시태그이므로 삭제
				hashtagRepository.delete(entry.getValue());
			}
		}

		for (String newName : newHashtagSet) {
			if (!existingHashtagMap.containsKey(newName)) {
				//기존에 없던 해시태그 추가
				Hashtag newHashtag = Hashtag.create(pb, newName);
				hashtagRepository.save(newHashtag);
			}
		}
		Pb updatedPb = pb.update(imgUrl == null ? pb.getImageUrl() : S3Url.S3_URL + imgUrl, introduce);
		pbRepository.save(updatedPb);
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
