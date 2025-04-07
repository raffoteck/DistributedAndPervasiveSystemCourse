
public class Reservations {
    private static Reservations instance;
    private int maxTickets;
    private int availableTickets;
    Reservations(int maxTickets) {
        this.maxTickets = maxTickets;
        this.availableTickets = maxTickets;
    }

    public static void setInstance(int maxTickets) {
        if (instance == null) {
            instance = new Reservations(maxTickets); // Viene creata solo una volta
        }
    }

    public static Reservations getInstance()
    {
        return instance;
    }


    public synchronized int reserveTicket() {
        if (this.availableTickets > 0) {
            // Decrementa i biglietti disponibili in modo atomico
            this.availableTickets--;
            System.out.println("Biglietto prenotato. Biglietti disponibili: " + this.availableTickets);
            return this.maxTickets-this.availableTickets;
        } else {
            System.out.println("Biglietti esauriti!");
            return 0;
        }
    }
}
