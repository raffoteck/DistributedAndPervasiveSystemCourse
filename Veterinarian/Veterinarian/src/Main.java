import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int maxAnimals = 30;
        Random random = new Random();
        waitingRoom.setInstance();
        for (int i = 0; i < maxAnimals; i++) {
            Animal animal = new Animal(random.nextBoolean(), i);
            animal.start();
        }
    }
}