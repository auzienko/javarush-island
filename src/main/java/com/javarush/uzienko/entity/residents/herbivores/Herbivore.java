package com.javarush.uzienko.entity.residents.herbivores;

import com.javarush.uzienko.entity.properties.ResidentProperties;
import com.javarush.uzienko.entity.residents.Animal;

public abstract class Herbivore extends Animal {

    public Herbivore(ResidentProperties properties) {
        super(properties);
    }
}
