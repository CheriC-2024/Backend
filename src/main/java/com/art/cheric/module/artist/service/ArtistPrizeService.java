package com.art.cheric.module.artist.service;


import com.art.cheric.module.artist.domain.entity.Artist;
import com.art.cheric.module.artist.domain.entity.ArtistPrize;
import com.art.cheric.module.artist.domain.repository.ArtistPrizeRepository;
import com.art.cheric.module.artist.dto.req.ArtistPrizeReqDto;
import com.art.cheric.module.artist.dto.res.ArtistPrizeResDto;
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
public class ArtistPrizeService {
    private final ArtistPrizeRepository artistPrizeRepository;

    @Transactional
    public void saveArtistPrize(List<ArtistPrizeReqDto> artistPrizeReqs, Artist artist) {
        List<ArtistPrize> artistPrizes = new ArrayList<>();
        for (ArtistPrizeReqDto artistPrizeReq : artistPrizeReqs) {
            artistPrizes.add(ArtistPrize.of(artist, artistPrizeReq.organization(), artistPrizeReq.level(),
                    artistPrizeReq.receivedAt()));
        }
        artistPrizeRepository.saveAll(artistPrizes);
    }

    public List<ArtistPrizeResDto> getArtistPrizes(Long artistId) {
        List<ArtistPrize> prizes = artistPrizeRepository.findAllByArtistId(artistId);

        return prizes.stream()
                .map(prize -> ArtistPrizeResDto.of(
                        prize.getOrganization(),
                        prize.getLevel(),
                        prize.getReceivedAt()
                ))
                .collect(Collectors.toList());
    }
}
