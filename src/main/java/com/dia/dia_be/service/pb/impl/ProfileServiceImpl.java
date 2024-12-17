package com.dia.dia_be.service.pb.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Hashtag;
import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.dto.pb.profileDTO.RequestProfileDTO;
import com.dia.dia_be.dto.pb.profileDTO.ResponseProfileDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.repository.PbRepository;
import com.dia.dia_be.service.pb.intf.ProfileServiceIntf;

@Service
public class ProfileServiceImpl implements ProfileServiceIntf {
	private final PbRepository pbRepository;

	public ProfileServiceImpl(PbRepository pbRepository) {
		this.pbRepository = pbRepository;
	}

	@Override
	public ResponseProfileDTO getProfile(Long pbId) {
		Pb pb = pbRepository.findById(pbId)
			.orElseThrow(() -> new GlobalException(PbErrorCode.PROFILE_NOT_FOUND));

		return ResponseProfileDTO.from(pb);
	}

	public RequestProfileDTO updateProfile(Long pbId, RequestProfileDTO profileDTO) {
		Pb pb = pbRepository.findById(pbId)
			.orElseThrow(() -> new GlobalException(PbErrorCode.PROFILE_NOT_FOUND));

		// 엔티티 필드 업데이트
		pb.update(profileDTO.getImageUrl(), profileDTO.getIntroduce());

		// 해시태그 업데이트 로직
		if (profileDTO.getHashTagList() != null) {
			List<Hashtag> existingHashtags = pb.getHashtag();

			profileDTO.getHashTagList().forEach(dto -> {
				if (dto.getId() != null) {
					existingHashtags.stream()
						.filter(hashtag -> hashtag.getId().equals(dto.getId()))
						.findFirst()
						.ifPresent(hashtag -> hashtag.update(dto.getName()));
				} else {
					Hashtag newHashtag = dto.toEntity(pb);
					existingHashtags.add(newHashtag);
				}
			});

		}

		pbRepository.save(pb);
		return profileDTO;
	}

}