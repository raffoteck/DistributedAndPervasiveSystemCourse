import java.util.ArrayList;
import java.util.List;

public class waitingRoom {
    private List<Boolean> animals;
    private static waitingRoom room;
    public waitingRoom() {
        this.animals = new ArrayList<>();
    }

    public static void setInstance() {
        if (room == null) {
            room = new waitingRoom();
        }
    }

    public static waitingRoom getInstance() {
        return room;
    }

    public synchronized void enterRoom(boolean isDog, int id) throws InterruptedException {
        boolean manage = false;
        while(!manage){
            if (canEnterRoom(isDog)) {
                this.animals.add(isDog);
                manage = true;
                System.out.println("Animali dopo inserimento " + id + " nella stanza: ");
                for (int i = 0; i < animals.size(); i++) {
                    String tipo = animals.get(i) ? "Cane" : "Gatto";
                    System.out.print((i + 1) + "." + tipo + " ");
                }
            }
            else {
                wait();
            }
        }


    }

    public synchronized void exitRoom(boolean isDog, int id)
    {
        this.animals.remove(animals.size() - 1);
        System.out.println("Animali dopo rimozione " + id + " nella stanza: ");
        for (int i = 0; i < animals.size(); i++) {
            String tipo = animals.get(i) ? "Cane" : "Gatto";
            System.out.print((i + 1) + "." + tipo + " ");
        }
        notifyAll();
    }

    private synchronized boolean canEnterRoom(boolean isDog) {
        if (!isDog) { // è un gatto
            if (this.animals.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } else { // è un cane
            // Non deve esserci nessun gatto
            if (this.animals.contains(false)) {
                return false;
            }

            // Conta i cani nella stanza
            long numCani = this.animals.stream().filter(a -> a).count();

            if (numCani < 4) {
                return true;
            } else {
                return false;
            }
        }

    }

}
