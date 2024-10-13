import controller.CharacterSelectionController;
import database.DB;
import models.*;
import models.Character;
import view.MainGameWindow;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        DB.loadData();
        new MainGameWindow();
    }
}
