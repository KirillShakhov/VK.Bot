package ru.ifmo.modules;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CookieModule{
    private Map<Integer, Stack<String>> cookies = new HashMap<>();

    public CookieModule(){}
    public String getTop(Integer id){
        return cookies.get(id).peek();
    }
    public void push(Integer id, String s){
        if(!cookies.containsKey(id))cookies.put(id, new Stack<>());
        cookies.get(id).push(s);
    }
    void removeLast(Integer id){
        if(!cookies.containsKey(id))cookies.put(id, new Stack<>());
        else cookies.get(id).pop();
    }
}
