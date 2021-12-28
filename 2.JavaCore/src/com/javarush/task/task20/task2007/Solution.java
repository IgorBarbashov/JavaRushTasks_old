package com.javarush.task.task20.task2007;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ObjIntConsumer;

/* 
Как сериализовать JavaRush?
*/
public class Solution {
    public static class JavaRush implements Serializable {
        public List<User> users = new ArrayList<>();

        @Override
        public String toString() {
            return "JavaRush{" +
                    "users=" + users +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileOutputStream fos = new FileOutputStream("1.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        User user1 = new User();
        user1.setFirstName("Igor");
        user1.setCountry(User.Country.RUSSIA);

        JavaRush javaRush = new JavaRush();
        javaRush.users.add(user1);

        oos.writeObject(javaRush);
        oos.close();

        FileInputStream fis = new FileInputStream("1.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        JavaRush newJavaRush = new JavaRush();
        newJavaRush = (JavaRush) ois.readObject();
        System.out.println(newJavaRush);

    }
}
