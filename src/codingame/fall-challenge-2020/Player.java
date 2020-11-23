import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int gameTurn = 0;

        // game loop
        while (true) {
            gameTurn++;
            int actionCount = in.nextInt(); // the number of spells and recipes in play
            List<Action> actionList = new ArrayList<>();
            List<Boolean> boolList = new ArrayList<>();
            List<Boolean> learnedList = new ArrayList<>();
            int castPower = 0;
            int castCount = 0;
            for (int i = 0; i < actionCount; i++) {
                int actionId = in.nextInt(); // the unique ID of this spell or recipe
                String actionType = in.next(); // in the first league: BREW; later: CAST, OPPONENT_CAST, LEARN, BREW
                int delta0 = in.nextInt(); // tier-0 ingredient change
                int delta1 = in.nextInt(); // tier-1 ingredient change
                int delta2 = in.nextInt(); // tier-2 ingredient change
                int delta3 = in.nextInt(); // tier-3 ingredient change
                int price = in.nextInt(); // the price in rupees if this is a potion
                int tomeIndex = in.nextInt(); // in the first two leagues: always 0; later: the index in the tome if this is a tome spell, equal to the read-ahead tax; For brews, this is the value of the current urgency bonus
                int taxCount = in.nextInt(); // in the first two leagues: always 0; later: the amount of taxed tier-0 ingredients you gain from learning this spell; For brews, this is how many times you can still gain an urgency bonus
                boolean castable = in.nextInt() != 0; // in the first league: always 0; later: 1 if this is a castable player spell
                boolean repeatable = in.nextInt() != 0; // for the first two leagues: always 0; later: 1 if this is a repeatable player spell
                Action action = new Action(actionId, actionType, delta0, delta1, delta2, delta3, price, tomeIndex, taxCount, castable, repeatable, 1);
                if (action.type != ActionType.OPPONENT_CAST) {
                    actionList.add(action);
                    boolList.add(!(action.type == ActionType.CAST && !castable));
                    learnedList.add(true);
                    if (action.type == ActionType.LEARN) {
                        actionList.add(new Action(-1, ActionType.CAST.str, delta0, delta1, delta2, delta3, price, 0, 0, true, repeatable, 1));
                        boolList.add(true);
                        learnedList.add(false);
                    }
                    if (action.type == ActionType.CAST) {
                        castPower += action.delta[0] + action.delta[1] * 2 + action.delta[2] * 3 + action.delta[3] * 4;
                        castCount++;
                    }
                }
            }
            Queue<State> queue = new ArrayDeque<>();
            for (int i = 0; i < 2; i++) {
                int inv0 = in.nextInt(); // tier-0 ingredients in inventory
                int inv1 = in.nextInt();
                int inv2 = in.nextInt();
                int inv3 = in.nextInt();
                if (i == 0) { // player
                    int[] inv = new int[]{inv0, inv1, inv2, inv3};
                    boolean[] castable = new boolean[boolList.size()];
                    for (int id = 0; id < boolList.size(); id++) {
                        castable[id] = boolList.get(id);
                    }
                    boolean[] learned = new boolean[learnedList.size()];
                    for (int id = 0; id < learnedList.size(); id++) {
                        learned[id] = learnedList.get(id);
                    }
                    queue.add(new State(gameTurn, inv, 0, castable, learned, null, null, castPower, castCount));
                }
                int score = in.nextInt(); // amount of rupees
            }
            State maxState = queue.peek();
            long startTime = System.nanoTime();
            {
                State state = queue.peek();
                for (int actionId = 0; actionId < actionList.size(); actionId++) {
                    Action action = actionList.get(actionId);
                    if (action.type == ActionType.LEARN && action.isAble(state.delta)) {
                        State nextState = state.clone();
                        nextState.turn++;
                        nextState.learned[actionId] = false;
                        nextState.learned[actionId + 1] = true;
                        nextState.delta[0] -= action.tomeIndex;
                        nextState.delta[0] += action.taxCount;
                        nextState.path = action;
                        nextState.castPower += action.delta[0] + action.delta[1] * 2 + action.delta[2] * 3 + action.delta[3] * 4;
                        nextState.castCount++;
                        queue.add(nextState);
                        if (maxState.score() < nextState.score()) {
                            maxState = nextState;
                        }
                    }
                }
            }
            while (!queue.isEmpty()) {
                State state = queue.poll();
                for (int actionId = 0; actionId < actionList.size(); actionId++) {
                    Action action = actionList.get(actionId);
                    if (action.type == ActionType.LEARN) continue;
                    Action baseAction = action;
                    while (state.castable[actionId] && state.learned[actionId] && action.isAble(state.delta)) {
                        State nextState = state.clone();
                        nextState.turn++;
                        if (action.type == ActionType.CAST) {
                            nextState.castable[actionId] = false;
                            for (int id = 0; id < 4; id++) {
                                nextState.delta[id] += action.delta[id];
                            }
                        }
                        if (action.type == ActionType.BREW) {
                            nextState.learned[actionId] = false;
                            for (int id = 0; id < 4; id++) {
                                nextState.delta[id] += action.delta[id];
                            }
                            int k = 20;
                            if (30 < nextState.turn) {
                                k = nextState.turn < 50 ? 50 - nextState.turn : 0;
                            }
                            nextState.rupees += action.price * k / 20;
                        }
                        nextState.path = action;
                        queue.add(nextState);
                        if (maxState.score() < nextState.score()) {
                            maxState = nextState;
                        }
                        if (action.repeatable && action.type == ActionType.CAST && action.times < 5) {
                            action = baseAction.repeat(action.times + 1);
                        } else {
                            break;
                        }
                    }
                }
                {
                    State nextState = state.clone();
                    nextState.turn++;
                    for (int actionId = 0; actionId < actionList.size(); actionId++) {
                        nextState.castable[actionId] = true;
                    }
                    nextState.path = new Action(-1, ActionType.REST.str, 0, 0, 0, 0, 0, 0, 0, true, false, 1);
                    queue.add(nextState);
                    if (maxState.score() < nextState.score()) {
                        maxState = nextState;
                    }
                }

                if (System.nanoTime() - startTime > 20L * 1000 * 1000) {
                    break;
                }
            }
            Action bestAction = null;
            while (maxState.path != null && maxState.prev != null) {
                bestAction = maxState.path;
                maxState = maxState.prev;
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            // in the first league: BREW <id> | WAIT; later: BREW <id> | CAST <id> [<times>] | LEARN <id> | REST | WAIT
            if (bestAction == null) {
                System.out.println("REST");
            } else {
                System.out.println(bestAction);
            }
        }
    }
}

enum ActionType {
    CAST("CAST"),
    OPPONENT_CAST("OPPONENT_CAST"),
    LEARN("LEARN"),
    BREW("BREW"),
    REST("REST");

    String str;
    ActionType(String str) {
        this.str = str;
    }
    public static ActionType of(String str) {
        for (ActionType type : ActionType.values()) {
            if (type.str.equals(str)) {
                return type;
            }
        }
        return null;
    }
}

class State {
    int turn;
    int[] delta;
    int rupees;
    boolean[] castable;
    boolean[] learned;
    State prev;
    Action path;
    int castPower;
    int castCount;
    public State(int turn, int[] delta, int rupees, boolean[] castable, boolean[] learned, State prev, Action path, int castPower, int castCount) {
        this.turn = turn;
        this.delta = delta;
        this.rupees = rupees;
        this.castable = castable;
        this.learned = learned;
        this.prev = prev;
        this.path = path;
        this.castPower = castPower;
        this.castCount = castCount;
    }
    public int score() {
        int restTurn = turn < 40 ? 40 - turn : 0;
        int score = 100 * castPower * restTurn / (castCount + 1);
        score += 100 * delta[0] * restTurn / 40;
        score += delta[1] * (100 + 100 * restTurn / 40);
        score += delta[2] * (100 + 200 * restTurn / 40);
        score += delta[3] * (100 + 300 * restTurn / 40);
        score += 100 * rupees;
        return score;
    }
    @Override
    public State clone() {
        int[] delta = new int[]{this.delta[0], this.delta[1], this.delta[2], this.delta[3]};
        boolean[] castable = new boolean[this.castable.length];
        for (int i = 0; i < this.castable.length; i++) {
            castable[i] = this.castable[i];
        }
        boolean[] learned = new boolean[this.learned.length];
        for (int i = 0; i < this.learned.length; i++) {
            learned[i] = this.learned[i];
        }
        return new State(turn, delta, rupees, castable, learned, this, null, castPower, castCount);
    }
}

class Action {
    int id;
    ActionType type;
    int[] delta;
    int price;
    int tomeIndex;
    int taxCount;
    boolean castable;
    boolean repeatable;
    int times;
    public Action(int id, String actionType, int delta0, int delta1, int delta2, int delta3, int price, int tomeIndex, int taxCount, boolean castable, boolean repeatable, int times) {
        this.id = id;
        this.type = ActionType.of(actionType);
        this.delta = new int[4];
        this.delta[0] = delta0 * times;
        this.delta[1] = delta1 * times;
        this.delta[2] = delta2 * times;
        this.delta[3] = delta3 * times;
        this.price = price;
        this.tomeIndex = tomeIndex;
        this.taxCount = taxCount;
        this.castable = castable;
        this.repeatable = repeatable;
        this.times = times;
    }
    public boolean isAble(int[] inv) {
        if (type == ActionType.OPPONENT_CAST) {
            return false;
        }
        if (type == ActionType.LEARN && tomeIndex > inv[0]) {
            return false;
        }
        int total = 0;
        for (int i = 0; i < 4; i++) {
            int sum = inv[i] + delta[i];
            if (sum < 0 && type != ActionType.LEARN) {
                return false;
            }
            total += sum;
        }
        if (total - tomeIndex + taxCount > 10) {
            return false;
        }
        return true;
    }

    public Action repeat(int times) {
        return new Action(id, type.str, delta[0], delta[1], delta[2], delta[3], price, tomeIndex, taxCount, castable, repeatable, times);
    }

    @Override
    public String toString() {
        if (type == ActionType.REST) return "REST";
        return type.str + " " + id + (repeatable && type == ActionType.CAST ? " " + times : "");
    }
}