import database.DB;
import view.MainGameWindow;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DB.loadData();
        new MainGameWindow();
    }
}
