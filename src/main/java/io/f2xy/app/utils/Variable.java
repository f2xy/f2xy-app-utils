package io.f2xy.app.utils;

import io.f2xy.app.utils.interfaces.Settings;
import io.f2xy.app.utils.interfaces.VariableFunction;
import org.json.JSONObject;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 19.09.2013
 *
 * @author Hakan GÜR <hakangur82@gmail.com>
 * @version 1.0
 */
public class Variable {

    private Map<String, String> variables;
    private Map<String, VariableFunction> functions;

    private String variableBeginSyntax = "$(";
    private String variableEndSyntax = ")";
    private String functionSeparator = ":";
    private String functionParameterSeparator = ",";

    //public abstract String variableCallFunction(String function,String... args) throws Exception;


    public Variable() {
        variables = new HashMap<String, String>();
        functions = new HashMap<String, VariableFunction>();
    }

    public Variable(Map<String,String> variables,Map<String,VariableFunction>functions){
        this.variables = variables;
        this.functions = functions;
    }

    @Nullable
    public String parseVariable(String s) {
        if (s == null)
            return null;

        String donen = s;

        while (true) {
            int basla = donen.lastIndexOf(variableBeginSyntax);

            if(basla < 0)
                break;

            int bitis = donen.indexOf(variableEndSyntax,basla);

            if ((bitis < 0))
                break;

            String ad = donen.substring(basla + variableBeginSyntax.length(), bitis);
            String deger = null;

            if (ad.indexOf(functionSeparator) > 0) {
                String fonk = ad.substring(0, ad.indexOf(functionSeparator));
                String d = ad.substring(ad.indexOf(functionSeparator) + functionSeparator.length());

                if(functions.containsKey(fonk)){
                    try{
                        deger = functions.get(fonk).executeFunction(d.split(functionParameterSeparator));
                    }catch (Exception e){
                        deger = getVariable(ad);
                    }
                }

                /*try {
                    //deger = variableCallFunction(fonk,d.split(functionParameterSeparator));
                    deger = functions.get(fonk).executeFunction(d.split(functionParameterSeparator));

                } catch (Exception e) {
                    deger = getVariable(ad, "");
                }*/

            } else {
                deger = getVariable(ad);
            }

            if(deger == null)
                deger = "";

            donen = donen.replace(variableBeginSyntax + ad + variableEndSyntax, deger);
        }
        return donen;
    }

    @Nullable
    public String getVariable(String name) {
        try {
            if (variables.containsKey(name)) {
                return parseVariable(variables.get(name));
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public String getVariable(String name, String defaultValue) {
        String s = getVariable(name);
        return (s == null) ? defaultValue : s;
    }

    public void putAll(Settings settings){
        for(String key:settings.keys()){
            put(key,settings.load(key,null));
        }
    }

    public void putAll(Map<String,String> map){
        variables.putAll(map);
    }

    public void putAll(JSONObject jsonObject){
        for(Object i : jsonObject.keySet()){
            variables.put(i.toString(),jsonObject.getString(i.toString()));
        }
    }

    public void put(String key, String value) {
        variables.put(key, value);
    }

    public void registerFunction(String name,VariableFunction f){
        functions.put(name,f);
    }

    public String getVariableBeginSyntax() {
        return variableBeginSyntax;
    }

    public void setVariableBeginSyntax(String variableBeginSyntax) {
        this.variableBeginSyntax = variableBeginSyntax;
    }

    public String getVariableEndSyntax() {
        return variableEndSyntax;
    }

    public void setVariableEndSyntax(String variableEndSyntax) {
        this.variableEndSyntax = variableEndSyntax;
    }

    public String getFunctionSeparator() {
        return functionSeparator;
    }

    public void setFunctionSeparator(String functionSeparator) {
        this.functionSeparator = functionSeparator;
    }

    public String getFunctionParameterSeparator() {
        return functionParameterSeparator;
    }

    public void setFunctionParameterSeparator(String functionParameterSeparator) {
        this.functionParameterSeparator = functionParameterSeparator;
    }
}
