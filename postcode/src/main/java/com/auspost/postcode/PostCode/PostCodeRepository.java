package com.auspost.postcode.PostCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

//  intro query by example for more flexible queries 
public interface PostCodeRepository extends JpaRepository<PostCode, Long> {
    List<PostCode> findByPostcode(String postcode);

    List<PostCode> findPostCodesByAssociatedSuburbsNameContainingIgnoreCase(String suburb);
}
