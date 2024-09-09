package com.project.zipkok.kok.service;

import com.project.zipkok.common.enums.Role;
import com.project.zipkok.repository.KokRepository;
import com.project.zipkok.service.KokService;
import com.project.zipkok.util.jwt.JwtUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@DisplayName("KokService 테스트")
public class KokServiceTest {

    @Mock
    private KokRepository kokRepository;

    @InjectMocks
    private KokService kokService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 콕_무한페이징_DTO를_반환한다(){
        JwtUserDetails jwtUserDetails = new JwtUserDetails(67L, Role.USER);
        Pageable pageable = PageRequest.of(0, 10);


    }

}
