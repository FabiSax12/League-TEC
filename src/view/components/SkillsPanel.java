package view.components;

import models.ASkill;
import models.Character;
import models.Entity;
import models.Tower;
import models.skills.AttackSkill;
import models.skills.BuffSkill;
import models.skills.HealSkill;
import view.BattleArena;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

class SkillCard extends JButton {
    public SkillCard(ASkill skill) {
        setBackground(new Color(0, 111, 238));
        setLayout(new GridLayout(0, 1, 0, 0));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(fieldData(skill.getName(), 18));
        add(fieldData("Mana: " + skill.getManaCost(), 14));

        switch (skill) {
            case AttackSkill attackSkill -> {
                add(fieldData("Type: Attack", 14));
                add(fieldData("Damage: " + attackSkill.getDamage(), 14));
            }
            case BuffSkill buffSkill -> {
                add(fieldData("Type: Buff", 14));
                add(fieldData("Boost factor: " + buffSkill.getBoost(), 14));
                add(fieldData("Stat to boost " + buffSkill.getStat(), 14));
            }
            case HealSkill healSkill -> {
                add(fieldData("Type: Heal", 14));
                add(fieldData("Heal" + healSkill.getHealAmount(), 14));
            }
            default -> {
            }
        }
    }

    private JLabel fieldData(String text, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Consolas", Font.PLAIN, size));
        label.setForeground(Color.WHITE);
        return label;
    };
}

public class SkillsPanel extends JPanel {
    private final MatrixButton button;
    private final ActionsPanel actionsPanel;
    private final Character character;
    private final BattleArena arena;

    public SkillsPanel(MatrixButton btn, ActionsPanel actionsPanel, BattleArena arena) {
        this.button = btn;
        this.character = (Character) btn.getEntity();
        this.actionsPanel = actionsPanel;
        this.arena = arena;

        setLayout(new GridLayout(0, 1, 0, 10));

        for (ASkill skill : character.getSkills()) {
            SkillCard skillCard = new SkillCard(skill);
            if (character.getMana() - skill.getManaCost() < 0) skillCard.setEnabled(false);
            skillCard.addActionListener(e -> handleSkillAction(skill));
            add(skillCard);
        }
    }

    private void handleSkillAction(ASkill skill) {
        if (skill instanceof AttackSkill) {
            handleAttackSkill((AttackSkill) skill);
        } else if (skill instanceof HealSkill) {
            handleHealSkill((HealSkill) skill);
        } else if (skill instanceof BuffSkill) {
            handleBuffSkill((BuffSkill) skill);
        }
        removeAll();
        revalidate();
        repaint();
    }

    private void handleAttackSkill(AttackSkill skill) {
        MatrixButton[] aroundButtons = actionsPanel.getAroundButtons(button);
        ArrayList<MatrixButton> nearbyEnemies = new ArrayList<>();

        for (MatrixButton b : aroundButtons) {
            try{
                if (b.getEntity() != null && !actionsPanel.getTurn().getEntities().contains(b.getEntity()))
                    nearbyEnemies.add(b);
            }catch (NullPointerException ex){
                System.out.println("NULL");
            }
        }

        nearbyEnemies.forEach(enemy -> {
            if (enemy != null && !actionsPanel.getTurn().getEntities().contains(enemy.getEntity())) {
                enemy.setFilter(new Color(255, 102, 102, 100));
                enemy.addActionListener(e -> {
                    Entity entity = enemy.getEntity();

//                    skill.use(character, entity);
                    character.useSkill(skill, entity);
                    if (entity.getHealth() <= 0) {
                        enemy.removeEntity();

                        if (entity instanceof Character) {
                            actionsPanel.getTurn().addDeadCharacter((Character) entity);
                            actionsPanel.getArena().addToPendingRevive((Character) entity);
                            entity.getTeam().getPlayer().getStatistics().incrementDeadCharacters();
                            character.levelUp();
                        } else if (entity instanceof Tower) {
                            actionsPanel.getTurn().getTowers().remove((Tower) entity);
                            entity.getTeam().getPlayer().getStatistics().incrementDestroyedTowers();
                            entity.getTeam().getTowers().remove((Tower) entity);

                            if (arena.getMatch().isGameOver()) {
                                arena.endGame(actionsPanel.getTurn().getPlayer());
                            }
                        }
                    }
                    arena.entityActed(character);
                    arena.revalidate();
                    arena.repaint();
                    enemy.refresh();

                    nearbyEnemies.forEach(noClickedEnemy -> {
                        noClickedEnemy.removeActionListeners();
                        noClickedEnemy.resetFilter(
                                arena.getMatch().getTeam1(),
                                arena.getMatch().getTeam2()
                        );
                        noClickedEnemy.addActionListener(__ -> arena.handleButtonClick(noClickedEnemy));
                    });
                });
            }
        });
    }

    private void handleHealSkill(HealSkill skill) {
        ArrayList<MatrixButton> aliesButtons = new ArrayList<>();

        for (MatrixButton[] row : actionsPanel.getButtons()) {
            for (MatrixButton b : row) {
                if (b.getEntity() instanceof Character && actionsPanel.getTurn().getEntities().contains(b.getEntity())) {
                    aliesButtons.add(b);
                }
            }
        }

        aliesButtons.forEach(btn -> {
            btn.setFilter(new Color(102, 204, 255, 100));

            btn.addActionListener(e -> {
//                skill.use(character, btn.getEntity());
                character.useSkill(skill, btn.getEntity());

                aliesButtons.forEach(aly -> {
                    aly.resetFilter(
                            arena.getMatch().getTeam1(),
                            arena.getMatch().getTeam2()
                    );
                    aly.removeActionListeners();
                    aly.addActionListener(__ -> arena.handleButtonClick(aly));
                });

                arena.entityActed(character);
                arena.repaint();
                button.refresh();
                btn.refresh();
            });
        });
    }

    private void handleBuffSkill(BuffSkill skill) {
//        skill.use(character, character);
        character.useSkill(skill, character);
        arena.entityActed(character);
        arena.repaint();
        button.removeActionListeners();
        button.addActionListener(e -> arena.handleButtonClick(button));
        button.refresh();
    }
}

