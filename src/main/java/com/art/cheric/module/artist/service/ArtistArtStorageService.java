package com.art.cheric.module.artist.service;


import com.art.cheric.module.artist.domain.entity.Artist;
import com.art.cheric.module.artist.domain.entity.ArtistArtStorage;
import com.art.cheric.module.artist.domain.repository.ArtistArtStorageRepository;
import com.art.cheric.module.artist.dto.req.ArtistArtStorageReqDto;
import com.art.cheric.module.artist.dto.res.ArtistArtStorageResDto;
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
public class ArtistArtStorageService {
    private final ArtistArtStorageRepository artistArtStorageRepository;

    @Transactional
    public void saveArtistArtStorage(List<ArtistArtStorageReqDto> artistArtStorageReqs, Artist artist) {
        List<ArtistArtStorage> artistArtStorages = new ArrayList<>();
        for (ArtistArtStorageReqDto artistArtStorage : artistArtStorageReqs) {
            artistArtStorages.add(ArtistArtStorage.of(artist, artistArtStorage.location()));
        }
        artistArtStorageRepository.saveAll(artistArtStorages);
    }

    public List<ArtistArtStorageResDto> getArtistArtStorages(Long artistId) {
        List<ArtistArtStorage> artStorages = artistArtStorageRepository.findAllByArtistId(artistId);

        return artStorages.stream()
                .map(artStorage -> ArtistArtStorageResDto.of(
                        artStorage.getLocation()
                ))
                .collect(Collectors.toList());
    }
}
