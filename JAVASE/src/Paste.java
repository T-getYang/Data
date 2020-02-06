import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Paste {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        //要被复制的文件路径
        File oldPath = new File("E:\\test\\test\\test.txt");
        //复制完存放的文件路径
        File newPath = new File("E:\\file");

        if(!oldPath.exists()){
            System.out.println("要进行复制的文件路径不存在。");
            return;
        }

        //如果 要被复制的文件路径是文件
        if(oldPath.isFile()){
            // 如果 目标路径也是文件
            if(newPath.isFile()){
                //则开始判断 要复制的文件的名字 是否 和目标路径下的文件的名字相等
                if(oldPath.getName().equals(newPath.getName())){
                    System.out.println("该文件的名字在目标路径底下已经有存在一个相同名字的文件，请问是否进行覆盖？ \n 1.覆盖 2.取消 3.创建副本");
                    int choose = sc.nextInt();
                    if(choose == 1){
                        FileCopy(oldPath,newPath);
                        System.out.println("文件复制成功");
                    }else if(choose == 2){
                        System.out.println("选择了取消该文件的复制");
                        return;
                    }else if(choose == 3){
                        System.out.println("创建该文件的副本");
                    }
                }else{

                    System.out.println("因为目标路径是一个文件，不允许被复制进去");
                    return;
                }
                // 如果 目标的路径是文件夹
            }else{
                //则开始判断 该文件夹底下是否存在着文件或者文件夹
                //如果 目标路径的长度为null则进行复制
                if(!newPath.exists()){
                    newPath.mkdirs();
                }
                if(newPath.listFiles().length == 0){
                    System.out.println("目标路径是一个空的文件夹，正在进行复制文件进去...");
                    FileCopy(oldPath,newPath);
                }else{
                    //如果目标路径的不为null，则取消操作
                    //满足需求： 如果目标底下有一个文件或者文件夹，则取消操作
                    System.out.println("目标路径不是一个空的文件夹，可能存在文件或者文件夹，不能被复制进去");
                    return;
                }
            }
            //要被复制的路径是一个文件夹
        }else{
            //目标路径是一个文件
            if(newPath.isFile()){
                System.out.println("目标路径是一个文件，文件夹不允许被复制进去...");
                return;
            }else{
                //目标路径的文件夹的长度是否为null
                //String.valueOf(newPath.lastModified())== null
                System.out.println(newPath.listFiles().length);
                if(newPath.listFiles().length ==0){
                    System.out.println("目标路径一个空的文件夹，正在复制文件夹进去...");
                    copyDirectory(oldPath,newPath);
                }else{
                    //判断 被复制的文件夹的名字 是否和 目标路径下的文件夹的名字 相同
                    if(oldPath.getName().equals(newPath.getName())){
                        System.out.println("出现相同的文件夹的名字，是否进行覆盖？ \n  1.覆盖 2.取消 3.创建副本");
                        int dir = sc.nextInt();
                        if(dir == 1){
                            copyDirectory(oldPath,newPath);
                            System.out.println("文件夹覆盖成功。");
                        }else if(dir == 2){
                            System.out.println("选择了取消文件夹的覆盖，正在进行取消操作...");
                            return;
                        }else if(dir == 3){
                            System.out.println("创建一个文件夹的副本，并把文件夹给复制进去");
                        }
                    }else{
                        System.out.println("该文件夹不是一个空的文件夹，无法复制文件夹进去...");
                        return;
                    }
                }
            }
        }
    }
    public static void FileCopy(File file1,File file2) throws IOException {

        FileInputStream in = new FileInputStream(file1);
        File src = null;
        if(file2.isDirectory()){
            src= new File(file2.getAbsolutePath(),file1.getName());
        }else{
            src = file2;
        }

        if(!src.exists()){
            src.createNewFile();
        }else if(src.exists()&& src.isFile()){
            src.delete();
        }else if(src.exists() && src.isDirectory()){
            System.out.println("错误 :出现同名文件夹,需要手动删除" );
            return;
        }
        FileOutputStream out = new FileOutputStream(src);
        byte[] bytes = new byte[1024 * 4]; // 4KB
        int length = -1;
        while ((length = in.read(bytes)) != -1){
            out.write( bytes, 0, length);
        }
        System.out.println("复制文件成功");
    }


    //复制文件夹
    private static void copyDirectory(File oldPath, File newPath) throws IOException {
        //将要被复制的文件的路径底下的所有文件放到一个数组里去
        File [] files =oldPath.listFiles();
        //创建一个空的变量用于存放路径
        File text = null;

        if (newPath.exists()){
            // 判断目标路径 是否为一个文件夹
            if(newPath.isDirectory()){
                //如果文件夹的名字不存在
                if(!newPath.exists()){
                    //则将获取到的名字赋值给 text
                    text = new File(newPath.getAbsolutePath()+File.separator+newPath.getName());
                    // 用 被赋值完的 text的值来创建一个文件夹
                    text.mkdirs();
                    // 如果文件夹存在
                }else{
                    //则把 目标路径 赋值给 text
                    text = newPath;
                }
                // 如果是一个文件，则进行赋值
            }else{
                text = newPath;
            }
        }else{
            System.out.println("要被复制的文件夹路径不存在。。。");
            return;
        }

        if(files != null){
            for(File file : files){
                //如果目标路径是一个文件夹
                if(file.isDirectory()){
                    //则进行递归
                    copyDirectory(file,text);
                    //目标路径是一个文件
                }else{
                    FileCopy(file,text);
                }
            }
        }
        System.out.println("文件夹复制成功");
    }
}
