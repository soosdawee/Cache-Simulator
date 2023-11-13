package controllers;

import views.CacheView;
import views.IntroView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private CacheView cacheView;
    private IntroView introView;

    public Controller (CacheView cacheView, IntroView introView) {
        this.cacheView = cacheView;
        this.introView = introView;

        this.introView.addStartListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("fasz");
                introView.setVisible(false);
                cacheView.setVisible(true);
            }
        });
    }
}
