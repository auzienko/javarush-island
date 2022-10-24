package com.javarush.uzienko.sevices;

import com.javarush.uzienko.config.Phases;
import com.javarush.uzienko.entity.residents.Resident;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResidentWorker implements Runnable {
    private final Resident resident;
    private final Phases phase;

    @Override
    public void run() {
        doSomething();
    }

    private void doSomething() {
        resident.startPhase(phase);
    }
}
