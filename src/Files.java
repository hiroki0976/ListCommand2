import java.io.File;
import java.util.*;

public class Files implements Comparable<Files>{
    private File dir = new File(".");
    private File[] files = dir.listFiles();
    private TreeSet<File> arrayFile = new TreeSet<>(); //通常配列
    private ArrayList<Character> opt = new ArrayList<>(); //文字の保持用
    private ArrayList<File> list;

    public Files(String[] args){
        for(File f: files) {
            arrayFile.add(f);
        }

        for(int j = 1; j < args.length; j++) {
            if(args[j].charAt(0) == '-') { //オプションの処理
                for(int i = 1; i < args[j].length(); i++) {
                    opt.add(args[j].charAt(i));
                }
            } else if(args[j].charAt(0) == '/' || args[j].charAt(0) == '~'){ //絶対パスの処理
                arrayFile = new TreeSet<>();
                for(int i = 0; i < args.length; i++) {
                    dir = new File(args[i]);
                }
                files = dir.listFiles();

                if(files == null) {
                    System.out.println("No such file or directory");
                } else {
                    for(File f: files) {
                        arrayFile.add(f);
                    }
                }
            } else { //相対パスの処理
                arrayFile = new TreeSet<>();
                for(int i = 0; i < args.length; i++) {
                    dir = new File(args[i]);
                }
                files = dir.listFiles();

                if(files == null) {
                    System.out.println("No such file or directory");
                } else {
                    for(File f: files) {
                        arrayFile.add(f);
                    }
                }
            }
        }

    }

    public File[] getFiles() {
        return files;
    }

    public TreeSet<File> getArrayFile() {
        return arrayFile;
    }

    public ArrayList<Character> opt() {
        return opt;
    }

    public ArrayList<File> list() {
        return list;
    }

    public String getName() {
        return dir.getName();
    }

    public void getNames() {
        if(opt.contains('r')) {
            for(int i = 0; i < files.length; i++) {
                if(opt.contains('a')) {
                    if(list.get(i).isHidden()) {
                        System.out.println(list.get(i).getName());
                    }
                }
                if(!list.get(i).isHidden()) {
                    System.out.println(list.get(i).getName());
                }
            }
        } else {
            for(File f: arrayFile) {
                if(opt.contains('a')) {
                    if(f.isHidden()) {
                        System.out.println(f.getName());
                    }
                }
                if(!f.isHidden()) {
                    System.out.println(f.getName());
                }
            }
        }
    }

    public void getOption() {

        for(int i = 0; i < opt.size(); i++) {
            switch(opt.get(i)) {
                case 'l':
                    break;
                case 'a':
                    break;
                case 't':
                    arrayFile = new TreeSet<>(new Comparator<File>() {
                        @Override
                        public int compare(File o1, File o2) {
                            if(o1.lastModified() == o2.lastModified()) {
                                return 1;
                            }
                            return (int)(o2.lastModified() - o1.lastModified());
                        }
                    });
                    for(File f: files) {
                        arrayFile.add(f);
                    }
                    break;
                case 'r':
                    break;
                default:
                    System.out.println("command not found");
                    break;
            }
        }

        if(opt.contains('r')) {
            list = new ArrayList<>(arrayFile);
            Collections.reverse(list);
        }

    }

    @Override
    public int compareTo(Files other) {
        if(this.equals(other)) {
            return 0;
        }
        return dir.getName().compareTo(other.getName());
    }
}
