package com.auspost.postcode.PostCode;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostCodeRepository extends JpaRepository<PostCode, Long> {
    List<PostCode> findByPostcode(String postcode);    
    List<PostCode> findPostCodesByAssociatedSuburbsName(String suburb);
}
