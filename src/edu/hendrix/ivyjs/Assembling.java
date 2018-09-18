package edu.hendrix.ivyjs;

import java.util.ArrayList;
import java.util.HashMap;

public class Assembling {
//    public static ArrayList<String> convertLabels(ArrayList<String> input) {
//        for (int i = 0; i < input.size(); i++) {
//            if (generatedTokenTable.containsKey(input.get(i))) {
//                input.set(i, "@" + generatedTokenTable.get(input.get(i)));
//            }
//        }
//        return input;
//    }
//    public static ArrayList<String> assemble(ArrayList<String> withConvertedLabels){
//
//    }

    public static String convertPointerToA(String input) {
        String asBinary = Integer.toBinaryString(Integer.parseInt(input));
        while (asBinary.length() < 16) {
            asBinary = "0" + asBinary;
        }
        return asBinary;
    }

    public static String getDestinationNonJump(String input) {
        return input.split("=")[0];
    }

    public static String getCommandNonJump(String input) {
        return input.split("=")[1];
    }

    public static String commandToBinary(String command) {
        String output = "";
        switch (command) {
            //All A=0 commands
            case "0":
                output = "0101010";
                break;
            case "1":
                output = "0111111";
                break;
            case "-1":
                output = "0111010";
                break;
            case "D":
                output = "0001100";
                break;
            case "A":
                output = "0110000";
                break;
            case "!D":
                output = "0001101";
                break;
            case "!A":
                output = "0110001";
                break;
            case "-D":
                output = "0001111";
                break;
            case "-A":
                output = "0110011";
                break;
            case "D+1":
                output = "0011111";
                break;
            case "A+1":
                output = "0110111";
                break;
            case "D-1":
                output = "0001110";
                break;
            case "A-1":
                output = "0110010";
                break;
            case "D+A":
                output = "0000010";
                break;
            case "D-A":
                output = "0010011";
                break;
            case "A-D":
                output = "0000111";
                break;
            case "D&A":
                output = "0000000";
                break;
            case "D|A":
                output = "0010101";
                break;
            //With A
            case "M":
                output = "1110000";
                break;
            case "!M":
                output = "1110001";
                break;
            case "-M":
                output = "1110011";
                break;
            case "M+1":
                output = "1110111";
                break;
            case "M-1":
                output = "1110010";
                break;
            case "D+M":
                output = "1000010";
                break;
            case "D-M":
                output = "1010011";
                break;
            case "M-D":
                output = "1000111";
                break;
            case "D&M":
                output = "1000000";
                break;
            case "D|M":
                output = "1010101";
                break;
        }
        return output;
    }

    public static String getJumpDestination(String input) {
        return input.split(";")[0];

    }

    public static String getJumpCommand(String input) {
        return input.split(";")[1];
    }

    public static String convertJumpCommandToBinary(String input) {
        String output = "";
        switch (input) {
            case "":
                output = "000";
                break;
            case "JGT":
                output = "001";
                break;
            case "JEQ":
                output = "010";
                break;
            case "JGE":
                output = "011";
                break;
            case "JLT":
                output = "100";
                break;
            case "JNE":
                output = "101";
                break;
            case "JLE":
                output = "110";
                break;
            case "JMP":
                output = "111";
        }
        return output;
    }

    public static String generateJumpBinary(String input) {
        String destination = getJumpDestination(input);
        String command = getJumpCommand(input);
        return "111" + commandToBinary(destination)+ commandToBinary(command) + "000" + convertJumpCommandToBinary(command);
    }
    public static String destinationToBinary(String input){
        String output="";
        switch(input){
            case "M": output = "001";
            break;
            case "D": output = "010";
            break;
            case "MD": output = "011";
            break;
            case "A": output = "100";
            break;
            case "AM": output = "101";
            break;
            case "AD": output = "110";
            break;
            case "AMD": output = "111";
            break;
        }
        return  output;
    }
    public static String generateCommandBinary(String input) {
        String destination = destinationToBinary(getDestinationNonJump(input));
        String command = commandToBinary(getCommandNonJump(input));
        return "111" + command + destination + "000";
    }

    public static ArrayList<String> assemble(ArrayList<String> input) {
        //input=convertLabels(input);
        HashMap<String, Integer> generatedTokenTable = PreAssembling.generateTokenTable(input);
        System.out.println(generatedTokenTable);
        for (int i = 0; i < input.size(); i++) {
            String currentLine = input.get(i);
            if (!currentLine.startsWith("@") && currentLine.contains(";")) {
                String binaryLine = generateJumpBinary(currentLine);
                input.set(i, binaryLine);
            } else {
                if (!currentLine.startsWith("@") && currentLine.contains("=")) {
                    String binaryLine = generateCommandBinary(currentLine);
                    input.set(i, binaryLine);
                } else if (currentLine.startsWith("@")||currentLine.startsWith("(")) {
                    if(currentLine.startsWith("(")){
                        input.remove(i);
                    }
                    if (generatedTokenTable.containsKey(input.get(i))) {
                        input.set(i,  convertPointerToA((String.valueOf(generatedTokenTable.get(input.get(i))))));
                    }
                }
            }
        }
        return input;
    }
}