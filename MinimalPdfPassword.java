import java.io.File;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

public class MinimalPdfPassword {

    public static void main(String[] args) throws Exception {

        File in = new File(args[0]);
        File out = new File(args[0] + ".protected");

        PDDocument doc = Loader.loadPDF(in);

        AccessPermission ap = new AccessPermission();
        StandardProtectionPolicy policy =
            new StandardProtectionPolicy(args[1], args[1], ap);

        doc.protect(policy);
        doc.save(out);
        doc.close();

        // replace original safely
        in.delete();
        out.renameTo(in);
    }
}