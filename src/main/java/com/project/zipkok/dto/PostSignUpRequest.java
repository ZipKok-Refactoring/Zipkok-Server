package com.project.zipkok.dto;

import com.project.zipkok.common.enums.Gender;
import com.project.zipkok.common.enums.OAuthProvider;
import com.project.zipkok.common.enums.Role;
import com.project.zipkok.common.enums.ValidEnum;
import com.project.zipkok.model.DesireResidence;
import com.project.zipkok.model.TransactionPriceConfig;
import com.project.zipkok.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostSignUpRequest {

    @NotBlank(message = "nickname: {NotBlank}")
    @Size(max = 12)
    private String nickname;

    @ValidEnum(enumClass = OAuthProvider.class)
    private String oauthProvider;

    @NotBlank
    @Email
    private String email;

    @ValidEnum(enumClass = Gender.class)
    private String gender;

    @NotBlank
    @Size(max =6)
    private String birthday;

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .oAuthProvider(OAuthProvider.valueOf(oauthProvider))
                .email(email)
                .birthday(birthday)
                .gender(Gender.valueOf(gender))
                .status("active")
                .role(Role.USER)
                .transactionPriceConfig(new TransactionPriceConfig())
                .desireResidence(new DesireResidence())
                .build();
    }
}
