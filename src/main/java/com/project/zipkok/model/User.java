package com.project.zipkok.model;

import com.project.zipkok.common.enums.*;
import com.project.zipkok.common.enums.Gender;
import com.project.zipkok.common.enums.OAuthProvider;
import com.project.zipkok.common.enums.RealEstateType;
import com.project.zipkok.common.enums.TransactionType;
import com.project.zipkok.dto.PatchOnBoardingRequest;
import com.project.zipkok.dto.PutUpdateMyInfoRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "\"User\"")
@Getter
@NoArgsConstructor
@Setter
@Builder
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "profileimg_url", nullable = true)
    private String profileImgUrl;

    @Column(name = "o_auth_provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthProvider oAuthProvider;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birthday", nullable = false)
    private String birthday;

    @Column(name = "realestate_type", nullable = true)
    @Enumerated(EnumType.STRING)
    private RealEstateType realEstateType;

    @Column(name = "transaction_type", nullable = true)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status", nullable = false)
    private String status = "active";

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "desire_residence_id", nullable = true)
    private DesireResidence desireResidence;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_config_id", nullable = true)
    private TransactionPriceConfig transactionPriceConfig;

    @ElementCollection
    private Set<Long> realEstateIds = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user",orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Zim> zims = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user",orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Kok> koks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user",orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Option> options = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Pin> pins = new ArrayList<>();

    @OneToMany(mappedBy = "user",orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Highlight> highlights = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Impression> impressions  = new LinkedHashSet<>();

    @Builder
    public User(String email, OAuthProvider oAuthProvider, String nickname, Gender gender, String birthday) {
        this.email = email;
        this.oAuthProvider = oAuthProvider;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.role = Role.USER;
        this.status =  "active";
    }

    public void setOnBoardingInfo(PatchOnBoardingRequest patchOnBoardingRequest) {
        this.realEstateType = patchOnBoardingRequest.getRealEstateType();
        this.transactionType = patchOnBoardingRequest.getTransactionType();

        this.desireResidence.setDesireResidenceInfo(patchOnBoardingRequest);
        this.transactionPriceConfig.setTransactionPriceConfig(patchOnBoardingRequest);
    }

    public void setUpdateUserInfo(String url, PutUpdateMyInfoRequest putUpdateMyInfoRequest){
        this.nickname = putUpdateMyInfoRequest.getNickname();
        this.birthday = putUpdateMyInfoRequest.getBirthday();
        this.gender = putUpdateMyInfoRequest.getGender();
        this.realEstateType = putUpdateMyInfoRequest.getRealEstateType();
        this.transactionType = putUpdateMyInfoRequest.getTransactionType();

        this.profileImgUrl = url;

        this.desireResidence.setDesireResidenceInfo(putUpdateMyInfoRequest);
        this.transactionPriceConfig.setTransactionPriceConfig(putUpdateMyInfoRequest);
    }
}
