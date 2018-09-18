package edu.hendrix.ivyjs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PreAssembling {
    //WE NEED TO HANDLE REGISTERS 0-15
    public static HashMap<String, Integer> generateMemoryPointers(ArrayList<String> inputArray){
        int currentRegister = 16;
        HashMap<String, Integer> tokenTable = new HashMap<>();


        for(int i=0; i<inputArray.size();i++){

            String currentToken = inputArray.get(i);

            if(currentToken.startsWith("@")){
                String token = currentToken.split("@")[1];

//                if(!currentToken.startsWith("@R")&&Integer.parseInt(token)<16) {
//TODO: Allow numbers <16 to be set as registers
//TODO: Allow you to set an exact memory location
//                if(!currentToken.startsWith("@R")) {

                    if(!isNumeric(token)) {
                        tokenTable.put("@" + token, currentRegister);
                        currentRegister++;
                    }else{
                        tokenTable.put("@" + token, Integer.valueOf(token));
                    }
                }else if(currentToken.startsWith("@")){
                String token = currentToken.split("@")[1];
                    if(token.contains("R")) {
                        token = token.split("R")[1];
                    }
                    tokenTable.put("@R" + token, Integer.valueOf(token));
                }
            }
        return tokenTable;

        }

    public static HashMap<String, Integer> generateJumpPointers(ArrayList<String> inputArray){
        HashMap<String, Integer> tokenTable = new HashMap<>();
        int lineCounter =0;
        for(int i=0; i<inputArray.size();i++){
            lineCounter++;
            String currentToken = inputArray.get(i);
            if(currentToken.startsWith("(")){
                lineCounter--;
                tokenTable.put(currentToken, lineCounter);

            }
        }
        return tokenTable;
    }
    public static HashMap<String, Integer> generateTokenTable(ArrayList<String> input){
        //This assigns the line numbers to pointer @'s.
        //TODO: FIX THIS ^
        HashMap afterJumps = generateJumpPointers(input);
        HashMap afterMemoryPointers = generateMemoryPointers(input);
       // System.out.println("afterjumps is "+ afterJumps);
        HashMap<String, Integer> output = new HashMap<>();
        for(String key: (Set<String>)(afterMemoryPointers.keySet())){
            String withoutAt = key.split("@")[1];

            Integer insideAfterJumps = (Integer) afterJumps.get("(" + withoutAt + ")");
            Integer oldValue = (Integer) afterMemoryPointers.get(key);

            if(afterJumps.containsKey("("+withoutAt+")")){
                afterMemoryPointers.replace(key, oldValue, insideAfterJumps);
            }
        }

        output.putAll(afterJumps);
        output.putAll(afterMemoryPointers);
        return output;
    }
    public static boolean isNumeric(String strNum) {
     //https://www.baeldung.com/java-check-string-number
        try {
            Integer.parseInt(strNum);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
