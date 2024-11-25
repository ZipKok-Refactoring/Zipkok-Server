package com.project.zipkok.kok.service;

import com.project.zipkok.common.enums.RealEstateType;
import com.project.zipkok.common.enums.TransactionType;
import com.project.zipkok.dto.*;
import com.project.zipkok.model.*;
import com.project.zipkok.repository.*;
import com.project.zipkok.service.KokService;
import com.project.zipkok.util.FileUploadUtils;
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
import static com.project.zipkok.kok.response.MakeTestKokControllerResponse.makePostOrPutKokRequest;
import static com.project.zipkok.kok.response.MakeTestKokServiceResponse.getUser;
import static com.project.zipkok.kok.response.MakeTestKokServiceResponse.makeTestKok;
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
    @Mock
    private FileUploadUtils fileUploadUtils;

    @InjectMocks
    private KokService kokService;

    private JwtUserDetails userDetails = new JwtUserDetails("test", 1L, GUEST);

    @DisplayName("콕 무한페이징 service 성공")
    @Test
    void 콕_무한페이징_DTO를_반환한다() throws Exception {
        //given
        Kok kok = makeTestKok(
                1L, "test/test", "test","test","test", TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 10,
                1L, "test/test",
                1L,
                "testOption", true, 1,
                "testDetailOption", true,
                "테스트입니다"
        );
        User user = getUser(1L);
        GetKokWithZimStatus getKokWithZimStatus = new GetKokWithZimStatus(kok, true);
        Pageable pageable = PageRequest.of(0, 10);

        given(kokRepository.getKokWithZimStatus(eq(user.getUserId()), eq(pageable))).willReturn(List.of(getKokWithZimStatus));

        //when
        GetKokResponse response = kokService.getKoks(userDetails, pageable);

        //then
        expectedServiceGetKokResponse(response, kok);
    }

    @DisplayName("콕 세부 정보 반환 service 성공")
    @Test
    void 콕_세부정보_반환() throws Exception {
        //given
        Kok kok =makeTestKok(
                1L, "test/test", "test","test","test", TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 10,
                1L, "test/test",
                1L,
                "testOption", true, 1,
                "testDetailOption", true,
                "테스트입니다"
        );
        User user = kok.getUser();
        given(userRepository.findByUserId(user.getUserId())).willReturn(user);
        given(kokRepository.findById(kok.getKokId())).willReturn(Optional.of(kok));

        //when
        GetKokDetailResponse response = kokService.getKokDetail(userDetails, kok.getKokId());

        //then
        expectedServiceGetKokDetailResponse(response, kok);
    }

    @DisplayName("콕 집 주변 정보 service 성공")
    @Test
    void 콕_집_주변_정보_반환() throws Exception {
        //given
        Kok kok =makeTestKok(
                1L, "test/test", "test","test","test", TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 10,
                1L, "test/test",
                1L,
                "testOption", true, 1,
                "testDetailOption", true,
                "테스트입니다"
        );
        User user = kok.getUser();
        given(userRepository.findByUserId(user.getUserId())).willReturn(user);
        given(kokRepository.findKokWithCheckedOptionAndCheckedDetailOption(kok.getKokId())).willReturn(kok);

        //when
        GetKokOuterInfoResponse response = kokService.getKokOuterInfo(userDetails, kok.getKokId());

        //then
        expectedServiceGetKokOuterInfoResponse(response, kok);
    }

    @DisplayName("콕 집 내부 정보 service 성공")
    @Test
    void 콕_집_내부_정보_반환() throws Exception {
        //given
        Kok kok =makeTestKok(
                1L, "test/test", "test","test","test", TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 10,
                1L, "test/test",
                1L,
                "testOption", true, 1,
                "testDetailOption", true,
                "테스트입니다"
        );
        User user = kok.getUser();
        given(userRepository.findByUserId(user.getUserId())).willReturn(user);
        given(kokRepository.findKokWithCheckedOptionAndCheckedDetailOption(kok.getKokId())).willReturn(kok);

        //when
        GetKokInnerInfoResponse response = kokService.getKokInnerInfo(userDetails, kok.getKokId());

        //then
        expectedServiceGetKokInnerInfoResponse(response, kok);
    }

    @DisplayName("콕 집 중개/계약 정보 service 성공")
    @Test
    void 콕_집_중개_계약_정보_반환() throws Exception {
        //given
        Kok kok =makeTestKok(
                1L, "test/test", "test","test","test", TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 10,
                1L, "test/test",
                1L,
                "testOption", true, 1,
                "testDetailOption", true
                ,"테스트입니다"
        );
        User user = kok.getUser();
        given(userRepository.findByUserId(user.getUserId())).willReturn(user);
        given(kokRepository.findKokWithCheckedOptionAndCheckedDetailOption(kok.getKokId())).willReturn(kok);

        //when
        GetKokContractResponse response = kokService.getKokContractInfo(userDetails, kok.getKokId());

        //then
        expectedServiceGetKokContractInfoResponse(response, kok);
    }

    @DisplayName("콕 집 리뷰 정보 service 성공")
    @Test
    void 콕_집_리뷰_정보_반환() throws Exception {
        //given
        Kok kok =makeTestKok(
                1L, "test/test", "test","test","test", TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 10,
                1L, "test/test",
                1L,
                "testOption", true, 1,
                "testDetailOption", true,
                "테스트입니다"
        );
        User user = kok.getUser();
        given(userRepository.findByUserId(user.getUserId())).willReturn(user);
        given(kokRepository.findKokWithImpressionAndStar(kok.getKokId())).willReturn(kok);

        //when
        GetKokReviewInfoResponse response = kokService.getKokReviewInfo(userDetails, kok.getKokId());

        //then
        expectedServiceGetKokReviewInfoResponse(response);
    }

    @DisplayName("콕 설정 정보 service 성공")
    @Test
    void 콕_설정_정보_반환() throws Exception {
        //given
        Kok kok =makeTestKok(
                1L, "test/test", "test","test","test", TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 10,
                1L, "test/test",
                1L,
                "testOption", true, 1,
                "testDetailOption", true,
                "테스트입니다"
        );
        User user = kok.getUser();
        given(userRepository.findByUserId(user.getUserId())).willReturn(user);
        given(kokRepository.findByKokId(kok.getKokId())).willReturn(kok);
        given(furnitureOptionRepository.findAll()).willReturn(List.of(kok.getCheckedFurnitures().iterator().next().getFurnitureOption()));

        //when
        GetKokConfigInfoResponse response = kokService.getKokConfigInfo(userDetails, kok.getKokId());

        //then
        expectedServiceGetKokConfigInfoResponse(response, kok);
    }

    @DisplayName("콕 생성 service 성공")
    @Test
    void 콕_생성(){
        //given
        Kok kok = givenForCreateOrUpdateKok();
        PostOrPutKokRequest request = makePostOrPutKokRequest();
        given(fileUploadUtils.deleteFile(any())).willReturn(null);

        //when
        PostOrPutKokResponse response = kokService.createOrUpdateKok(userDetails, null, request);

        //then
        expectedServicePostOrPutKokResponse(response, kok);
    }


    private Kok givenForCreateOrUpdateKok(){
        Kok kok =makeTestKok(
                1L, "test/test", "test","test","test", TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 10,
                1L, "http://test/test",
                1L,
                "testOption", true, 1,
                "testDetailOption", true,
                "테스트입니다"
        );
        User user = kok.getUser();
        given(kokRepository.save(any(Kok.class))).willReturn(kok);
        given(userRepository.findByUserIdWithZimAndKok(user.getUserId())).willReturn(Optional.of(user));
//        given(realEstateRepository.findById(kok.getRealEstate().getRealEstateId())).willReturn(Optional.of(kok.getRealEstate()));
        given(kokRepository.findById(kok.getKokId())).willReturn(Optional.of(kok));
        given(starRepository.save(any(Star.class))).willReturn(kok.getStar());
        given(furnitureOptionRepository.findAll()).willReturn(kok.getCheckedFurnitures().stream().map(CheckedFurniture::getFurnitureOption).toList());
        given(optionRepository.findAll()).willReturn(kok.getCheckedOptions().stream().map(CheckedOption::getOption).toList());
        given(detailOptionRepository.findAll()).willReturn(kok.getCheckedDetailOptions().stream().map(CheckedDetailOption::getDetailOption).toList());

        return kok;
    }
}
