package xellos.aka.atan.example;
import java.util.ArrayList;
public class Visual {

    public void paint(ArrayList<Integer> LastTestValue) {
        String alphabet = "abcdefg";
        String temp;
        System.out.println();
        System.out.println();
        System.out.println("  0 1 2 3 4 5 6");
        for (int i = 0 ; i < 7 ; i++)
        {
            temp = String.valueOf(alphabet.charAt(i));
            System.out.print(temp);
            for (int j = 0 ; j < 7 ; j++)
            {
                boolean check = false;

                for (int k = 1 ; k < 19 ; k+=2) {

                    if (LastTestValue.get(k-1) == j && LastTestValue.get(k) == i)
                    {
                        check = true;
                    }
                }
                    if (check)
                    {
                        System.out.print(" #");
                    }
                    else {
                        System.out.print(" *");
                    }
            }
            System.out.println();
        }

        System.out.println();
        System.out.print("  TestArray " + LastTestValue );
        System.out.println();
    }

}
