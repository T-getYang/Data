import java.io.File;

public class Delete {

    public static void main(String[] args) {
        File file = new File("H:\\eclipse\\st\\src\\st");
        if(!file.exists()){
            System.out.println("文件路徑不存在");
        }else {
            fileDelete(file);
        }
    }

    public static void fileDelete(File file) {
        File[] files = file.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    fileDelete(f);
                } else {
                    f.delete();
                }
            }
        }
        file.delete();
    }

}
