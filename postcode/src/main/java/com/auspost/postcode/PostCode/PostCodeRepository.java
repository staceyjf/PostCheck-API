package com.auspost.postcode.PostCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auspost.postcode.Suburb.Suburb;

import java.util.List;

public interface PostCodeRepository extends JpaRepository<PostCode, Long> {
    @Query("SELECT p.associatedSuburbs FROM PostCode p WHERE p.postcode = :postcode")
    List<Suburb> findAssociatedSuburbsByPostcode(@Param("postcode") String postcode);

    List<PostCode> findPostCodesByAssociatedSuburbsName(String suburbName);
}
