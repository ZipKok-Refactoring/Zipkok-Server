package com.project.zipkok.repository;

import com.project.zipkok.model.RealEstate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, Long> {

    @EntityGraph(attributePaths = {"realEstateImages"})
    Optional<RealEstate> findById(long realEstateId);

    @EntityGraph(attributePaths = {"realEstateImages"})
    List<RealEstate> findByLatitudeBetweenAndLongitudeBetween(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude);

    @Query(value = "SELECT r " +
            "FROM RealEstate r " +
            "JOIN FETCH r.realEstateImages " +
            "WHERE r.latitude BETWEEN :minLat AND :maxLat " +
            "AND r.longitude BETWEEN :minLon AND :maxLon " +
            "AND (r.latitude <> :latitude OR r.longitude <> :longitude) " +
            "ORDER BY r.realEstateId ASC " +
            "LIMIT 5")
    List<RealEstate> findTop5ByLatitudeBetweenAndLongitudeBetween(@Param(value = "latitude") double latitude, @Param(value = "longitude") double longitude, @Param("minLat") double minLat, @Param("maxLat") double maxLat, @Param("minLon") double minLon, @Param("maxLon") double maxLon);
}
