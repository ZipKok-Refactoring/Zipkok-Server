package com.project.zipkok.realEstate.application;

import com.project.zipkok.common.enums.RealEstateType;
import com.project.zipkok.common.enums.TransactionType;
import com.project.zipkok.common.exception.RealEstateException;
import com.project.zipkok.dto.*;
import com.project.zipkok.model.RealEstate;
import com.project.zipkok.realEstate.fixture.RealEstateFixture;
import com.project.zipkok.repository.RealEstateRepository;
import com.project.zipkok.repository.UserRepository;
import com.project.zipkok.service.RealEstateService;
import com.project.zipkok.util.jwt.JwtUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static com.project.zipkok.realEstate.fixture.RealEstateFixture.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class RealEstateServiceTest {

    @InjectMocks
    private RealEstateService realEstateService;

    @Mock
    private RealEstateRepository realEstateRepository;

    @Mock
    private UserRepository userRepository;

    /**
     * getRealEstateInfo 내에서 사용되는 findNearbyRealEstates() 메서드가 근처 매물 5개를 채우지 못할 경우에도 정상 응답할 수 있도록 수정 필요
     */
    @Test
    @DisplayName("매물_상세정보_응답_성공_테스트")
    void getRealEstateInfoTest() {
        // given
        RealEstate resposeRealEstate = RealEstateFixture.makeTestRealEstateWithRealEstateImage(1L, TransactionType.MONTHLY, RealEstateType.ONEROOM, 1000L, 48L);
        RealEstate neighborRealEstate1 = RealEstateFixture.makeDummyRealEstate(2L);
        RealEstate neighborRealEstate2 = RealEstateFixture.makeDummyRealEstate(3L);
        RealEstate neighborRealEstate3 = RealEstateFixture.makeDummyRealEstate(4L);
        RealEstate neighborRealEstate4 = RealEstateFixture.makeDummyRealEstate(4L);
        RealEstate neighborRealEstate5 = RealEstateFixture.makeDummyRealEstate(5L);

        given(realEstateRepository.findById(resposeRealEstate.getRealEstateId().longValue()))
                .willReturn(Optional.of(resposeRealEstate));
        given(userRepository.findByUserIdWithZimAndKok(USER_USER.getUserId()))
                .willReturn(Optional.of(USER_USER));
        given(realEstateRepository.findTop5ByLatitudeBetweenAndLongitudeBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .willReturn(List.of(neighborRealEstate1, neighborRealEstate2, neighborRealEstate3, neighborRealEstate4, neighborRealEstate5));

        // when
        GetRealEstateResponse response = realEstateService.getRealEstateInfo(JwtUserDetails.from(USER_USER), resposeRealEstate.getRealEstateId());

        // then
        assertEquals(resposeRealEstate.getRealEstateId(), response.getRealEstateId());
        assertEquals(resposeRealEstate.getAddress(), response.getAddress());
        assertEquals(resposeRealEstate.getTransactionType().name(), response.getTransactionType());
        assertEquals(resposeRealEstate.getRealEstateType().name(), response.getRealEstateType());
        assertEquals(resposeRealEstate.getDeposit(), response.getDeposit());
        assertEquals(resposeRealEstate.getPrice(), response.getPrice());
        assertEquals(resposeRealEstate.getAdministrativeFee(), response.getAdministrativeFee());
        assertEquals(resposeRealEstate.getLatitude(), response.getLatitude());
        assertEquals(resposeRealEstate.getLongitude(), response.getLongitude());

        assertEquals(2, response.getImageInfo().getImageNumber());
        assertEquals(5, response.getNeighborRealEstates().size());
    }

    @Test
    @DisplayName("매물_상세정보_매물이_없는 경우_테스트")
    void getRealEstateInfoTest2() {
        //given
        RealEstate notExistRealEstate = RealEstateFixture.makeDummyRealEstate(1L);

        given(realEstateRepository.findById(notExistRealEstate.getRealEstateId().longValue())).willReturn(Optional.empty());

        //then
        assertThrows(RealEstateException.class, () -> realEstateService.getRealEstateInfo(JwtUserDetails.from(USER_USER), notExistRealEstate.getRealEstateId()));
    }

    @Test
    @DisplayName("매물_직접_등록_응답_성공_테스트")
    void registerRealEstateTest() {
        //given
        PostRealEstateRequest postRealEstateRequest = PostRealEstateRequest.builder()
                .realEstateName("자취방1")
                .transactionType(TransactionType.MONTHLY.name())
                .realEstateType(RealEstateType.ONEROOM.name())
                .deposit(10000000L)
                .price(400000L)
                .administrativeFee(10000)
                .address("서울특별시 광진구 자양로 23나길 24")
                .detailAddress("202호")
                .latitude(1.0)
                .longitude(1.0)
                .pyeongsu(12)
                .floorNum(2)
                .build();

        RealEstate realEstate = RealEstate.from(USER_USER.getUserId(), postRealEstateRequest);
        ReflectionTestUtils.setField(realEstate, "realEstateId", 10L);

        given(realEstateRepository.save(any())).willReturn(realEstate);

        //when
        PostRealEstateResponse response = realEstateService.registerRealEstate(JwtUserDetails.from(USER_USER), postRealEstateRequest);

        //then
        assertEquals(10L, response.getRealEstateId());
    }

    @Test
    @DisplayName("매물_직접_등록_실패_테스트")
    void registerRealEstateTest2() {
        //given
        PostRealEstateRequest postRealEstateRequest = PostRealEstateRequest.builder()
                .realEstateName("자취방1")
                .transactionType(TransactionType.MONTHLY.name())
                .realEstateType(RealEstateType.ONEROOM.name())
                .deposit(10000000L)
                .price(400000L)
                .administrativeFee(10000)
                .address("서울특별시 광진구 자양로 23나길 24")
                .detailAddress("202호")
                .latitude(1.0)
                .longitude(1.0)
                .pyeongsu(12)
                .floorNum(2)
                .build();

        given(realEstateRepository.save(any())).willThrow(new DataAccessException("DB error") { });

        //then
        assertThrows(DataAccessException.class, () -> realEstateService.registerRealEstate(JwtUserDetails.from(USER_USER), postRealEstateRequest));
    }

    @Test
    @DisplayName("로그인_유저의_지도에_띄울_매물_응답_테스트")
    void getRealEstateTest() {
        //given
        RealEstate responseRealEstate1 = RealEstateFixture
                .makeTestRealEstateWithLatLon(1L, TransactionType.MONTHLY, RealEstateType.ONEROOM, 1000L, 48L, 1.0, 1.0);
        RealEstate responseRealEstate2 = RealEstateFixture
                .makeTestRealEstateWithLatLon(2L, TransactionType.MONTHLY, RealEstateType.ONEROOM, 1000000L, 48000L, 1.0, 1.0);
        RealEstate responseRealEstate3 = RealEstateFixture
                .makeTestRealEstateWithLatLon(3L, TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 48L, 1.0, 1.0);
        RealEstate responseRealEstate4 = RealEstateFixture
                .makeTestRealEstateWithLatLon(4L, TransactionType.YEARLY, RealEstateType.ONEROOM, 1000L, 48L, 1.0, 1.0);

        GetRealEstateOnMapRequest request = GetRealEstateOnMapRequest.builder()
                .southWestLat(0.0)
                .southWestLon(0.0)
                .northEastLat(10.0)
                .northEastLon(10.0)
                .build();

        given(realEstateRepository.findByLatitudeBetweenAndLongitudeBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .willReturn(List.of(responseRealEstate1, responseRealEstate2, responseRealEstate3, responseRealEstate4));

        given(userRepository.findByUserIdWithZimAndKok(USER_USER.getUserId())).willReturn(Optional.of(USER_USER));

        //when
        GetLoginMapRealEstateResponse response = (GetLoginMapRealEstateResponse) realEstateService.getRealEstate(JwtUserDetails.from(USER_USER), request);

        //then

        //회원의 온보딩 설정이 MONTHLY, ONEROOM이고, responseRealEstate2 가격 범위가 회원의 설정을 벗어나 1개만 응답해야 함
        assertEquals(1, response.getRealEstateInfoList().size());
    }

    @Test
    @DisplayName("비로그인_유저의_지도에_띄울_매물_응답_테스트")
    void getRealEstateTest2() {
        //given
        RealEstate responseRealEstate1 = RealEstateFixture
                .makeTestRealEstateWithLatLon(1L, TransactionType.MONTHLY, RealEstateType.ONEROOM, 1000L, 48L, 1.0, 1.0);
        RealEstate responseRealEstate2 = RealEstateFixture
                .makeTestRealEstateWithLatLon(2L, TransactionType.MONTHLY, RealEstateType.ONEROOM, 1000000L, 48000L, 11.0, 11.0);
        RealEstate responseRealEstate3 = RealEstateFixture
                .makeTestRealEstateWithLatLon(3L, TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 48L, 1.0, 1.0);
        RealEstate responseRealEstate4 = RealEstateFixture
                .makeTestRealEstateWithLatLon(4L, TransactionType.YEARLY, RealEstateType.ONEROOM, 1000L, 48L, 1.0, 1.0);


        GetRealEstateOnMapRequest request = GetRealEstateOnMapRequest.builder()
                .southWestLat(0.0)
                .southWestLon(0.0)
                .northEastLat(10.0)
                .northEastLon(10.0)
                .transactionType(TransactionType.MONTHLY)
                .realEstateType(RealEstateType.ONEROOM)
                .depositMin(0L)
                .depositMax(20000000L)
                .priceMin(0L)
                .priceMax(500000L)
                .build();

        given(realEstateRepository.findByLatitudeBetweenAndLongitudeBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .willReturn(List.of(responseRealEstate1, responseRealEstate2, responseRealEstate3, responseRealEstate4));

        //when
        GetTempRealEstateResponse response = (GetTempRealEstateResponse) realEstateService.getRealEstate(JwtUserDetails.makeGuestJwtDetails(), request);

        //then

        //비로그인 유저의 필터에 따라 MONTHLY_ONEROOM_01 매물만 응답해야 함
        assertEquals(1, response.getRealEstateInfoList().size());
    }

    /**
     * min, max 값을 모두 입력하지 않았더라도 성공적으로 응답해야함
     */
    @Test
    @DisplayName("비로그인_유저가_필터_정보를_충분히_입력하지_않은_경우_테스트")
    void getRealEstateTest3() {

        //given
        RealEstate responseRealEstate1 = RealEstateFixture
                .makeTestRealEstateWithLatLon(1L, TransactionType.MONTHLY, RealEstateType.ONEROOM, 1000L, 48L, 1.0, 1.0);
        RealEstate responseRealEstate2 = RealEstateFixture
                .makeTestRealEstateWithLatLon(2L, TransactionType.MONTHLY, RealEstateType.ONEROOM, 1000000L, 48000L, 11.0, 11.0);
        RealEstate responseRealEstate3 = RealEstateFixture
                .makeTestRealEstateWithLatLon(3L, TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 48L, 1.0, 1.0);
        RealEstate responseRealEstate4 = RealEstateFixture
                .makeTestRealEstateWithLatLon(4L, TransactionType.YEARLY, RealEstateType.ONEROOM, 1000L, 48L, 1.0, 1.0);

        GetRealEstateOnMapRequest request = GetRealEstateOnMapRequest.builder()
                .southWestLat(0.0)
                .southWestLon(0.0)
                .northEastLat(10.0)
                .northEastLon(10.0)
                .transactionType(TransactionType.MONTHLY)
                .realEstateType(RealEstateType.ONEROOM)
                .depositMin(null)
                .depositMax(null)
                .priceMin(null)
                .priceMax(null)
                .build();

        given(realEstateRepository.findByLatitudeBetweenAndLongitudeBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .willReturn(List.of(responseRealEstate1, responseRealEstate2, responseRealEstate3, responseRealEstate4));

        //when
        GetTempRealEstateResponse response = (GetTempRealEstateResponse) realEstateService.getRealEstate(JwtUserDetails.makeGuestJwtDetails(), request);

        //then
        //보증금, 가격 정보가 요청에 포함되지 않았음으로 월세, 원룸에 해당하는 모든 매물을 응답해야 함
        assertEquals(2, response.getRealEstateInfoList().size());

    }

}
