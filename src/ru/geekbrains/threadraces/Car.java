package ru.geekbrains.threadraces;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private final Race race;
    private final int speed;
    private final String name;
    private final CyclicBarrier startLine;
    private final CountDownLatch startCounter;
    private final CountDownLatch finishCounter;

    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CyclicBarrier startLine, CountDownLatch startCounter, CountDownLatch finishCounter) {
        this.race = race;
        this.speed = speed;
        this.startLine = startLine;
        this.startCounter = startCounter;
        this.finishCounter = finishCounter;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            startCounter.countDown();
            if (startCounter.getCount() == 0) {
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            }
            startLine.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        finishCounter.countDown();
        if (finishCounter.getCount() == CARS_COUNT - 1) {
            System.out.println(name + " - WIN");
        }
    }
}