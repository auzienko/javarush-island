package com.javarush.uzienko.entity.residents.carnivores;

import com.javarush.uzienko.entity.properties.ResidentProperties;
import com.javarush.uzienko.entity.residents.Animal;

public abstract class Carnivore extends Animal {

    public Carnivore(ResidentProperties properties) {
        super(properties);
    }
}
