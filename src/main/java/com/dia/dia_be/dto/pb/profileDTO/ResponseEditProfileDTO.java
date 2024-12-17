package com.dia.dia_be.dto.pb.profileDTO;

import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.dto.pb.HashtagDTO.ResponseHashtagDTO;
import com.dia.dia_be.global.S3.S3Url;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseEditProfileDTO {
    private List<ResponseHashtagDTO> HashtagList;
    private String introduce;
    private String imgUrl;

    // Entity -> DTO 변환
    public static ResponseEditProfileDTO from(Pb pb) {
        return ResponseEditProfileDTO.builder()
                .HashtagList(pb.getHashtag() != null
                        ? pb.getHashtag().stream()
                        .map(ResponseHashtagDTO::from)
                        .collect(Collectors.toList())
                        : null)
                .introduce(pb.getIntroduce())
                .imgUrl(S3Url.S3_URL+pb.getImageUrl())
                .build();
    }
}
