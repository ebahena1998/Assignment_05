import java.io.*;

public class FileCopy {
    public static File[] pathOfFile = File.listRoots();

    public static void main (String[] args) {
        File sourceFile;
        File targetFile;
        if(args.length != 2) {
            System.out.println("Error, must have two command line arguments");
            System.exit(0);
        }

        String sourceName = args[0];
        String targetName = pathOfFile[0] + args[1];

        try {

            sourceFile = checkSourceFile(sourceName);
            targetFile = createDirectories(targetName);
            copyContents(sourceFile, targetFile);

            System.out.println("successfully copied info into: " + targetFile.getAbsolutePath());

        } catch (FileNotFoundException fnfe) {

            System.out.println(fnfe.getMessage());
            System.exit(0);

        } catch (IOException ioe) {

            System.out.println(ioe.getMessage());
            System.exit(0);

        }



    }

    public static File checkSourceFile(String sourceName) throws FileNotFoundException{
        File source = new File(sourceName);
        if(!source.exists()) {

            throw new FileNotFoundException("Error, source file does not exists!");
        }

        return source;
    }

    public static File createDirectories(String targetName) throws IOException{
        if(targetName.contains("/")) {

            targetName = targetName.replaceAll("/", "\\\\");

        }

        File target = new File(targetName).getParentFile();
        target.mkdirs();


        target = new File(targetName);
        if(target.exists()) {

            throw new FileNotFoundException("Error, target file already exist!");

        }

        target.createNewFile();
        return target;
    }

    public static void copyContents(File source, File target) throws FileNotFoundException, IOException{
        InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(target);

        int content;
        while((content = in.read()) != -1) {
            out.write((char) content);
        }
        out.flush();
        out.close();
        in.close();
    }




}
