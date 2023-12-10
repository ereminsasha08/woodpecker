package com.woodpecker.repository;

import com.woodpecker.model.support.Laser;

import java.util.Optional;

public interface LaserRepository extends BaseRepository<Laser> {


    Optional<Laser> findByName(String laserName);
}