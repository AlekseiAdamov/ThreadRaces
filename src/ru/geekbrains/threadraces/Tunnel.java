package ru.geekbrains.threadraces;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private final Semaphore limiter;
    public Tunnel(Semaphore limiter) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        this.limiter = limiter;
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                limiter.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                limiter.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}