package com.project.zipkok.model;

import com.project.zipkok.dto.PatchOnBoardingRequest;
import com.project.zipkok.dto.PutUpdateMyInfoRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DesireResidence")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class DesireResidence {

    @Id
    @Column(name = "desire_residence_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long desireResidenceId;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude", nullable = true)
    private Double latitude;

    @Column(name = "longitude", nullable = true)
    private Double longitude;

    @Column(name = "status", nullable = false)
    private String status = "active";

    @OneToOne(mappedBy = "desireResidence", orphanRemoval = true, cascade = CascadeType.ALL)
    private User user;

    public DesireResidence(User user) {
        this.user = user;
    }

    public void setDesireResidenceInfo(PatchOnBoardingRequest patchOnBoardingRequest){
        this.address = patchOnBoardingRequest.getAddress();
        this.latitude = patchOnBoardingRequest.getLatitude();
        this.longitude = patchOnBoardingRequest.getLongitude();
    }

    public void setDesireResidenceInfo(PutUpdateMyInfoRequest putUpdateMyInfoRequest) {
        this.address = putUpdateMyInfoRequest.getAddress();
        this.latitude = putUpdateMyInfoRequest.getLatitude();
        this.longitude = putUpdateMyInfoRequest.getLongitude();
    }

    @Builder
    public DesireResidence(String address, Double latitude, Double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = "active";
    }
}
