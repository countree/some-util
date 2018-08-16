
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class CleanMavenReposFile {

  private static final String EXTENSION_LAST_UPDATE_REGEX = ".*\\.jar\\.lastUpdated";

  private static Set<File> deleteFilePathSet = new HashSet();

  public static void main(String[] args) {
    if (args.length > 1) {
      System.err.println("    只需要输入maven资源库的路径");
      return;
    } else if (args.length <= 0) {
      System.err.println("    必须输入maven资源库的路径");
      return;
    }
    File file = new File(args[0]);
    if (file.exists()) {
      if (!file.isDirectory()) {
        System.err.println("    maven资源库的路径必须是个文件夹");
        return;
      }
    } else {
      System.err.println("    输入的文件路径不存在");
      return;
    }
    long st = System.currentTimeMillis();
    findDeleteFile(file);
    long et = System.currentTimeMillis();
    long bt = et - st;
    System.out.println(deleteFilePathSet);
    deleteFilePathSet.forEach(
        ifile -> deleteAllFile(ifile)
    );
    System.out.printf("耗时:%d毫秒\n", bt);
  }


  private static void findDeleteFile(File filePath) {
    File[] files = filePath.listFiles();
    if (files != null) {
      for (File f : files) {
        if (f.isDirectory()) {
          findDeleteFile(f);
        } else {
          String fileName = f.getName();
          if (fileName.matches(EXTENSION_LAST_UPDATE_REGEX)) {
            System.out.printf("删除文件>%s\n", f.getAbsolutePath());
            deleteFilePathSet.add(f.getParentFile());
          }
        }
      }
    }
  }

  private static void deleteAllFile(File filePath) {
    File[] files = filePath.listFiles();
    if (files != null) {
      for (File f : files) {
        if (f.isDirectory()) {
          deleteAllFile(f);
        } else {
          f.delete();
        }
      }
    }
  }

}
