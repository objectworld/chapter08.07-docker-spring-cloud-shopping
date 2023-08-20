package org.objectworld.shopping.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.objectworld.shopping.common.dto.ReviewDto;
import org.objectworld.shopping.common.util.ObjectMapper;
import org.objectworld.shopping.product.domain.Review;
import org.objectworld.shopping.product.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public static ReviewDto mapToDto(Review review) {
    	return ObjectMapper.map(review, ReviewDto.class);
    }

    public Review createFromDto(ReviewDto reviewDto) {
        return ObjectMapper.map(reviewDto, Review.class);
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> findAll() {
        log.debug("Request to get all Reviews");
        return this.reviewRepository.findAll()
                .stream()
                .map(ReviewService::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReviewDto findById(Long id) {
        log.debug("Request to get Review : {}", id);
        return this.reviewRepository.findById(id)
        	.map(ReviewService::mapToDto).orElse(null);
    }

    public ReviewDto create(ReviewDto reviewDto) {
        log.debug("Request to create Review : {}", reviewDto);    	
        return mapToDto(this.reviewRepository.save(createFromDto(reviewDto)));
    }

    public void delete(Long id) {
        log.debug("Request to delete Review : {}", id);
        this.reviewRepository.deleteById(id);
    }
}
