package excelUtils;

import java.io.IOException;
import java.util.ArrayList;

public class testClass {
    public static void main(String[] args) throws IOException {
        TestData td = new TestData();
        ArrayList<String> d = td.getData("LibraryAPI","AddBook");
      //  System.out.println(d.get(0));
        System.out.println(d.get(1));
        System.out.println(d.get(2));
        System.out.println(d.get(3));
        System.out.println(d.get(4));
    }
}
