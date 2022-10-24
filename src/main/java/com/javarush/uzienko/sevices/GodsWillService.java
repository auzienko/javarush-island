package com.javarush.uzienko.sevices;

import com.javarush.uzienko.config.Phases;
import com.javarush.uzienko.entity.residents.Resident;
import com.javarush.uzienko.storage.Coords;

import java.util.Map;
import java.util.Set;

public interface GodsWillService {

    void createTheIsland();

    Resident createResident(Coords coords, Resident resident) throws CloneNotSupportedException;

    @SuppressWarnings("UnusedReturnValue")
    boolean removeResident(Resident resident);

    Map<String, Set<Resident>> getResidents(Coords coords);

    Phases whatIShouldToDo(Resident resident);

    Set<Resident> giveFoodForResident(Resident resident);

    void giveSpawnChanceForResident(Resident resident) throws CloneNotSupportedException;

    Coords whereIShouldGo(Coords cords, int steps);

    boolean tryToMigrateResident(Coords oldCoords, Coords newCoords, Resident resident);
}
