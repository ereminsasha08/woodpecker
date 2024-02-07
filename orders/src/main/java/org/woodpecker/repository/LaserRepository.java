package org.woodpecker.repository;

import org.woodpecker.repository.model.support.Laser;

import java.util.Optional;

public interface LaserRepository extends BaseRepository<Laser> {


    Optional<Laser> findByName(String laserName);
}
