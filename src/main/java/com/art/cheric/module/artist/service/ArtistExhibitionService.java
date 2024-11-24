package com.art.cheric.module.artist.service;


import com.art.cheric.module.artist.domain.entity.Artist;
import com.art.cheric.module.artist.domain.entity.ArtistExhibition;
import com.art.cheric.module.artist.domain.repository.ArtistExhibitionRepository;
import com.art.cheric.module.artist.dto.req.ArtistExhibitionReqDto;
import com.art.cheric.module.artist.dto.res.ArtistExhibitionResDto;
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
public class ArtistExhibitionService {
    private final ArtistExhibitionRepository artistExhibitionRepository;

    @Transactional
    public void saveArtistExhibition(List<ArtistExhibitionReqDto> artistExhibitionReqs, Artist artist) {
        List<ArtistExhibition> artistExhibitions = new ArrayList<>();
        for (ArtistExhibitionReqDto artistExhibition : artistExhibitionReqs) {
            artistExhibitions.add(
                    ArtistExhibition.of(artist, artistExhibition.exhibitionName(), artistExhibition.location(),
                            artistExhibition.byWho(), artistExhibition.exhibitionType(), artistExhibition.openedAt()));
        }
        artistExhibitionRepository.saveAll(artistExhibitions);
    }

    public List<ArtistExhibitionResDto> getArtistExhibitions(Long artistId) {
        List<ArtistExhibition> exhibitions = artistExhibitionRepository.findAllByArtistId(artistId);

        return exhibitions.stream()
                .map(exhibition -> ArtistExhibitionResDto.of(
                        exhibition.getName(),
                        exhibition.getLocation(),
                        exhibition.getByWho(),
                        exhibition.getExhibitionType(),
                        exhibition.getOpenedAt()
                ))
                .collect(Collectors.toList());
    }
}
