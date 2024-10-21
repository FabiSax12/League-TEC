package view.components;

import models.*;
import models.Character;
import view.BattleArena;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

class ActionCard extends ButtonComponent {
    public ActionCard(String action) {
        super(action, CustomColors.BLUE);
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}

public class ActionsPanel extends JPanel {
    private final BattleArena arena;
    private final Match match;
    private final Team turn;
    private final MatrixButton[][] matrixButtons;

    public ActionsPanel(MatrixButton btn, MatrixButton[][] matrixButtons, Team turn, BattleArena arena, Match match) {
        this.turn = turn;
        this.arena = arena;
        this.match = match;
        this.matrixButtons = matrixButtons;

        setLayout(new GridLayout(0, 1, 0, 10));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel turnLabel = new JLabel("Haz seleccionado " + btn.getEntity().toString());
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(turnLabel);
        addActionButtons(btn);
    }

    private void addActionButtons(MatrixButton btn) {
        ActionCard skipAction = new ActionCard("Skip");
        skipAction.addActionListener(e -> handleSkipAction(btn.getEntity()));
        add(skipAction);

        if (btn.getEntity() instanceof Character) {
            addCharacterActions(btn);
        } else if (btn.getEntity() instanceof Tower) {
            addTowerActions(btn);
        }
    }

    private void handleSkipAction(Entity entity) {
        arena.entityActed(entity);
        clearPanel();
    }

    private void addCharacterActions(MatrixButton btn) {
        ActionCard moveCard = new ActionCard("Move");
        ActionCard skillCard = new ActionCard("Skill");

        moveCard.addActionListener(e -> handleMoveAction(btn));
        skillCard.addActionListener(e -> handleSkillOptions(btn));

        add(moveCard);
        add(skillCard);
    }

    private void addTowerActions(MatrixButton btn) {
        ActionCard attackAction = new ActionCard("Attack");

        attackAction.addActionListener(e -> {
            MatrixButton[] aroundButtons = getAroundButtons(btn);
            ArrayList<MatrixButton> nearbyEnemies = new ArrayList<>();

            try {
                for (MatrixButton b : aroundButtons) {
                    if (b.getEntity() != null
                            && b.getEntity() instanceof Character
                            && !turn.getEntities().contains(b.getEntity())
                    ) {
                        nearbyEnemies.add(b);
                    }
                }
            }catch(NullPointerException ex){System.out.println("NULL en addTowerActions");}

            for (MatrixButton adjBtn : nearbyEnemies) {
                adjBtn.setFilter(new Color(255, 102, 102, 100));

                adjBtn.addActionListener(e2 -> {
                    attackEntity(btn, adjBtn);
                    arena.enableAllButtons();
                    arena.entityActed(btn.getEntity());

                    for (MatrixButton enemy : nearbyEnemies) {
                        enemy.removeActionListeners();
                        enemy.addActionListener(__ -> arena.handleButtonClick(adjBtn));
                        enemy.resetFilter(match.getTeam1(), match.getTeam2());
                    }
                    adjBtn.refresh();
                });
            }

            clearPanel();
        });

        add(attackAction);
    }

    private void handleMoveAction(MatrixButton btn) {
        int originRow = Math.divideExact(btn.getIdentifier(), 10);
        int originCol = btn.getIdentifier() % 10;
        btn.setEnabled(false);

        ArrayList<MatrixButton> destBtns = new ArrayList<>();

        for (int i = 1; i <= ((Character) btn.getEntity()).getMovements(); i++) {
            MatrixButton[] adjBtns = getAdjacentButtons(originRow, originCol, i);
            destBtns.add(adjBtns[0]);
            destBtns.add(adjBtns[1]);
            destBtns.add(adjBtns[2]);
            destBtns.add(adjBtns[3]);
        }

        for (MatrixButton destBtn : destBtns) {
            if (destBtn != null && destBtn.getEntity() == null && !turn.getEntities().contains(destBtn.getEntity())) {
                destBtn.setBackground(new Color(153, 255, 153));
                destBtn.addActionListener(e -> moveCharacterAndReset(btn, destBtn, destBtns));
            }
        }

        clearPanel();
    }

    private void handleSkillOptions(MatrixButton btn) {
        removeAll();
        add(new SkillsPanel(btn, this, this.arena));
        revalidate();
        repaint();
    }

    private MatrixButton[] getAdjacentButtons(int originRow, int originCol, int i) {
        MatrixButton[] buttons = new MatrixButton[4];
        try {
            buttons[0] = matrixButtons[originRow][originCol - i];
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            buttons[1] = matrixButtons[originRow][originCol + i];
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            buttons[2] = matrixButtons[originRow - i][originCol];
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            buttons[3] = matrixButtons[originRow + i][originCol];
        } catch (ArrayIndexOutOfBoundsException ignored) {}

        return buttons;
    }

    public MatrixButton[] getAroundButtons(MatrixButton btn) {
        int originRow = Math.divideExact(btn.getIdentifier(), 10);
        int originCol = btn.getIdentifier() % 10;

        MatrixButton[] buttons = new MatrixButton[8];
        MatrixButton[] adjacents = getAdjacentButtons(originRow, originCol, 1);

        buttons[0] = adjacents[0];
        buttons[1] = adjacents[1];
        buttons[2] = adjacents[2];
        buttons[3] = adjacents[3];

        try {
            buttons[4] = matrixButtons[originRow - 1][originCol - 1];
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            buttons[5] = matrixButtons[originRow - 1][originCol + 1];
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            buttons[6] = matrixButtons[originRow + 1][originCol - 1];
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            buttons[7] = matrixButtons[originRow + 1][originCol + 1];
        } catch (ArrayIndexOutOfBoundsException ignored) {}

        return buttons;
    }

    private void moveCharacterAndReset(MatrixButton origin, MatrixButton dest, Iterable<MatrixButton> allBtns) {
        dest.setEntity(origin.getEntity(), match.getTeam1(), match.getTeam2());
        origin.removeEntity();
        resetButtons(allBtns);
        origin.setEnabled(true);
        arena.entityActed(dest.getEntity());
    }

    private void resetButtons(Iterable<MatrixButton> buttons) {
        for (MatrixButton btn : buttons) {
            if (btn == null) continue;
            btn.resetBackground();
            btn.removeActionListeners();
            btn.addActionListener(e -> arena.handleButtonClick(btn));
        }
    }

    private void attackEntity(MatrixButton origin, MatrixButton dest) {
        Entity enemy = dest.getEntity();

        enemy.takeDamage(origin.getEntity().getDamage());
        if (enemy.getHealth() <= 0) {
            dest.removeEntity();

            if (enemy instanceof Character) {
                turn.addDeadCharacter((Character) enemy);
                getArena().addToPendingRevive((Character) enemy);
            } else if (enemy instanceof Tower) {
                turn.getTowers().remove((Tower) enemy);

                if (arena.getMatch().isGameOver()) {
                    arena.endGame(turn.getPlayer());
                }
            }
        }
    }

    private void clearPanel() {
        removeAll();
        revalidate();
        repaint();
    }

    public Team getTurn() {
        return turn;
    }

    public BattleArena getArena() {
        return arena;
    }

    public MatrixButton[][] getButtons() {
        return matrixButtons;
    }
}
