package by.yarzl.service;

import by.yarzl.db.DataProvider;
import by.yarzl.db.DemoEntity;
import java.sql.SQLException;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


//сервис обрабатывает, проверяет на ошибки
public class DataService {
    private final DataProvider dataProvider;

    public DataService() {
        dataProvider = DataProvider.getProvider();
    }

    public String getAllEntitiesForResponse() throws SQLException, ClassNotFoundException {

        List<DemoEntity> entities = dataProvider.getAllEntities();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(entities);

    }

    public void addEntity(String entityString) throws SQLException, ClassNotFoundException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        DemoEntity demoEntity = gson.fromJson(entityString, DemoEntity.class);
        dataProvider.addEntity(demoEntity);

    }

    public String deleteEntity(String id) throws Exception{

        int intId = Integer.parseInt(id);
        dataProvider.deleteEntityById(intId);


        String massage;

        return massage;

    }
}
