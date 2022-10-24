package com.javarush.uzienko.sevices;

import com.javarush.uzienko.config.GameConfig;
import com.javarush.uzienko.config.Phases;
import com.javarush.uzienko.entity.residents.Animal;
import com.javarush.uzienko.entity.residents.Plant;
import com.javarush.uzienko.entity.residents.Resident;
import com.javarush.uzienko.exceptions.IslandException;
import com.javarush.uzienko.repository.Repository;
import com.javarush.uzienko.storage.Coords;
import com.javarush.uzienko.utils.Generator;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class GodsWillServiceImpl implements GodsWillService {
    private final GameConfig gameConfig;
    private final Repository repository;

    @Override
    public Resident createResident(Coords coords, Resident resident) throws CloneNotSupportedException {
        Resident residentClone = resident.clone();
        residentClone.setGodsWillService(this);
        residentClone.getProperties().setCoords(coords);
        return residentClone;
    }

    @Override
    public boolean removeResident(Resident resident) {
        return repository.removeResident(resident);
    }

    @Override
    public void createTheIsland() {
        gameConfig.getContext().forEach((k, v) -> {
            for (int i = 0; i < gameConfig.getRows(); i++) {
                for (int j = 0; j < gameConfig.getCols(); j++) {
                    Coords coords = new Coords(j, i);
                    int count = Generator.get(0, v.getProperties().getMaxInCell() + 1);
                    Set<Resident> residentSet = new HashSet<>();
                    try {
                        for (int l = 0; l < count; l++) {
                            residentSet.add(createResident(coords, v));
                        }
                    } catch (CloneNotSupportedException e) {
                        throw new IslandException(e);
                    }
                    repository.addAll(coords, residentSet);
                }
            }
        });
    }

    @Override
    public Map<String, Set<Resident>> getResidents(Coords coords) {
        return repository.getResidentsByCoords(coords);
    }

    @Override
    public Phases whatIShouldToDo(Resident resident) {
        if (resident instanceof Animal animal) {
            return Phases.values()[Generator.get(0, Phases.values().length)];
        }
        return Phases.SPAWN;
    }

    @Override
    public void giveSpawnChanceForResident(Resident resident) throws CloneNotSupportedException {
        int countInCell = repository.getHeardSize(resident.getProperties().getCoords(),
                resident.getProperties().getType());
        int maxInCell = resident.getProperties().getMaxInCell();

        if (countInCell + 1 > maxInCell) {
            return;
        }

        if (resident instanceof Animal && countInCell < 2) {
            return;
        }

        int health = (int) (resident.getProperties().getCurrentWeight() / resident.getProperties().getWeight() * 100 / 4);

        if (Generator.get(0, 100) < health || resident instanceof Plant) {
            Resident spawnResident = createResident(resident.getProperties().getCoords(), resident);
            repository.addResidentToCoords(spawnResident.getProperties().getCoords(), spawnResident);
        }
    }

    @Override
    public Coords whereIShouldGo(Coords cords, int steps) {
        int x = cords.getX();
        int y = cords.getY();

        for (int i = 0; i < steps; i++) {
            int direction = Generator.get(0, 4);
            switch (direction) {
                case 0 -> ++x;
                case 1 -> --x;
                default -> {
                }
            }
            switch (direction) {
                case 2 -> ++y;
                case 3 -> --y;
                default -> {
                }
            }
        }

        return new Coords(Math.abs(x % gameConfig.getCols()), Math.abs(y % gameConfig.getRows()));
    }

    @Override
    public boolean tryToMigrateResident(Coords oldCoords, Coords newCoords, Resident resident) {
        return repository.tryToMigrateResident(oldCoords, newCoords, resident);
    }

    @Override
    public Set<Resident> giveFoodForResident(Resident resident) {
        if (resident == null || resident.getProperties().getFoodList().isEmpty()) {
            return new HashSet<>();
        }

        Set<String> types = resident.getProperties().getFoodList().keySet();

        return repository.getAll(resident.getProperties().getCoords(), types);
    }
}
