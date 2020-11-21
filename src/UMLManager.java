import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Manages everything about the parsing
 */
public class UMLManager {

    private UMLClassView view;
    private boolean showWatermark;

    public UMLManager(UMLClassView view) {
        this.view = view;
        this.showWatermark = view.isWatermarkSelected();
        view.setManager(this);
    }

    /**
     * Converts the class text into compilable Java files at the package destination containing the code
     * @param classesText An ArrayList of Type String containing all classes in UML text represantation
     * @param packagePath The path to the package where the classes should be stored to
     */
    public void parseClasses(ArrayList<String> classesText, String packagePath) {

        String packageString = packagePath.replaceAll(".*src\\\\(.*)", "$1");
        packageString = packageString.replaceAll("\\\\", ".");

        ArrayList<UMLClass> classes = new ArrayList<>();

        for (String text : classesText) {
            classes.add(new UMLClass(text, packageString, this));
        }

        classes.forEach(e -> System.out.println(e.toString()));

        for (UMLClass c : classes) {
            try {
                String path = packagePath + "/" + c.getName() + ".java";
                FileWriter fw = new FileWriter(path);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(c.toString());
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isShowWatermark() {
        return showWatermark;
    }

    public void setShowWatermark(boolean showWatermark) {
        this.showWatermark = showWatermark;
    }
}
