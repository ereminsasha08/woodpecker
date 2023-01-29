package com.woodpecker.woodpecker.repository;

import com.woodpecker.woodpecker.model.support.Laser;

public interface LaserRepository extends BaseRepository<Laser> {

    Laser findByName(String laserName);
}
