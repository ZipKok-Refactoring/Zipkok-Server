package com.project.zipkok.realEstate.application;

import com.project.zipkok.common.enums.RealEstateType;
import com.project.zipkok.common.enums.TransactionType;
import com.project.zipkok.common.exception.RealEstateException;
import com.project.zipkok.dto.*;
import com.project.zipkok.model.RealEstate;
import com.project.zipkok.repository.RealEstateRepository;
import com.project.zipkok.repository.UserRepository;
import com.project.zipkok.service.RealEstateService;
import com.project.zipkok.util.jwt.JwtUserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static com.project.zipkok.realEstate.fixture.RealEstateFixture.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class RealEstateServiceTest {

    @InjectMocks
    private RealEstateService realEstateService;

    @Mock
    private RealEstateRepository realEstateRepository;

    @Mock
    private UserRepository userRepository;

    /**
     * 추후 getRealEstateInfo 내의 getAllImageUrlsFromRealEstate, findNearbyRealEstates 메서드에 대한 테스트 분리 필요
     */
    @Test
    @DisplayName("매물_상세정보_응답_성공_테스트")
    void getRealEstateInfoTest() {
        // given
        given(realEstateRepository.findById(MONTHLY_ONEROOM_01.getRealEstateId().longValue())).willReturn(Optional.of(MONTHLY_ONEROOM_01));
        given(userRepository.findByUserIdWithZimAndKok(USER_USER.getUserId())).willReturn(Optional.of(USER_USER));
        given(realEstateRepository.findTop5ByLatitudeBetweenAndLongitudeBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .willReturn(List.of(MONTHLY_ONEROOM_01, MONTHLY_ONEROOM_02, MONTHLY_APARTMENT_01, YEARLY_ONEROOM_01, MONTHLY_ONEROOM_02));

        // when
        GetRealEstateResponse response = realEstateService.getRealEstateInfo(JwtUserDetails.from(USER_USER), MONTHLY_ONEROOM_01.getRealEstateId());

        // then
        assertEquals(MONTHLY_ONEROOM_01.getRealEstateId(), response.getRealEstateId());
        assertEquals(MONTHLY_ONEROOM_01.getAddress(), response.getAddress());
        assertEquals(MONTHLY_ONEROOM_01.getTransactionType().name(), response.getTransactionType());
        assertEquals(MONTHLY_ONEROOM_01.getRealEstateType().name(), response.getRealEstateType());
        assertEquals(MONTHLY_ONEROOM_01.getDeposit(), response.getDeposit());
        assertEquals(MONTHLY_ONEROOM_01.getPrice(), response.getPrice());
        assertEquals(MONTHLY_ONEROOM_01.getAdministrativeFee(), response.getAdministrativeFee());
        assertEquals(MONTHLY_ONEROOM_01.getLatitude(), response.getLatitude());
        assertEquals(MONTHLY_ONEROOM_01.getLongitude(), response.getLongitude());

        assertEquals(2, response.getImageInfo().getImageNumber());
        assertEquals(5, response.getNeighborRealEstates().size());
    }

    @Test
    @DisplayName("매물_상세정보_매물이_없는 경우_테스트")
    void getRealEstateInfoTest2() {
        //given
        given(realEstateRepository.findById(MONTHLY_ONEROOM_01.getRealEstateId().longValue())).willReturn(Optional.empty());

        //then
        assertThrows(RealEstateException.class, () -> realEstateService.getRealEstateInfo(JwtUserDetails.from(USER_USER), MONTHLY_ONEROOM_01.getRealEstateId()));
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

    /**
     * DB에서 발생하는 예외에 대해 전반적 예외 핸들링 논의 후 추가 예정
     */
    @Test
    @DisplayName("매물_직접_등록_실패_테스트")
    void registerRealEstateTest2() {
        //given
        //when
        //then
    }

    @Test
    @DisplayName("로그인_유저의_지도에_띄울_매물_응답_테스트")
    void getRealEstateTest() {
        //given
        GetRealEstateOnMapRequest request = GetRealEstateOnMapRequest.builder()
                .southWestLat(0.0)
                .southWestLon(0.0)
                .northEastLat(10.0)
                .northEastLon(10.0)
                .build();

        given(realEstateRepository.findByLatitudeBetweenAndLongitudeBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .willReturn(List.of(MONTHLY_ONEROOM_01, MONTHLY_ONEROOM_02, MONTHLY_APARTMENT_01, YEARLY_ONEROOM_01));

        given(userRepository.findByUserIdWithZimAndKok(USER_USER.getUserId())).willReturn(Optional.of(USER_USER));

        //when
        GetLoginMapRealEstateResponse response = (GetLoginMapRealEstateResponse) realEstateService.getRealEstate(JwtUserDetails.from(USER_USER), request);

        //then

        //회원의 온보딩 설정이 MONTHLY, ONEROOM이고, ONEROOM_02의 가격 범위가 회원의 설정을 벗어나 1개만 응답해야 함
        assertEquals(1, response.getRealEstateInfoList().size());
    }

    @Test
    @DisplayName("비로그인_유저의_지도에_띄울_매물_응답_테스트")
    void getRealEstateTest2() {
        //given
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
                .willReturn(List.of(MONTHLY_ONEROOM_01, MONTHLY_ONEROOM_02, MONTHLY_APARTMENT_01, YEARLY_ONEROOM_01));

        //when
        GetTempRealEstateResponse response = (GetTempRealEstateResponse) realEstateService.getRealEstate(JwtUserDetails.makeGuestJwtDetails(), request);

        //then

        //비로그인 유저의 필터에 따라 MONTHLY_ONEROOM_01 매물만 응답해야 함
        assertEquals(1, response.getRealEstateInfoList().size());
    }

    /**
     * 추후 비로그인 유저가 제공한 필터 정보가 충분하지 않은 경우에 대한 예외 처리 추가 필요
     */
    @Test
    @DisplayName("비로그인_유저가_필터_정보를_입력하지_않은_경우_테스트")
    void getRealEstateTest3() {

    }

}
