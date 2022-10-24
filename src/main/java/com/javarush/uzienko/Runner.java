package com.javarush.uzienko;

import com.javarush.uzienko.config.GameConfig;
import com.javarush.uzienko.repository.Repository;
import com.javarush.uzienko.repository.RepositoryImpl;
import com.javarush.uzienko.sevices.GodsWillService;
import com.javarush.uzienko.sevices.GodsWillServiceImpl;
import com.javarush.uzienko.sevices.MainLoopWorker;
import com.javarush.uzienko.sevices.StatisticService;
import com.javarush.uzienko.storage.Storage;
import com.javarush.uzienko.storage.StorageImpl;
import com.javarush.uzienko.view.ConsoleGameView;
import com.javarush.uzienko.view.GameView;

public class Runner {
    public static void main(String[] args) {

        GameConfig gameConfig = GameConfig.getInstance();

        Storage storage = new StorageImpl(gameConfig);
        Repository repository = new RepositoryImpl(storage);

        GodsWillService godsWillService = new GodsWillServiceImpl(gameConfig, repository);
        godsWillService.createTheIsland();

        StatisticService statisticService = new StatisticService(gameConfig, repository);

        GameView gameView = new ConsoleGameView(gameConfig, statisticService);

        MainLoopWorker game = new MainLoopWorker(gameView, gameConfig, godsWillService, statisticService);
        game.start();
    }
}
