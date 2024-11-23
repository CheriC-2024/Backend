package com.art.cheric.module.artist.service;


import com.art.cheric.module.artist.domain.entity.Artist;
import com.art.cheric.module.artist.domain.entity.ArtistDegree;
import com.art.cheric.module.artist.domain.repository.ArtistDegreeRepository;
import com.art.cheric.module.artist.dto.req.ArtistDegreeReqDto;
import com.art.cheric.module.artist.dto.res.ArtistDegreeResDto;
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
public class ArtistDegreeService {
    private final ArtistDegreeRepository artistDegreeRepository;

    @Transactional
    public void saveArtistDegree(List<ArtistDegreeReqDto> artistDegreeReqs, Artist artist) {
        List<ArtistDegree> artistDegrees = new ArrayList<>();
        for (ArtistDegreeReqDto artistDegreeReq : artistDegreeReqs) {
            artistDegrees.add(ArtistDegree.of(artist, artistDegreeReq.schoolName(), artistDegreeReq.major(),
                    artistDegreeReq.entranceAt(), artistDegreeReq.graduateAt()));
        }
        artistDegreeRepository.saveAll(artistDegrees);
    }

    public List<ArtistDegreeResDto> getArtistDegrees(Long artistId){
        List<ArtistDegree> degrees =  artistDegreeRepository.findAllByArtistId(artistId);

        return degrees.stream()
                .map(degree -> ArtistDegreeResDto.of(
                        degree.getName(),
                        degree.getMajor(),
                        degree.getEntranceAt(),
                        degree.getGraduateAt()
                ))
                .collect(Collectors.toList());
    }

}
