package com.example.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rest.entity.Weather;


@Repository
public interface PincodeWeatherRepository extends JpaRepository<Weather, Integer> {

}
