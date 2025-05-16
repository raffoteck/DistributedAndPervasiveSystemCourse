public class Animal extends Thread {
    private final boolean isDog;
    private final waitingRoom room;
    private final int id;

    public Animal(boolean isDog, int id) {
        this.isDog = isDog;
        this.id = id;
        room = waitingRoom.getInstance();
    }
    public boolean isDog() {
        return isDog;
    }

    public void run() {
        try {
            this.room.enterRoom(this.isDog, this.id);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.room.exitRoom(this.isDog, this.id);

    }
}
