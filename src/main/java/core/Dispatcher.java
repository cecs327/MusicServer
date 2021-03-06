package core; /**
 * The Dispatcher implements DispatcherInterface.
 *
 * @author Oscar Morales-Ponce
 * @version 0.15
 * @since 02-11-2019
 */

import java.util.HashMap;
import java.util.*;
import java.lang.reflect.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;


public class Dispatcher implements DispatcherInterface {
    private static final Logger LOGGER = Logger.getLogger(Dispatcher.class);
    private HashMap<String, Object> dispatchers;

    public Dispatcher() {
        dispatchers = new HashMap<String, Object>();
    }

    /*
    * dispatch: Executes the remote method in the corresponding Object
    * @param request: Request: it is a Json file
    {
        "remoteMethod":"getSongChunk",
        "objectName":"SongServices",
        "param":
          {
              "song":490183,
              "fragment":2
          }
    }
    */

    public synchronized String dispatch(String request) {
        JsonParser parser = new JsonParser();
        JsonObject jsonRequest = parser.parse(request).getAsJsonObject();

        String callSemantic = jsonRequest.get("callSemantic").getAsString();
        String requestId = jsonRequest.get("requestId").getAsString();

        // Check cache before retrieving other fields, since the response might already exist.
        if (callSemantic.equals("AT_MOST_ONCE")) {
            if (Server.requestCache.containsKey(requestId)) {
                return Server.requestCache.get(requestId);
            }
        }

        Dispatcher dispatcher = getDispatcher(jsonRequest);
        Method method = getMethodFromJson(dispatcher, jsonRequest);
        Class[] types = method.getParameterTypes();

        String[] strParams = stringifyParametersToArray(jsonRequest);
        Object[] parameters = typifyParameters(strParams, types);

        String response = getRemoteMethodResponseObject(dispatcher, method, parameters).toString();

        Server.requestCache.put(requestId, response);

        return response;
    }

    private Dispatcher getDispatcher(JsonObject jsonRequest) {
        String dispatcherString = jsonRequest.get("dispatcher").getAsString();
        return (Dispatcher) dispatchers.get(dispatcherString);
    }

    // TODO: Extract to class
    private Method getMethodFromJson(Dispatcher dispatcher, JsonObject jsonRequest) {
        Method[] methods = dispatcher.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(jsonRequest.get("method").getAsString()))
                return method;
        }

        return null;
    }

    // TODO: Extract to class
    public String[] stringifyParametersToArray(JsonObject jsonRequest) {
        JsonObject jsonParams = jsonRequest.get("params").getAsJsonObject();

        Set<Map.Entry<String, JsonElement>> entrySet = jsonParams.entrySet();

        String[] params = new String[entrySet.size()];

        int i = 0;
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            params[i++] = entry.getValue().getAsString();
        }

        return params;
    }

    // TODO: Extract to class
    public Object[] typifyParameters(String[] parameters, Class[] types) {
        Object[] typedParameters = new Object[parameters.length];

        for(int i = 0; i < parameters.length; i++) {
            switch (types[i].getCanonicalName()) {
                case "java.lang.Long":
                    typedParameters[i] = Long.parseLong(parameters[i]);
                    break;
                case "java.lang.Integer":
                    typedParameters[i] = Integer.parseInt(parameters[i]);
                    break;
                case "java.lang.String":
                    typedParameters[i] = parameters[i];
                    break;
            }
        }

        return typedParameters;
    }

    private JsonObject getRemoteMethodResponseObject(Dispatcher dispatcher, Method method, Object[] parameters) {
        JsonObject jsonReturn = new JsonObject();

        try {
            String ret = method.invoke(dispatcher, parameters).toString();
            jsonReturn.addProperty("ret", ret);
        } catch (Exception e) {
            LOGGER.error("getRemoteMethodResponseObject Method Error: ", e);
            jsonReturn.addProperty("error", "Error while trying to invoke remote method.");
        }

        return jsonReturn;
    }

    /*
     * registerObject: It register the objects that handle the request
     * @param remoteMethod: It is the name of the method that
     *  objectName implements.
     * @objectName: It is the core class that contaions the remote methods
     * each object can contain several remote methods
     */
    @Deprecated
    public void registerDispatcher(Object remoteMethod, String objectName) {
        dispatchers.put(objectName, remoteMethod);
    }

    // Use this one
    public void registerDispatcher(String dispatcherName, DispatcherService dispatcher) {
        dispatchers.put(dispatcherName, dispatcher);
    }

    public HashMap<String, Object> getDispatchers() {
        return dispatchers;
    }
}
