package org.example;

import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void error(String str){
        System.err.println(str);
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        Base base = new Base( new Coord( in.nextInt(), in.nextInt() ) );
        Base enemyBase;
        int heroesPerPlayer = in.nextInt(); // Always 3 ????
        Coord sizeField = new Coord(17630, 9000);
        if (base.location.posX == 0) {
            enemyBase = new Base(sizeField);
        } else {
            enemyBase = new Base(new Coord(0, 0));
        }

        // game loop
        while (true) {
            //for (int i = 0; i < 2; i++) {
            base.health = in.nextInt(); // Each player's base health
            base.mana = in.nextInt(); // Ignore in the first league; Spend ten mana to cast a spell
            enemyBase.health = in.nextInt();
            enemyBase.mana = in.nextInt();
            //}
            Heroes heroes = new Heroes();
            HashMap<Integer, Entity> monsters = new HashMap<>();
            // HashMap<Integer, Entity> villains = new HashMap<>();
            Targets targets = new Targets();

            int entityCount = in.nextInt(); // Amount of heros and monsters you can see
            for (int i = 0; i < entityCount; i++) {
                Entity entity = new Entity();

                entity.id = in.nextInt(); // Unique identifier
                int type = in.nextInt(); // 0=monster, 1=your hero, 2=opponent hero
                // System.err.println(entity.id + " " + type);

                entity.location = new Coord(in.nextInt(), in.nextInt()); // Position of this entity
                entity.shieldLife = in.nextInt(); // Ignore for this league; Count down until shield spell fades
                entity.isControlled = in.nextInt(); // Ignore for this league; Equals 1 when this entity is under a control spell
                entity.health = in.nextInt(); // Remaining health of this monster
                entity.vx = in.nextInt(); // Trajectory of this monster
                entity.vy = in.nextInt();
                entity.nearBase = in.nextInt(); // 0=monster with no target yet, 1=monster targeting a base
                entity.threatFor = in.nextInt(); // Given this monster's trajectory, is it a threat to 1=your base, 2=your opponent's base, 0=neither

                if (type == 0) { //monster
                    int distanceToBase = entity.location.distanceTo(base.location);
                    monsters.put(entity.id, entity);
                    targets.addPriority(entity.id, distanceToBase, entity.threatFor);
                }
                if (type == 1) { //heroes
                    Hero hero = new Hero();
                    hero.entity = entity;
                    heroes.heroes.add(hero);
                }
                // if (type == 2) { //heroes
                //     villains.put(entity.id, entity);
                // }

            }

            targets.sortDistances();

            if (targets.priorities.size() > 1) {
                for (Priority priority: targets.priorities) {
                    error("id: " + priority.id + ", dist:"+ priority.distanceToBase);
                }
            }

            int minManaStored = 50;
            int rangeWind = 1250;

            for (Priority priority: targets.priorities) {
                if (heroes.freeHeroes == 0){
                    break;
                }
                Entity enemy = monsters.get(priority.id);
                if (priority.distanceToBase < 1200) {
                    Hero selectedHero = heroes.assignHero(monsters.get(priority.id));
                    int distanceToEnemy = selectedHero.entity.location.distanceTo(enemy.location);
                    if (base.mana>10 && distanceToEnemy<rangeWind) {
                        selectedHero.sendWind = true;
                        Coord sendWindTo = selectedHero.entity.location.moveMe(base.delta()*10, base.delta()*10);
                        selectedHero.nextMove = sendWindTo;
                    }
                    continue;
                }
                if (priority.distanceToBase < 5000) {
                    heroes.assignHero(monsters.get(priority.id));
                    continue;
                }
                if (priority.distanceToBase < 8000) {
                    heroes.assignHero(monsters.get(priority.id));
                    continue;
                }
                break;
            }

            ArrayList<Coord> defaults = new ArrayList<>();
            int delta = base.delta() * 4500;
            defaults.add(new Coord(base.location.posX + delta, base.location.posX + delta));
            defaults.add(new Coord(base.location.posX, base.location.posX + delta));
            defaults.add(new Coord(base.location.posX + delta, base.location.posX));

            error("" + defaults.get(0));

            for (int i = 0; i < heroesPerPlayer; i++) {

                Hero hero = heroes.heroes.get(i);
                if (hero.targetId == -1) {  //Sin objetivo
                    hero.nextMove = defaults.get(0);
                    defaults.remove(0);
                } else {
                    if (hero.sendWind) {    //Habilitado para hacer SPELL WIND
                        System.out.println("SPELL WIND " + hero.nextMove.posX + " " + hero.nextMove.posY);
                        continue;
                    } else {                //SOLO ATACAR
                        hero.nextMove = monsters.get(hero.targetId).getNextMove();
                    }
                }
                System.out.println("MOVE " + hero.nextMove.posX + " " + hero.nextMove.posY);

            }
        }
    }

}

class Hero {
    public Entity entity;
    public int targetId;
    public Coord defaultLocation;
    public boolean isDefending;
    public Coord nextMove;
    public boolean sendWind;
    public Hero(){
        this.defaultLocation = new Coord(4000, 4000);
        this.targetId = -1;
        this.isDefending = false;
        this.sendWind = false;
    }
}

class Heroes {
    public ArrayList<Hero> heroes;
    public int freeHeroes;
    public Heroes() {
        heroes = new ArrayList<>();
        freeHeroes = 3;
    }
    public Hero assignHero(Entity monster) {
        int selectedHero = -1;
        int bestDistance = 20000;
        for (int i=0; i<heroes.size(); i++) {
            Hero hero = heroes.get(i);
            if (hero.targetId != -1) {
                continue;
            }
            int distanceToMonster = heroes.get(i).entity.location.distanceTo(monster.location);
            if (distanceToMonster < bestDistance) {
                selectedHero = i;
            }
        }
        heroes.get(selectedHero).targetId = monster.id;
        this.freeHeroes--;
        System.err.println(monster.id + " asignado al heroe:" + selectedHero);
        return heroes.get(selectedHero);
    }
}

class Priority {
    public int id;
    public int distanceToBase;
    public boolean isUrgent;
    public boolean isTarget;
    public Priority(int id, int distanceToBase, boolean isUrgent) {
        this.id = id;
        this.distanceToBase = distanceToBase;
        this.isUrgent = isUrgent;
        this.isTarget = false;
    }
    public int getDistance(){
        return this.distanceToBase;
    }
}

class Targets {
    public ArrayList<Priority> priorities;
    public Targets(){
        priorities = new ArrayList<>();
    }
    public void addPriority(int id, int distanceToBase, int threatFor) {
        boolean isUrgent=false;
        if (threatFor == 1) {//mybase
            isUrgent = true;
        }
        priorities.add(new Priority(id, distanceToBase, isUrgent));
    }
    public void sortDistances(){
        Comparator<Priority> byUrgent = Comparator.comparing(p -> !p.isUrgent);
        Comparator<Priority> byDistance = Comparator.comparing(p -> p.distanceToBase);
        Comparator<Priority> allOptions = byUrgent.thenComparing(byDistance);
        Collections.sort(priorities, allOptions);
    }
}

class Entity {

    public Coord location;
    public int id;
    public int shieldLife;
    public int isControlled;
    public int health;
    public int vx;
    public int vy;
    public int nearBase;
    public int threatFor;
    //public int distanceToBase;
    public Entity(){}
    public Coord getNextMove(){
        return new Coord(location.posX + vx, location.posY + vy);
    }

}

class Coord {

    public int posX;
    public int posY;

    public Coord(int x, int y) {
        this.posX = x;
        this.posY = y;
    }
    public int distanceTo(Coord anotherCoord) {
        int dX = this.posX - anotherCoord.posX;
        int dY = this.posY - anotherCoord.posY;
        return (int) Math.sqrt( dX*dX + dY*dY );
    }
    public Coord moveMe(int dX, int dY) {
        return (new Coord(this.posX+dX, this.posY+dY));
    }

}

class Base {

    public Coord location;
    public int health = 0;
    public int mana = 0;

    public Base(Coord coord) {
        this.location = coord;
    }

    public int delta() {
        if (location.posX == 0) {
            return 1;
        } else {
            return -1;
        }
    }

}

