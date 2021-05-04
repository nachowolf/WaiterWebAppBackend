package api.services.models;

public class Waiter {
    private long waiterID;
    private String waiterName;

    public Waiter(long waiterID, String waiterName) {
        this.waiterID = waiterID;
        this.waiterName = waiterName;
    }

    public Waiter(String waiterName) {
        this.waiterName = waiterName;
    }

    public long getWaiterID() {
        return waiterID;
    }

    public String getWaiterName() {
        return waiterName;
    }

    @Override
    public String toString() {
        return "Waiter{" +
                "waiterID=" + waiterID +
                ", waiterName='" + waiterName + '\'' +
                '}';
    }
}
