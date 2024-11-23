package com.art.cheric.module.artist.service;


import com.art.cheric.module.artist.domain.entity.Artist;
import com.art.cheric.module.artist.domain.entity.ArtistResidence;
import com.art.cheric.module.artist.domain.repository.ArtistResidenceRepository;
import com.art.cheric.module.artist.dto.req.ArtistResidenceReqDto;
import com.art.cheric.module.artist.dto.res.ArtistResidenceResDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ArtistResidenceService {
    private final ArtistResidenceRepository artistResidenceRepository;

    @Transactional
    public void saveArtistResidence(List<ArtistResidenceReqDto> artistResidenceReqs, Artist artist) {
        List<ArtistResidence> artistResidences = new ArrayList<>();
        for (ArtistResidenceReqDto artistResidenceReq : artistResidenceReqs) {
            artistResidences.add(ArtistResidence.of(artist, artistResidenceReq.residenceName()));
        }
        artistResidenceRepository.saveAll(artistResidences);
    }


    public List<ArtistResidenceResDto> getArtistResidences(Long artistId) {
        List<ArtistResidence> residences = artistResidenceRepository.findAllByArtistId(artistId);

        return residences.stream()
                .map(residence -> ArtistResidenceResDto.of(
                        residence.getName()
                ))
                .collect(Collectors.toList());
    }
}
