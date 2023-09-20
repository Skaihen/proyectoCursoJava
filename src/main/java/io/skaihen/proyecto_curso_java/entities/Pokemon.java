package io.skaihen.proyecto_curso_java.entities;

import io.skaihen.proyecto_curso_java.classes.Move;
import io.skaihen.proyecto_curso_java.classes.Types;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Entity
public class Pokemon {

    @Transient
    @NotBlank(message = "HasMoves is required")
    private final Move[] hasMoves = new Move[4];
    private final Random random = new Random();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Type1 is required")
    private String type1;
    @NotBlank(message = "Type2 is required")
    private String type2;
    @NotBlank(message = "Level is required")
    private int level;
    @NotBlank(message = "Hp is required")
    private int hp;
    @NotBlank(message = "Attack is required")
    private int attack;
    @NotBlank(message = "Defense is required")
    private int defense;
    @NotBlank(message = "SpecialAttack is required")
    private int specialAttack;
    @NotBlank(message = "SpecialDefense is required")
    private int specialDefense;
    @NotBlank(message = "Speed is required")
    private int speed;

    protected Pokemon() {
    }

    public Pokemon(Long id, String name, String type1, String type2, int level, int attack, int defense, int specialAttack, int specialDefense, int speed) {
        this.id = id;
        this.name = name;
        this.type1 = type1;
        this.type2 = type2;
        this.level = level;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
    }

    /**
     * Calcula el daño recibido por un ataque enemigo y lo resta a la vida del
     * pokemon.
     *
     * @param move         Movimiento realizado por el enemigo.
     * @param enemyPokemon Pokemon enemigo.
     */
    public void receiveAttack(Move move, Pokemon enemyPokemon) {

        // Cambiar ecuación de daño a [0.5 * power * attack/defense * type * stab *
        // random(0.75,1)] + 1

        Map<String, Double> typeChart = Types.getTypeChart(move.getType());

        int totalDamage = "physical".equals(move.getCategory())
                ? ((((((2 * enemyPokemon.getLevel()) / 5) + 2) * move.getPower() * enemyPokemon.getAttack()
                / getDefense()) / 50) + 2)
                : ((((((2 * enemyPokemon.getLevel()) / 5) + 2) * move.getPower() * enemyPokemon.getSpecialAttack()
                / getSpecialDefense()) / 50) + 2);

        if (Objects.equals(enemyPokemon.getType1(), move.getType())
                || Objects.equals(enemyPokemon.getType2(), move.getType())) {
            totalDamage *= 1.5;
        }

        for (Map.Entry<String, Double> entry : typeChart.entrySet()) {
            if (Objects.equals(entry.getKey(), this.getType1()) || Objects.equals(entry.getKey(), this.getType2())) {
                totalDamage = (int) Math.round(totalDamage * entry.getValue());
            }
        }

        if (totalDamage >= 1) {
            totalDamage = totalDamage * (this.getRandom().nextInt(39) + 217) / 255;
        }

        this.hp -= totalDamage;
    }

    public Random getRandom() {
        return random;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public int getLevel() {
        return level;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public Move[] getHasMoves() {
        return hasMoves;
    }
}
