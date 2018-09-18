package edu.hendrix.ivyjs;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FileIO {
    //This is just basic text handling stuff
    public static ArrayList<String> readLines(String filename) throws IOException {
        //http://www.technical-recipes.com/2011/reading-text-files-into-string-arrays-in-java/
        FileReader fileReader = new FileReader(filename);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }

        bufferedReader.close();
        return new ArrayList<>(Arrays.asList(lines.toArray(new String[lines.size()])));





    }
    public static ArrayList<String> removeWhitespace(ArrayList<String> inputArray){
        //https://stackoverflow.com/questions/2932392/java-how-to-replace-2-or-more-spaces-with-single-space-in-string-and-delete-lead/33628796
        for(int i=0;i<inputArray.size(); i++){
            String beforeTrim = inputArray.get(i);
            String after = beforeTrim.trim().replaceAll(" +", " ");
            inputArray.set(i, after);
        }
        inputArray.removeAll(Arrays.asList("", null));
        return inputArray;
    }
    public static ArrayList<String> removeComments(ArrayList<String> inputArray){
        for(int i=0;i<inputArray.size(); i++){
            String line = inputArray.get(i);
            String[] splitted = line.split("//");
            inputArray.set(i, splitted[0]);
        }
        return inputArray;
    }
    public static ArrayList<String> prepareInputFile(String filename) throws IOException {
        ArrayList<String> fileContents = FileIO.readLines(filename);
        fileContents = FileIO.removeComments(fileContents);
        fileContents = FileIO.removeWhitespace(fileContents);
        return fileContents;
    }
    public static void writeFile(ArrayList<String> contents, String path) throws IOException {
        File file = new File(path);
        FileWriter writer = new FileWriter(path);
        for(String str: contents) {
            writer.write(str+"\n");
        }
        writer.close();
    }
    public static void AssembleAndSave(String filepath) throws IOException {
        ArrayList preparedFile  = FileIO.prepareInputFile(filepath);
        String pathWithHack = filepath.split(".asm")[0] + ".hack";
        HashMap outputHashmap = PreAssembling.generateTokenTable(preparedFile);
        preparedFile= Assembling.assemble(preparedFile);
        FileIO.writeFile(preparedFile, pathWithHack);
    }
}
