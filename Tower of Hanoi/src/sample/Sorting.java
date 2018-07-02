package sample;

import java.io.*;

public class Sorting {
    int data;
    String name, lavel;

    Sorting(int Value, String Name, String Lavel) {
        data = Value;
        name = Name;
        lavel = Lavel;
    }

    Sorting() {
    }

    public void sort() {
        Sorting[] obj = new Sorting[1000];
        BufferedReader reader = null;
        int i = 0;
        String s;
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\User\\Solver.txt"));
            while ((s = reader.readLine()) != null) {
                name = s;
                s = reader.readLine();
                lavel = s;
                s = reader.readLine();
                data = Integer.parseInt(s);
                obj[i] = new Sorting(data, name, lavel);
                i++;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int size = i;
        Sorting temp = null;
        for (i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (obj[i].data > obj[j].data) {
                    temp = obj[i];
                    obj[i] = obj[j];
                    obj[j] = temp;
                }
            }
        }
        BufferedWriter writer=null;
        try {
            writer = new BufferedWriter(new FileWriter("C:\\Users\\User\\Solver.txt"));
            writer.close();
            writer = new BufferedWriter(new FileWriter("C:\\Users\\User\\Solver.txt", true));
            for (i = 0; i < size; i++) {
                writer.append(obj[i].name + "\n" + obj[i].lavel + "\n" + Integer.toString(obj[i].data) + "\n");
            }
            writer.close();
        } catch (IOException e1) {
            System.out.println("ERROR");
            e1.printStackTrace();
        }
    }
}
