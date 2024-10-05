import controller.CharacterSelectionController;
import database.DB;
import models.*;
import view.MainGameWindow;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DB.loadData();
        new MainGameWindow();
    }
}
