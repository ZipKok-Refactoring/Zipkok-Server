package com.project.zipkok.kok.service;

import com.project.zipkok.dto.GetKokWithZimStatus;
import com.project.zipkok.repository.KokRepository;
import com.project.zipkok.service.KokService;
import com.project.zipkok.util.jwt.JwtUserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.zipkok.common.enums.Role.GUEST;
import static com.project.zipkok.kok.fixture.KokFixture.KOK_01;
import static com.project.zipkok.kok.fixture.UserFixture.DUMMY_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("KokService 테스트")
@ExtendWith(MockitoExtension.class)
public class KokServiceTest {

    @Mock
    private KokRepository kokRepository;

    @InjectMocks
    private KokService kokService;

    @Test
    void 콕_무한페이징_DTO를_반환한다(){

    }

}
