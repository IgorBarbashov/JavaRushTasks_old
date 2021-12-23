package com.javarush.task.task20.task2006;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* 
Как сериализовать?
*/
public class Solution {
    public static class Human implements Serializable {
        public String name;
        public List<Asset> assets = new ArrayList<>();

        public Human() {
        }

        public Human(String name, Asset... assets) {
            this.name = name;
            if (assets != null) {
                this.assets.addAll(Arrays.asList(assets));
            }
        }

        @Override
        public String toString() {
            return "Human{" +
                    "name='" + name + '\'' +
                    ", assets=" + assets +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileOutputStream fos = new FileOutputStream("1.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        Human human = new Human("Igor");
        human.assets.add(new Asset("Первый пошел"));
        human.assets.add(new Asset("Второй пошел"));
        oos.writeObject(human);
        oos.close();

        FileInputStream fis = new FileInputStream("1.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Human newHuman = new Human();
        newHuman = (Human) ois.readObject();
        System.out.println(newHuman);
        ois.close();
    }


}
