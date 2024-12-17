package com.dia.dia_be.service.pb.impl;

import java.util.LinkedList;
import java.util.List;
import com.dia.dia_be.dto.pb.profileDTO.ResponseEditProfileDTO;
import com.dia.dia_be.repository.HashtagRepository;
import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Hashtag;
import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.repository.PbRepository;
import com.dia.dia_be.service.pb.intf.ProfileServiceIntf;

@Service
public class ProfileServiceImpl implements ProfileServiceIntf {
	private final PbRepository pbRepository;
	private final HashtagRepository hashtagRepository;

	public ProfileServiceImpl(PbRepository pbRepository, HashtagRepository hashtagRepository) {
		this.pbRepository = pbRepository;
		this.hashtagRepository = hashtagRepository;
	}

	@Override
	public ResponseEditProfileDTO updateProfile(Long pbId, String introduce, String imgUrl, List<String> hashtags) {
		Pb pb = pbRepository.findById(pbId)
			.orElseThrow(() -> new GlobalException(PbErrorCode.PROFILE_NOT_FOUND));

		List<Hashtag> hashtagList = new LinkedList<>();
		for(String h : hashtags) {
			Hashtag hashtag = Hashtag.create(pb,h);
			Hashtag savedHashtag = hashtagRepository.save(hashtag);
			hashtagList.add(savedHashtag);
		}
		Pb updatedPb = pb.update(imgUrl==null?pb.getImageUrl():imgUrl, introduce);
		return ResponseEditProfileDTO.from(updatedPb);
	}
}