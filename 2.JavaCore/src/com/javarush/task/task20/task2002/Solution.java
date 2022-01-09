package com.javarush.task.task20.task2002;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* 
Читаем и пишем в файл: JavaRush
*/
public class Solution {
    public static void main(String[] args) {
        //you can find your_file_name.tmp in your TMP directory or adjust outputStream/inputStream according to your file's actual location
        //вы можете найти your_file_name.tmp в папке TMP или исправьте outputStream/inputStream в соответствии с путем к вашему реальному файлу
        try {
            File yourFile = File.createTempFile("your_file_name", null);
            OutputStream outputStream = new FileOutputStream(yourFile);
            InputStream inputStream = new FileInputStream(yourFile);

            JavaRush javaRush = new JavaRush();
            //initialize users field for the javaRush object here - инициализируйте поле users для объекта javaRush тут
            System.out.println(javaRush);
            User user1 = new User();
            user1.setCountry(User.Country.RUSSIA);
            user1.setFirstName("Bob");

            User user2 = new User();
            user2.setFirstName("Tom");
            user2.setLastName("Smith");
            user2.setBirthDate(new Date());
            user2.setMale(true);
            user2.setCountry(User.Country.OTHER);

            javaRush.users.add(user1);
            javaRush.users.add(user2);
            javaRush.save(outputStream);
            outputStream.flush();

            JavaRush loadedObject = new JavaRush();
            loadedObject.load(inputStream);
            //here check that the codeGym object is equal to the loadedObject object - проверьте тут, что javaRush и loadedObject равны
            System.out.println(loadedObject);
            System.out.println(javaRush.equals(loadedObject));

            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Oops, something is wrong with my file");
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Oops, something is wrong with the save/load method");
        }
    }

    public static class JavaRush {
        public List<User> users = new ArrayList<>();

        public void save(OutputStream outputStream) throws Exception {
            PrintWriter pw = new PrintWriter(outputStream);
            int count = users.size();
            pw.println(count);
            if (count != 0) {
                for (User user : users) {
                    pw.println(user.getFirstName());
                    pw.println(user.getLastName());

                    Date date = user.getBirthDate();
                    if (date != null) {
                        pw.println(date.getTime());
                    } else pw.println("null");

                    pw.println(user.isMale());
                    pw.println(user.getCountry());
                }
            }
            pw.close();
        }

        public void load(InputStream inputStream) throws Exception {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            int count = Integer.parseInt(br.readLine());
            if (count != 0) {
                for (int i = 0; i < count; i++) {
                    User user = new User();
                    String name = br.readLine();
                    if (!name.equals("null")) user.setFirstName(name);
                    String lastName = br.readLine();
                    if (!lastName.equals("null")) user.setLastName(lastName);

                    String date = br.readLine();
                    if (!date.equals("null")) user.setBirthDate(
                            new Date(Long.valueOf(date))
                    );

                    String male = br.readLine();
                    if (!male.equals("null")) user.setMale(male.equals("true"));
                    String county = br.readLine();
                    if (!county.equals("null")) user.setCountry(User.Country.valueOf(county));
                    users.add(user);
                }
            }
            br.close();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            JavaRush javaRush = (JavaRush) o;

            return users != null ? users.equals(javaRush.users) : javaRush.users == null;

        }

        @Override
        public int hashCode() {
            return users != null ? users.hashCode() : 0;
        }
    }
}
