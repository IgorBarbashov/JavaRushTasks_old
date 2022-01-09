package com.javarush.task.task20.task2015;

import java.io.*;

import static java.lang.Thread.sleep;

/*
Переопределение сериализации
*/
public class Solution implements Serializable, Runnable {
    private transient Thread runner;
    private int speed;

    public Solution(int speed) {
        this.speed = speed;
        runner = new Thread(this);
        runner.start();
    }

    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + " : speed - " + speed + " : " + this);
            try {
                sleep(3000);
            } catch (InterruptedException e) {
            }
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        speed *= 2;
        runner = new Thread(this);
        runner.start();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        Solution firstSolution = new Solution(100);
        oos.writeObject(firstSolution);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Solution secondSolution = new Solution(500);
        secondSolution = (Solution) ois.readObject();
        ois.close();
    }
}