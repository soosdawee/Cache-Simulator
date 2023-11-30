package controllers;

import utils.Parser;
import views.CacheView;
import views.IntroView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.List;

public class Controller {
    private CacheView cacheView;
    private IntroView introView;

    public Controller (CacheView cacheView, IntroView introView) {
        this.cacheView = cacheView;
        this.introView = introView;

        this.introView.addStartListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                introView.setVisible(false);
                //TODO: get values from introView and build the initial setup of the tables
                List<String[]> controls = Parser.parseFile("input.txt");

                cacheView.setVisible(true);
            }
        });
    }
}
