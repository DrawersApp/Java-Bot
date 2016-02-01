package dictionary.bot;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by harshit on 27/1/16.
 */
public class OperationsManager {

    public void registerOperations(OperationsType operationsType, Class<? extends Operation> operations) {
        operationMap.put(operationsType, new OperationsMapValues(operations));
    }

    public static OperationsManager getOperationsManager() {
        return operationsManager;
    }

    private static OperationsManager operationsManager = new OperationsManager();

    private Map<OperationsType, OperationsMapValues> operationMap = new HashMap<>();

    public DrawersBotString getDrawersBotString(String inputString) {
        DrawersBotString drawersBotString = DrawersBotString.fromString(inputString);
        return drawersBotString;
    }

    public OutputBody performOperations(DrawersBotString drawersBotString) {
        if (drawersBotString.getBotStringElements().isEmpty()) {
            return null;
        }
        String type = drawersBotString.getOperationsType();
        OperationsType operationsType = OperationsType.valueOf(type);
        for (Map.Entry<OperationsType, OperationsMapValues> entry : operationMap.entrySet()) {
            if (entry.getKey() == operationsType) {
                try {
                    return entry.getValue().operations.newInstance().makeRestCall(drawersBotString);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public class OperationsMapValues {
        private Class<? extends Operation> operations;

        public OperationsMapValues(Class<? extends Operation> operations) {
            this.operations = operations;
        }
    }

}
