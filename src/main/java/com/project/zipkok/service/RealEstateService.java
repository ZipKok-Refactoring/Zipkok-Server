package com.project.zipkok.service;

import com.project.zipkok.common.enums.RealEstateType;
import com.project.zipkok.common.enums.Role;
import com.project.zipkok.common.enums.TransactionType;
import com.project.zipkok.common.exception.RealEstateException;
import com.project.zipkok.common.exception.user.NoMatchUserException;
import com.project.zipkok.dto.*;
import com.project.zipkok.model.*;
import com.project.zipkok.repository.RealEstateRepository;
import com.project.zipkok.repository.UserRepository;
import com.project.zipkok.util.GeoLocationUtils;
import com.project.zipkok.util.jwt.JwtUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.project.zipkok.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RealEstateService {

    private final RealEstateRepository realEstateRepository;
    private final UserRepository userRepository;

    @Transactional
    public GetRealEstateResponse getRealEstateInfo(JwtUserDetails jwtUserDetail, Long realEstateId) {

        log.info("[RealEstateService.getRealEstateInfo]");

        RealEstate realEstate = realEstateRepository.findById(realEstateId.longValue())
                .orElseThrow(() -> new RealEstateException(INVALID_PROPERTY_ID));

        User user = userRepository.findByUserIdWithZimAndKok(jwtUserDetail.getUserId())
                .orElseThrow(() -> new NoMatchUserException(MEMBER_NOT_FOUND));

        List<String> realEstateImages = getAllImageUrlsFromRealEstate(realEstate);

        List<GetRealEstateResponse.RealEstateBriefInfo> neighborRealEstates = findNearbyRealEstates(realEstate.getLatitude(), realEstate.getLongitude(), 5)
                .stream()
                .map(GetRealEstateResponse.RealEstateBriefInfo::from)
                .toList();


        return GetRealEstateResponse.of(realEstate,
                user.getZims().stream().map(Zim::getRealEstate).collect(Collectors.toSet()).contains(realEstate),
                user.getKoks().stream().map(Kok::getRealEstate).collect(Collectors.toSet()).contains(realEstate),
                GetRealEstateResponse.ImageInfo.from(realEstateImages),
                neighborRealEstates);
    }

    private static List<String> getAllImageUrlsFromRealEstate(RealEstate realEstate) {
        List<String> realEstateImages = new ArrayList<>();

        // 대표 이미지
        realEstateImages.add(realEstate.getImageUrl());

        // 부가 이미지
        realEstate.getRealEstateImages()
                .stream()
                .map(RealEstateImage::getImageUrl)
                .forEach(realEstateImages::add);

        return realEstateImages;
    }

    public PostRealEstateResponse registerRealEstate(JwtUserDetails jwtUserDetail, PostRealEstateRequest postRealEstateRequest) {

            RealEstate realEstate = RealEstate.from(jwtUserDetail.getUserId(), postRealEstateRequest);

            Long realEstateId = realEstateRepository.save(realEstate).getRealEstateId();

            return new PostRealEstateResponse(realEstateId);

    }

    public GetMapRealEstateResponse getRealEstate(JwtUserDetails jwtUserDetail, GetRealEstateOnMapRequest getRealEstateOnMapRequest) {

        log.info("{UserService.getRealEstate} userId: {}, role: {}", jwtUserDetail.getUserId(), jwtUserDetail.getRole());

        List<RealEstate> realEstateList = this.realEstateRepository.findByLatitudeBetweenAndLongitudeBetween(getRealEstateOnMapRequest.getSouthWestLat(),getRealEstateOnMapRequest.getNorthEastLat(),getRealEstateOnMapRequest.getSouthWestLon(),getRealEstateOnMapRequest.getNorthEastLon());

        if(realEstateList == null){
            throw new RealEstateException(PROPERTY_NOT_FOUND);
        }

        TransactionType userTransactionType;
        RealEstateType userRealEstateType;

        if(jwtUserDetail.getRole().equals(Role.GUEST)) {
            userTransactionType = getRealEstateOnMapRequest.getTransactionType();
            userRealEstateType = getRealEstateOnMapRequest.getRealEstateType();

            GetTempRealEstateResponse getTempRealEstateResponse = new GetTempRealEstateResponse();

            getTempRealEstateResponse.setFilter(GetTempRealEstateResponse.Filter.builder()
                    .transactionType(userTransactionType)
                    .realEstateType(userRealEstateType)
                    .depositMin(getRealEstateOnMapRequest.getDepositMin())
                    .depositMax(getRealEstateOnMapRequest.getDepositMax())
                    .priceMin(getRealEstateOnMapRequest.getPriceMin())
                    .priceMax(getRealEstateOnMapRequest.getPriceMax())
                    .build()
            );

            if(userTransactionType != null && userRealEstateType != null){
                
                realEstateList = realEstateList
                                    .stream()
                                    .filter(result -> result.getTransactionType().equals(userTransactionType) && result.getRealEstateType().equals(userRealEstateType))
                                    .filter(result -> filterPriceConfig(result, getTempRealEstateResponse.getFilter(), false))
                                    .toList();

            }


            List<GetTempRealEstateResponse.RealEstateInfo> realEstateInfoList = realEstateList
                    .stream()
                    .filter(result -> result.getUserId() == null)
                    .map(result -> GetTempRealEstateResponse.RealEstateInfo.builder()
                            .realEstateId(result.getRealEstateId())
                            .imageURL(result.getImageUrl())
                            .deposit(result.getDeposit())
                            .price(result.getPrice())
                            .transactionType(result.getTransactionType().toString())
                            .realEstateType(result.getRealEstateType().toString())
                            .address(result.getAddress())
                            .detailAddress(result.getDetailAddress())
                            .latitude(result.getLatitude())
                            .longitude(result.getLongitude())
                            .agent(result.getAgent() == null ? "직접 등록한 매물" : result.getAgent())
                            .isZimmed(false)
                            .isKokked(false)
                            .build())
                    .collect(Collectors.toList());

            getTempRealEstateResponse.setRealEstateInfoList(realEstateInfoList);
            return getTempRealEstateResponse;

        } else {

            User user = this.userRepository.findByUserIdWithZimAndKok(jwtUserDetail.getUserId())
                    .orElseThrow(() -> new NoMatchUserException(MEMBER_NOT_FOUND));

            GetLoginMapRealEstateResponse getLoginMapRealEstateResponse = new GetLoginMapRealEstateResponse();

            userTransactionType = user.getTransactionType();
            userRealEstateType = user.getRealEstateType();

            Set<RealEstate> zimmedRealEstates = user.getZims().stream()
                    .map(Zim::getRealEstate)
                    .collect(Collectors.toSet());

            Set<RealEstate> kokkedRealEstates = user.getKoks().stream()
                    .map(Kok::getRealEstate)
                    .collect(Collectors.toSet());


            //filter 정보 mapping
            getLoginMapRealEstateResponse.setFilter(GetLoginMapRealEstateResponse.Filter.builder()
                    .transactionType(userTransactionType)
                    .realEstateType(userRealEstateType)
                    .mdepositMin(user.getTransactionPriceConfig().getMDepositMin())
                    .mdepositMax(user.getTransactionPriceConfig().getMDepositMax())
                    .mpriceMin(user.getTransactionPriceConfig().getMPriceMin())
                    .mpriceMax(user.getTransactionPriceConfig().getMPriceMax())
                    .ydepositMin(user.getTransactionPriceConfig().getYDepositMin())
                    .ydepositMax(user.getTransactionPriceConfig().getYDepositMax())
                    .purchaseMin(user.getTransactionPriceConfig().getPurchaseMin())
                    .purchaseMax(user.getTransactionPriceConfig().getPurchaseMax())
                    .build()
            );

            if(userTransactionType != null && userRealEstateType != null){
                realEstateList = realEstateList
                        .stream()
                        .filter(result -> result.getTransactionType().equals(userTransactionType) && result.getRealEstateType().equals(userRealEstateType))
                        .filter(result -> filterPriceConfig(result, getLoginMapRealEstateResponse.getFilter(), true))
                        .toList();
            }

            //realEstateInfo mapping
            List<GetLoginMapRealEstateResponse.RealEstateInfo> realEstateInfoList = realEstateList
                    .stream()
                    .filter(result -> result.getUserId() == null || result.getUserId().equals(jwtUserDetail.getUserId()))
                    .map(result -> GetLoginMapRealEstateResponse.RealEstateInfo.builder()
                            .realEstateId(result.getRealEstateId())
                            .imageURL(result.getImageUrl())
                            .deposit(result.getDeposit())
                            .price(result.getPrice())
                            .transactionType(result.getTransactionType().toString())
                            .realEstateType(result.getRealEstateType().toString())
                            .address(result.getAddress())
                            .detailAddress(result.getDetailAddress())
                            .latitude(result.getLatitude())
                            .longitude(result.getLongitude())
                            .agent(result.getAgent() == null ? "직접 등록한 매물" : result.getAgent())
                            .isZimmed(zimmedRealEstates.contains(result))
                            .isKokked(kokkedRealEstates.contains(result))
                            .build())
                    .collect(Collectors.toList());

            getLoginMapRealEstateResponse.setRealEstateInfoList(realEstateInfoList);
            return getLoginMapRealEstateResponse;
        }
    }

    private boolean filterPriceConfig(RealEstate realEstate, GetMapRealEstateResponse.Filter filter, boolean isLogin){
        String transactionType = realEstate.getTransactionType().getDescription();
        long deposit = realEstate.getDeposit() * 10000;
        long price = realEstate.getPrice() * 10000;

        if(isLogin){
            GetLoginMapRealEstateResponse.Filter loginFilter = (GetLoginMapRealEstateResponse.Filter) filter;

            if(transactionType.equals("월세")){
                if(deposit < loginFilter.getMdepositMin() || deposit > loginFilter.getMdepositMax()){ return false; }
                if(price < loginFilter.getMpriceMin() || price > loginFilter.getMpriceMax()) { return false; }
            }else if(transactionType.equals("전세")) {
                if(deposit < loginFilter.getYdepositMin() || deposit > loginFilter.getYdepositMax()){ return false; }
            }else if(transactionType.equals("매매")){
                if(price < loginFilter.getPurchaseMin() || price > loginFilter.getPurchaseMax()) { return false; }
            }
        }
        else{
            GetTempRealEstateResponse.Filter loginFilter = (GetTempRealEstateResponse.Filter) filter;

            long depositMin = loginFilter.getDepositMin() != null ? loginFilter.getDepositMin() : 0L;
            long depositMax = loginFilter.getDepositMax() != null ? loginFilter.getDepositMax() : Long.MAX_VALUE;
            long priceMin = loginFilter.getPriceMin() != null ? loginFilter.getPriceMin() : 0L;
            long priceMax = loginFilter.getPriceMax() != null ? loginFilter.getPriceMax() : Long.MAX_VALUE;

            if(transactionType.equals("월세")){
                if(deposit < depositMin || deposit > depositMax){ return false; }
                if(price < priceMin || price > priceMax) { return false; }
            }else if(transactionType.equals("전세")) {
                if(deposit < depositMin || deposit > depositMax){ return false; }
            }else if(transactionType.equals("매매")){
                if(price < priceMin || price > priceMax) { return false; }
            }

        }


        return true;
    }

    private List<RealEstate> findNearbyRealEstates(double centerLat, double centerLon, int minResults) {
        double radiusInKm = 0.5;
        List<RealEstate> nearbyRealEstates;

        do {
            double[] bounds = GeoLocationUtils.getSquareBounds(centerLat, centerLon, radiusInKm);
            nearbyRealEstates = realEstateRepository.findTop5ByLatitudeBetweenAndLongitudeBetween(centerLat, centerLon, bounds[0], bounds[1], bounds[2], bounds[3]);
            radiusInKm += 0.1;
        } while (nearbyRealEstates.size() < minResults);

        return nearbyRealEstates;
    }
}
