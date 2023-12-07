package capstone.bookdiary.service;

import capstone.bookdiary.domain.dto.ImageRequestDto;
import capstone.bookdiary.domain.dto.ImageResponseDto;
import capstone.bookdiary.domain.entity.Image;
import capstone.bookdiary.domain.entity.Scrap;
import capstone.bookdiary.exception.exceptions.DataNotFoundException;
import capstone.bookdiary.feign.ImageClient;
import capstone.bookdiary.repository.ImageRepository;
import capstone.bookdiary.repository.ScrapRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageClient imageClient;
    private final ImageRepository imageRepository;
    private final ScrapRepository scrapRepository;

    public Map<String, Object> generateImage(ImageRequestDto imageRequestDto) {
        Map<String, String> data = new HashMap<>();
        String messageString = imageRequestDto.getContent() + " " + imageRequestDto.getMemo();
        data.put("message", messageString);

        //AI로 전송 using FeignClient
        ImageResponseDto imageResponseDto = imageClient.getImageUrl(data);
        String imageUrl = imageResponseDto.getBody().substring(1, imageResponseDto.getBody().length() - 1);

        Scrap scrap = scrapRepository.findById(imageRequestDto.getScrapId())
                .orElseThrow(DataNotFoundException::new);
        Image savedImage = imageRepository.save(new Image(scrap, imageUrl));

        //return: DB에 저장된 ImageId
        Map<String, Object> response = new HashMap<>();
        response.put("imageId", savedImage.getImageId());
        return response;
    }
}
