package com.project.zipkok.kok.service;

import com.project.zipkok.dto.*;
import com.project.zipkok.model.Kok;
import com.project.zipkok.model.Star;
import com.project.zipkok.repository.*;
import com.project.zipkok.service.KokService;
import com.project.zipkok.util.jwt.JwtUserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.project.zipkok.common.enums.Role.GUEST;
import static com.project.zipkok.kok.fixture.CheckedFixture.*;
import static com.project.zipkok.kok.fixture.KokFixture.KOK_01;
import static com.project.zipkok.kok.fixture.RealEstateFixture.DUMMY_REALESTATE;
import static com.project.zipkok.kok.fixture.StarFixture.DUMMY_STAR;
import static com.project.zipkok.kok.fixture.UserFixture.DUMMY_USER;
import static com.project.zipkok.kok.response.MakeTestKokResponse.makePostOrPutKokRequest;
import static com.project.zipkok.kok.service.KokServiceResponseMatcher.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@DisplayName("KokService 테스트")
@ExtendWith(MockitoExtension.class)
public class KokServiceTest {

    @Mock
    private KokRepository kokRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FurnitureOptionRepository furnitureOptionRepository;
    @Mock
    private RealEstateRepository realEstateRepository;
    @Mock
    private StarRepository starRepository;
    @Mock
    private OptionRepository optionRepository;
    @Mock
    private DetailOptionRepository detailOptionRepository;

    @InjectMocks
    private KokService kokService;

    private JwtUserDetails userDetails = new JwtUserDetails(DUMMY_USER.getUserId(), GUEST);

    @DisplayName("콕 무한페이징 service 성공")
    @Test
    void 콕_무한페이징_DTO를_반환한다() throws Exception {
        //given
        GetKokWithZimStatus getKokWithZimStatus = new GetKokWithZimStatus(KOK_01, true);
        Pageable pageable = PageRequest.of(0, 10);

        given(kokRepository.getKokWithZimStatus(eq(DUMMY_USER.getUserId()), eq(pageable))).willReturn(List.of(getKokWithZimStatus));

        //when
        GetKokResponse response = kokService.getKoks(userDetails, pageable);

        //then
        expectedServiceGetKokResponse(response);
    }

    @DisplayName("콕 세부 정보 반환 service 성공")
    @Test
    void 콕_세부정보_반환() throws Exception {
        //given
        given(userRepository.findByUserId(DUMMY_USER.getUserId())).willReturn(DUMMY_USER);
        given(kokRepository.findById(KOK_01.getKokId())).willReturn(Optional.of(KOK_01));

        //when
        GetKokDetailResponse response = kokService.getKokDetail(userDetails, KOK_01.getKokId());

        //then
        expectedServiceGetKokDetailResponse(response);
    }

    @DisplayName("콕 집 주변 정보 service 성공")
    @Test
    void 콕_집_주변_정보_반환() throws Exception {
        //given
        given(userRepository.findByUserId(DUMMY_USER.getUserId())).willReturn(DUMMY_USER);
        given(kokRepository.findKokWithCheckedOptionAndCheckedDetailOption(KOK_01.getKokId())).willReturn(KOK_01);

        //when
        GetKokOuterInfoResponse response = kokService.getKokOuterInfo(userDetails, KOK_01.getKokId());

        //then
        expectedServiceGetKokOuterInfoResponse(response);
    }

    @DisplayName("콕 집 내부 정보 service 성공")
    @Test
    void 콕_집_내부_정보_반환() throws Exception {
        //given
        given(userRepository.findByUserId(DUMMY_USER.getUserId())).willReturn(DUMMY_USER);
        given(kokRepository.findKokWithCheckedOptionAndCheckedDetailOption(KOK_01.getKokId())).willReturn(KOK_01);

        //when
        GetKokInnerInfoResponse response = kokService.getKokInnerInfo(userDetails, KOK_01.getKokId());

        //then
        expectedServiceGetKokInnerInfoResponse(response);
    }

    @DisplayName("콕 집 중개/계약 정보 service 성공")
    @Test
    void 콕_집_중개_계약_정보_반환() throws Exception {
        //given
        given(userRepository.findByUserId(DUMMY_USER.getUserId())).willReturn(DUMMY_USER);
        given(kokRepository.findKokWithCheckedOptionAndCheckedDetailOption(KOK_01.getKokId())).willReturn(KOK_01);

        //when
        GetKokContractResponse response = kokService.getKokContractInfo(userDetails, KOK_01.getKokId());

        //then
        expectedServiceGetKokContractInfoResponse(response);
    }

    @DisplayName("콕 집 리뷰 정보 service 성공")
    @Test
    void 콕_집_리뷰_정보_반환() throws Exception {
        //given
        given(userRepository.findByUserId(DUMMY_USER.getUserId())).willReturn(DUMMY_USER);
        given(kokRepository.findKokWithImpressionAndStar(KOK_01.getKokId())).willReturn(KOK_01);

        //when
        GetKokReviewInfoResponse response = kokService.getKokReviewInfo(userDetails, KOK_01.getKokId());

        //then
        expectedServiceGetKokReviewInfoResponse(response);
    }

    @DisplayName("콕 설정 정보 service 성공")
    @Test
    void 콕_설정_정보_반환() throws Exception {
        //given
        given(userRepository.findByUserId(DUMMY_USER.getUserId())).willReturn(DUMMY_USER);
        given(kokRepository.findByKokId(KOK_01.getKokId())).willReturn(KOK_01);
        given(furnitureOptionRepository.findAll()).willReturn(List.of(DUMMY_FURNITURE_OPTION));

        //when
        GetKokConfigInfoResponse response = kokService.getKokConfigInfo(userDetails, KOK_01.getKokId());

        //then
        expectedServiceGetKokConfigInfoResponse(response);
    }

    @DisplayName("콕 생성 service 성공")
    @Test
    void 콕_생성(){
        //given
        givenForCreateOrUpdateKok();
        PostOrPutKokRequest request = makePostOrPutKokRequest();

        //when
        PostOrPutKokResponse response = kokService.createOrUpdateKok(userDetails, null, request);

        //then
        expectedServicePostOrPutKokResponse(response);
    }


    private void givenForCreateOrUpdateKok(){
        given(kokRepository.save(any(Kok.class))).willReturn(KOK_01);
        given(userRepository.findByUserIdWithZimAndKok(DUMMY_USER.getUserId())).willReturn(Optional.of(DUMMY_USER));
        given(realEstateRepository.findById(DUMMY_REALESTATE.getRealEstateId())).willReturn(Optional.of(DUMMY_REALESTATE));
        given(kokRepository.findById(KOK_01.getKokId())).willReturn(Optional.of(KOK_01));
        given(starRepository.save(any(Star.class))).willReturn(DUMMY_STAR);
        given(furnitureOptionRepository.findAll()).willReturn(List.of(DUMMY_FURNITURE_OPTION));
        given(optionRepository.findAll()).willReturn(List.of(DUMMY_OPTION_OUTER, DUMMY_OPTION_INNER, DUMMY_OPTION_CONTRACT));
        given(detailOptionRepository.findAll()).willReturn(List.of(DUMMY_DETAIL_OPTION_OUTER, DUMMY_DETAIL_OPTION_INNER, DUMMY_DETAIL_OPTION_CONTRACT));
    }
}
