package ru.geekbrains.threadraces;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class MainClass {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        CyclicBarrier startLine = new CyclicBarrier(CARS_COUNT);
        CountDownLatch startCounter = new CountDownLatch(CARS_COUNT);
        CountDownLatch finishCounter = new CountDownLatch(CARS_COUNT);
        Semaphore limiter = new Semaphore(CARS_COUNT / 2);

        Race race = new Race(new Road(60), new Tunnel(limiter), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), startLine, startCounter, finishCounter);
        }
        for (Car car : cars) {
            new Thread(car).start();
        }
        try {
            finishCounter.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
