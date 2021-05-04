package api;

import api.services.CafeDatabase;
import api.services.models.Waiter;
import api.services.response.Response;
import com.google.gson.Gson;
import spark.Request;
import spark.Route;

import static api.services.response.ResponseType.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static spark.route.HttpMethod.get;

public class WaiterApi {

    private CafeDatabase cafeDatabase;

    public WaiterApi(CafeDatabase cafeDatabase) {
        this.cafeDatabase = cafeDatabase;
    }

    public Route getWaiters() {
        return (req, res) -> {
            res.type("application/json");
            try {
                List<Waiter> waiterList = cafeDatabase.getWaiters();
                Response response = new Response(SUCCESS, waiterList);
                return new Gson().toJson(response);

            } catch (SQLException e) {
                Response response = new Response(ERROR, e.getMessage());
                return new Gson().toJson(response);
            }
        };
    }

    public Route waiterExist(){
        return (req, res) -> {
          res.type("application/json");

          String waiterName = req.queryParams("waiter_name").toLowerCase();
          try {
              Boolean waiterExists = cafeDatabase.waiterExists(waiterName);
              Response response = new Response(SUCCESS, waiterExists);
              return new Gson().toJson(response);

          }catch (SQLException e) {
              Response response = new Response(ERROR, e.getMessage());
              return new Gson().toJson(response);
          }
        };
    }

    public Route addWaiter(){
        return (req, res) -> {
            Waiter waiter = new Gson().fromJson(req.body(), Waiter.class);
            try{
                Map<String, String> addedWaiter = cafeDatabase.addWaiter(waiter.getWaiterName());
                Response response = new Response(SUCCESS, addedWaiter.get("message"), addedWaiter.get("result"));
                return new Gson().toJson(response);
            }
            catch (SQLException e){
                Response response = new Response(ERROR, e.getMessage());
                return new Gson().toJson(response);
            }
        };
    }

    public Route deleteWaiter(){
        return (req, res) -> {
            Waiter waiter = new Gson().fromJson(req.body(), Waiter.class);
            try{
                Map<String, String> deletedWaiter = cafeDatabase.deleteWaiter(waiter);
                Response response = new Response(SUCCESS, deletedWaiter.get("message"), deletedWaiter.get("result"));
                return new Gson().toJson(response);
            }
            catch (SQLException e){
                Response response = new Response(ERROR, e.getMessage());
                return new Gson().toJson(response);
            }
        };

    }

}
