package com.project.zipkok.user.application;

import com.project.zipkok.common.service.RedisService;
import com.project.zipkok.dto.*;
import com.project.zipkok.user.fixture.AuthTokensFixture;
import com.project.zipkok.user.fixture.JwtUserDetailsFixture;
import com.project.zipkok.user.fixture.UserDtoFixture;
import com.project.zipkok.user.fixture.UserFixture;
import com.project.zipkok.model.Highlight;
import com.project.zipkok.model.Option;
import com.project.zipkok.model.User;
import com.project.zipkok.repository.*;
import com.project.zipkok.service.UserService;
import com.project.zipkok.util.jwt.AuthTokens;
import com.project.zipkok.util.jwt.JwtProvider;
import com.project.zipkok.util.jwt.JwtUserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DesireResidenceRepository desireResidenceRepository;

    @Mock
    private TransactionPriceConfigRepository transactionPriceConfigRepository;

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private KokRepository kokRepository;

    @Mock
    private HighLightBulkJdbcRepository highLightBulkJdbcRepository;

    @Mock
    private ImpressionBulkJdbcRepository impressionBulkJdbcRepository;

    @Mock
    private OptionBulkJdbcRepository optionBulkJdbcRepository;

    @Mock
    private DetailOptionBulkJdbcRepository detailOptionBulkJdbcRepository;

    @Mock
    private HighlightRepository highlightRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private RedisService redisService;

    @Mock
    private JwtUserDetails jwtUserDetails;

    @DisplayName("회원가입에 성공하면 토큰을 생성하여 반환한다.")
    @Test
    void signUp() {
        //given
        PostSignUpRequest request = UserDtoFixture.createDefaultPostSignUpRequest();
        User expectedUser = request.toEntity();
        AuthTokens expectedToken = AuthTokensFixture.createDefaultAuthTokens();

        given(userRepository.save(any(User.class))).willReturn(expectedUser);
        given(jwtProvider.createToken(any())).willReturn(expectedToken);
        willDoNothing().given(redisService).setValues(any(String.class), any(String.class), any(Duration.class));

        //when
        AuthTokens actualToken = userService.signUp(request);

        //then
        assertEquals(expectedToken, actualToken);

        //verify
        then(userRepository).should(times(1)).save(any(User.class));
        then(jwtProvider).should(times(1)).createToken(any());
        then(redisService).should(times(1)).setValues(any(String.class), any(String.class), any(Duration.class));

    }

    @DisplayName("유저의 온보딩 정보를 수정할 수 있다.")
    @Test
    void setOnBoarding() {

        //given
        PatchOnBoardingRequest request = UserDtoFixture.createDefaultPatchOnBoardingRequest();
        User expectUser = UserFixture.createUserWithDetails();
        JwtUserDetails expectJwtUserDetails = JwtUserDetailsFixture.createDefaultJwtUserDetails();

        given(userRepository.findByUserIdWithDesireResidenceAndTransactionPriceConfig(1L)).willReturn(expectUser);

        //when
        Object result = userService.setOnBoarding(request, expectJwtUserDetails);

        //then
        assertNull(result);

        //verify
        // Mock 객체의 메서드 호출 검증
        then(userRepository).should(times(1)).findByUserIdWithDesireResidenceAndTransactionPriceConfig(any(Long.class));
        then(userRepository).should(times(1)).save(any(User.class));

    }

    @DisplayName("유저 정보를 조회할 수 있다.")
    @Test
    void myPageLoad() {

        //given
        JwtUserDetails jwtUserDetails = JwtUserDetailsFixture.createDefaultJwtUserDetails();
        User expectUser = UserFixture.createUserWithDetails();

        given(userRepository.findByUserIdWithDesireResidenceAndTransactionPriceConfig(jwtUserDetails.getUserId())).willReturn(expectUser);

        //when
        GetMyPageResponse response = userService.myPageLoad(jwtUserDetails);

        //then
        assertNotNull(response);
        assertEquals(expectUser.getNickname(), response.getNickname());
        then(userRepository).should().findByUserIdWithDesireResidenceAndTransactionPriceConfig(jwtUserDetails.getUserId());
    }

    //@DisplayName("유저 정보를 상세하게 조회할 수 있다.")
    @Test
    void myPageDetailLoad() {

        //given
        JwtUserDetails jwtUserDetails = JwtUserDetailsFixture.createDefaultJwtUserDetails();
        User expectUser = UserFixture.createUserWithDetails();

        given(userRepository.findByUserIdWithDesireResidenceAndTransactionPriceConfig(jwtUserDetails.getUserId())).willReturn(expectUser);

        //when
        GetMyPageDetailResponse response = userService.myPageDetailLoad(jwtUserDetails);

        //then
        assertNotNull(response);
        then(userRepository).should().findByUserIdWithDesireResidenceAndTransactionPriceConfig(jwtUserDetails.getUserId());
    }

    @DisplayName("마이페이지 리스트 항목 수정 페이지 로드할 수 있다.")
    @Test
    void loadKokOption() {
        //이렇게 테스트하는게 맞는지 모르겠다!

        //given
        JwtUserDetails jwtUserDetails = JwtUserDetailsFixture.createDefaultJwtUserDetails();
        User expectUser = UserFixture.createUserWithDetails();

        List<Highlight> highlightList = Highlight.makeDefaultHighlights(expectUser).stream().toList();
        List<Option> optionList = Option.makeDefaultOptions(expectUser);

        given(highlightRepository.findAllByUserId(jwtUserDetails.getUserId())).willReturn(highlightList);
        given(optionRepository.findAllByUserIdWithDetailOption(jwtUserDetails.getUserId())).willReturn(optionList);

        //when
        GetKokOptionLoadResponse response = userService.loadKokOption(jwtUserDetails);

        //then
        assertThat(response).isNotNull();

        then(highlightRepository).should().findAllByUserId(jwtUserDetails.getUserId());
        then(optionRepository).should().findAllByUserIdWithDetailOption(jwtUserDetails.getUserId());
    }


    @DisplayName("마이페이지 리스트 항목 수정할 수 있다.")
    @Test
    void updateKokOption() {

        // given
        JwtUserDetails jwtUserDetails = JwtUserDetailsFixture.createDefaultJwtUserDetails();
        PostUpdateKokOptionRequest requestDto = mock(PostUpdateKokOptionRequest.class);

        willDoNothing().given(highLightBulkJdbcRepository).deleteAll(any());
        willDoNothing().given(highLightBulkJdbcRepository).saveAll(any());
        willDoNothing().given(optionBulkJdbcRepository).updateAll(any());

        // when
        userService.updateKokOption(jwtUserDetails, requestDto);

        // then
        then(highLightBulkJdbcRepository).should(times(1)).deleteAll(any());
        then(highLightBulkJdbcRepository).should(times(1)).saveAll(any());
        then(optionBulkJdbcRepository).should(times(1)).updateAll(any());
    }

}