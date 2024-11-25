package com.project.zipkok.kok.repository;

import com.project.zipkok.common.enums.*;
import com.project.zipkok.dto.GetKokWithZimStatus;
import com.project.zipkok.kok.response.MakeTestKokRepositoryResponse;
import com.project.zipkok.model.*;
import com.project.zipkok.model.CheckedOption;
import com.project.zipkok.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Set;

import static com.project.zipkok.kok.repository.KokRespositoryResponseMatcher.*;
import static com.project.zipkok.kok.response.MakeTestKokServiceResponse.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class KokRepositoryTest {

    @Autowired
    private KokRepository kokRepository;
    @Autowired
    private ZimRepository zimRepository;
    @Autowired
    private RealEstateRepository realEstateRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private DetailOptionRepository detailOptionRepository;
    @Autowired
    private CheckedDetailOptionRepository checkedDetailOptionRepository;
    @Autowired
    private CheckedOptionRepository checkedOptionRepository;
    @Autowired
    private ImpressionRepository impressionRepository;
    @Autowired
    private CheckedImpressionRepository checkedImpressionRepository;

    @DisplayName("유저와 매물에 해당하는 kok 존재 여부 query 성공")
    @Test
    void 콕_존재_여부() throws Exception {
        //given
        User user = getUser(1L);
        RealEstate realEstate = getRealEstateWithoutImage(1L, "test","test","test", TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 10,1.1,1.1);
        Kok kok = MakeTestKokRepositoryResponse.makeTestKok(user, realEstate);
        userRepository.save(user);
        realEstateRepository.save(realEstate);
        kokRepository.save(kok);

        //when
        boolean response = kokRepository.existsByUserAndRealEstate(user, realEstate);

        //then
        assertTrue(response);
    }

    @DisplayName("콕 아이디 값에 일치하는 kok 반환 성공")
    @Test
    void 콕_아이디로_콕_불러오기() throws Exception {
        //given
        User user = getUser(1L);
        RealEstate realEstate = getRealEstateWithoutImage(1L, "test","test","test", TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 101,1.1,1.1);
        Kok kok = MakeTestKokRepositoryResponse.makeTestKok(user, realEstate);
        userRepository.save(user);
        realEstateRepository.save(realEstate);
        kokRepository.save(kok);

        //when
        Kok response = kokRepository.findByKokId(kok.getKokId());

        //then
        expectedRepositoryFindByKokId(response, kok);
    }

    @DisplayName("유저 아아디 값에 일치하는 kok list 반환 성공, 무한페이징")
    @Test
    void 유저_아이디로_콕_무한페이징_불러오기() throws Exception {
        //given
        User user = getUser(1L);
        RealEstate realEstate = getRealEstateWithoutImage(1L, "test","test","test", TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 101,1.1,1.1);
        Kok kok = MakeTestKokRepositoryResponse.makeTestKok(user, realEstate);
        Pageable pageable = PageRequest.of(0, 10);
        userRepository.save(user);
        realEstateRepository.save(realEstate);
        kokRepository.save(kok);

        //when
        Slice<Kok> response = kokRepository.findByUserId(user.getUserId(), pageable);

        //then
        expectedRepositoryFindByUserId(response, kok);
    }

    @DisplayName("유저 아이디 값에 일치하는 콕 list를 Zim 정보와 함께 반환 성공")
    @Test
    void 유저_아이디로_콕_무한페이징_불러오기_찜_포함() throws Exception {
        //given
        User user = getUser(1L);
        RealEstate realEstate = getRealEstateWithoutImage(1L, "test","test","test", TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 10,1.1,1.1);
        Kok kok = MakeTestKokRepositoryResponse.makeTestKok(user, realEstate);
        Zim zim = MakeTestKokRepositoryResponse.makeTestZim(realEstate,user);
        Pageable pageable = PageRequest.of(0, 10);
        userRepository.save(user);
        realEstateRepository.save(realEstate);
        kokRepository.save(kok);
        zimRepository.save(zim);

        //when
        List<GetKokWithZimStatus> response = kokRepository.getKokWithZimStatus(user.getUserId(), pageable);

        //then
        expectedRepositoryGetKokWithZimStatus(response, kok);
    }

    @DisplayName("콕 아이디를 통해 option, detailOption이 join된 콕을 반환 성공")
    @Test
    void 콕_아이디로_옵션과_세부옵션과_함께_콕_반환() throws Exception {
        //given
        User user = getUser(1L);
        RealEstate realEstate = getRealEstateWithoutImage(1L, "test","test","test", TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 10,1.1,1.1);
        Kok kok = MakeTestKokRepositoryResponse.makeTestKok(user, realEstate);
        Option option = MakeTestKokRepositoryResponse.makeTestOption(user);
        CheckedOption checkedOption =MakeTestKokRepositoryResponse.makeTestCheckedOption(kok, option);
        DetailOption detailOption =MakeTestKokRepositoryResponse.makeTestDetailOption(option);
        CheckedDetailOption checkedDetailOption = MakeTestKokRepositoryResponse.makeTestCheckedDetailOption(kok,detailOption );
        userRepository.save(user);
        optionRepository.save(option);
        detailOptionRepository.save(detailOption);
        realEstateRepository.save(realEstate);
        kokRepository.save(kok);
        checkedDetailOptionRepository.save(checkedDetailOption);
        checkedOptionRepository.save(checkedOption);
        kok.setCheckedOptions(Set.of(checkedOption));
        kok.setCheckedDetailOptions(Set.of(checkedDetailOption));
        kokRepository.save(kok);

        //when
        Kok response = kokRepository.findKokWithCheckedOptionAndCheckedDetailOption(kok.getKokId());

        //then
        expectedFindKokWithCheckedOptionAndCheckedDetailOption(response, kok, checkedOption, checkedDetailOption);
    }

    @DisplayName("콕 아이디를 통해 impression, star가 join된 콕을 반환 성공")
    @Test
    void 콕_아이디로_느낀점과_별점과_함께_콕_반환() throws Exception {
        //given
        User user = getUser(1L);
        RealEstate realEstate = getRealEstateWithoutImage(1L, "test","test","test", TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 10,1.1,1.1);
        Kok kok = MakeTestKokRepositoryResponse.makeTestKok(user, realEstate);
        Impression impression = MakeTestKokRepositoryResponse.makeTestImpression();
        CheckedImpression checkedImpression = MakeTestKokRepositoryResponse.makeTestCheckedImpression(kok, impression);
        userRepository.save(user);
        realEstateRepository.save(realEstate);
        impressionRepository.save(impression);
        kokRepository.save(kok);
        checkedImpressionRepository.save(checkedImpression);
        kok.setCheckedImpressions(List.of(checkedImpression));
        kokRepository.save(kok);

        //when
        Kok response = kokRepository.findKokWithImpressionAndStar(kok.getKokId());

        //then
        expectedFindKokWithImpressionAndStar(response, kok);
    }
}


