//Title: Assignment 5
//Name: Edgar Bahena
//Email: ebahena5@toromail.csudh.edu
//Date: 05/12/2023

import java.io.*;

public class DirectoryAnalyzer {
    public static void main(String[] args){
        if(args.length != 1){
            System.out.println("Error, must enter only 1 command line arg");
            System.exit(0);
        }

        String directoryName = "";
        directoryName = args[0];

        File file = new File(directoryName);

        InputStream in = null;
        OutputStream out = null;

        try{
            if(!file.isDirectory()){
                System.out.println("FILE IS NOT A DIRECTORY");
                System.exit(0);
            }
            System.out.println(String.format("%-35s %7s %20s %15s %11s", "File Name", "Size", "Alpha Chars",
                    "Numeric Chars", "Spaces"));
            int endProgram = 0;
            int total = 0;
            int secondTotal = 0;
            int thridTotal = 0;
            int fTotal = 0;
            int sixTotal = 0;
            File fileList[] = file.listFiles();
            for(File tempFile : fileList){

                if(!tempFile.canRead() || !tempFile.canExecute() || tempFile.isDirectory()) {

                    throw new FileNotFoundException("CAnnot execute ");

                } else {

                    in = new FileInputStream(tempFile);
                    int i = 0;
                    int alphaNums = 0;
                    int numeric = 0;
                    int space = 0;
                    int bSize = 0;

                    while((i = in.read()) != -1){
                        if(i > 64 && i < 133){
                            alphaNums++;
                        }
                        if(i > 47 && i < 58){
                            numeric++;
                        }
                        if(i == 32){
                            space++;
                        }
                        bSize++;
                    }

                    String fileInfo = "";
                    fileInfo += String.format("%-35s %10s %12d %13d %16d\n", tempFile.getName(),
                            ("" + bSize + " bytes"), alphaNums, numeric, space);
                    System.out.print(fileInfo);
                    endProgram++;
                    total += alphaNums;
                    secondTotal += numeric;
                    thridTotal += space;

                    if(endProgram == fileList.length){
                        System.out.print("\nTotal Files:\t\t" + fileList.length);
                        System.out.print("\nTotal Alpha Chars:\t" + total);
                        System.out.print("\nTotal Numeric Chars:\t" + secondTotal);
                        System.out.println("\nTotal Space Chars:\t" + thridTotal);
                    }

                }
            }
        } catch (IOException ioe){

            System.out.println(ioe.getMessage());

        } finally{
            try{
                if(in!= null){
                    in.close();
                }
                if(out != null){
                    out.close();
                }
            } catch(IOException ioe){
                ioe.printStackTrace();
            }
        }

    }
}