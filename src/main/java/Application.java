import api.WaiterApi;
import api.services.CafeDatabase;

import static spark.Spark.*;


public class Application {


    public static void main(String[] args) {
        WaiterApi waiterApi = new WaiterApi(CafeDatabase.getInstance());
        path("/api/cafe", () -> {
            path("/waiters", () ->{
                get("", waiterApi.getWaiters());
                get("/exists", waiterApi.waiterExist());
                post("/add", waiterApi.addWaiter());
                delete("/delete", waiterApi.deleteWaiter());
            });
            });
        }
    }

