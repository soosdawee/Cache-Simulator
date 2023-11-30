import controllers.Controller;
import views.CacheView;
import views.IntroView;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        IntroView introView = new IntroView();
        CacheView cacheView = new CacheView();

        Controller controller = new Controller(cacheView, introView);


    }
}
