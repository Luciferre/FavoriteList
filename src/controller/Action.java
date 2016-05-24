/* Name: Shan Gao
 * Date: 12/5/2015
 * Course number: 08672
 */

package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public abstract class Action {
    // Returns the name of the action, used to match the request in the hash table
    public abstract String getName();

    // Returns the name of the jsp used to render the output.
    public abstract String perform(HttpServletRequest request);

    //
    // Class methods to manage dispatching to Actions
    //
    private static Map<String,Action> map = new HashMap<String,Action>();

    public static void add(Action action) {
    	synchronized (map) {
    		if (map.get(action.getName()) != null) {
    			throw new AssertionError("Two actions with the same name (" + action.getName() + "): " + action.getClass().getName() + " and " + map.get(action.getName()).getClass().getName());
    		}
    		
    		map.put(action.getName(),action);
    	}
    }

    public static String perform(String name,HttpServletRequest request) {
        Action action;
        synchronized (map) {
        	action = map.get(name);
        }
        
        if (action == null) return null;
        return action.perform(request);
    }
}
