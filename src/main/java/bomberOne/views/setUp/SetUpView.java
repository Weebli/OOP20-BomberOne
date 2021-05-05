package bomberOne.views.setUp;

import bomberOne.views.basic.View;

public interface SetUpView extends View {

    void init();

    void drawSetUp();

    void switchToGame();

    void switchToHome();
}
